package ec.com.sofka.cases.transaction;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.*;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.model.TransactionMessage;
import ec.com.sofka.models.transaction.Transaction;
import ec.com.sofka.models.transaction.values.objects.ProcessingDate;
import ec.com.sofka.requests.TransactionRequest;
import ec.com.sofka.responses.TransactionResponse;
import ec.com.sofka.rules.BalanceCalculator;
import ec.com.sofka.rules.ValidateTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase {
    private final IEventStore repository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountRepository accountRepository;
    private final ValidateTransaction validateTransaction;
    private final BalanceCalculator balanceCalculator;
    private final TransactionBusMessage transactionBusMessage;
    private final ErrorBusMessage errorBusMessage;

    public CreateTransactionUseCase(IEventStore repository, TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository, AccountRepository accountRepository, ValidateTransaction validateTransaction, BalanceCalculator balanceCalculator, TransactionBusMessage transactionBusMessage, ErrorBusMessage errorBusMessage) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.accountRepository = accountRepository;
        this.validateTransaction = validateTransaction;
        this.balanceCalculator = balanceCalculator;
        this.transactionBusMessage = transactionBusMessage;
        this.errorBusMessage = errorBusMessage;
    }

    public Mono<TransactionResponse> execute(TransactionRequest transactionRequest) {
        return transactionTypeRepository.findById(transactionRequest.getTransactionTypeId())
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Transaction Type not found.", "Create Transaction"));
                    return Mono.error(new RecordNotFoundException("Account not found."));
                })).map(transactionType ->
                        new TransactionDTO(
                                transactionRequest.getAccountNumber(),
                                transactionRequest.getDetails(),
                                transactionRequest.getAmount(),
                                null,
                                null,
                                transactionType))
                .flatMap(validateTransaction::validateTransaction)
                .flatMap(this::updateBalanceAndSave)
                .flatMap(tran -> {
                    transactionBusMessage.sendMsg(
                            new TransactionMessage(tran.getId().value(),
                                    tran.getTransactionAccount().value(),
                                    tran.getTransactionType().getType(),
                                    tran.getAmount().value()));
                    return Mono.just(tran);
                })
                .map(TransactionMapper::mapToResponseFromModel);
    }

    public Mono<Transaction> updateBalanceAndSave(TransactionDTO transaction) {
        AccountAggregate accountAggregate = new AccountAggregate();

        BigDecimal newBalance = balanceCalculator.calculate(
                transaction,
                transaction.getAccount().getBalance()
        );
        transaction.getAccount().setBalance(newBalance);

        accountAggregate.createTransaction(
                transaction.getAccountNumber(),
                transaction.getDetails(),
                transaction.getAmount(),
                new ProcessingDate(null).value(),
                AccountMapper.mapToModelFromDTO(transaction.getAccount()),
                TransactionTypeMapper.mapToModelFromDTO(transaction.getTransactionType())
        );

        return accountRepository.save(transaction.getAccount())
                .then(transactionRepository.save(transaction))
                .thenMany(Flux.fromIterable(accountAggregate.getUncommittedEvents()))
                .flatMap(event -> Mono.fromCallable(() -> repository.save(event)))
                .then(Mono.fromCallable(() -> {
                    accountAggregate.markEventsAsCommitted();
                    return accountAggregate.getTransaction();
                }));
    }

}