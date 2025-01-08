package ec.com.sofka.cases.transaction;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.cases.transcationType.FindTransactionTypeByIdUseCase;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.*;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.model.TransactionMessage;
import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.requests.TransactionRequest;
import ec.com.sofka.responses.TransactionResponse;
import ec.com.sofka.rules.BalanceCalculator;
import ec.com.sofka.rules.ValidateTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase implements IUseCaseExecute<TransactionRequest, TransactionResponse> {
    private final IEventStore repository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountRepository accountRepository;
    private final ValidateTransaction validateTransaction;
    private final BalanceCalculator balanceCalculator;
    private final FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase;
    private final TransactionBusMessage transactionBusMessage;
    private final ErrorBusMessage errorBusMessage;

    public CreateTransactionUseCase(IEventStore repository, TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository, AccountRepository accountRepository, ValidateTransaction validateTransaction, BalanceCalculator balanceCalculator, FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase, TransactionBusMessage transactionBusMessage, ErrorBusMessage errorBusMessage) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.accountRepository = accountRepository;
        this.validateTransaction = validateTransaction;
        this.balanceCalculator = balanceCalculator;
        this.findTransactionTypeByIdUseCase = findTransactionTypeByIdUseCase;
        this.transactionBusMessage = transactionBusMessage;
        this.errorBusMessage = errorBusMessage;
    }

    @Override
    public Mono<TransactionResponse> execute(TransactionRequest transactionRequest) {
        return findTransactionTypeByIdUseCase.getUserByAggregate(transactionRequest.getTransactionTypeAggregateId())
                .map(TransactionTypeMapper::mapToDTOFromModel)
                .map(transactionType ->
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

        accountAggregate.updateAccount(
                accountAggregate.getTransaction().getAccount().getId().value(),
                accountAggregate.getTransaction().getAccount().getAccountNumber().value(),
                accountAggregate.getTransaction().getAccount().getBalance(),
                accountAggregate.getTransaction().getAccount().getStatus(),
                accountAggregate.getTransaction().getAccount().getUser()
        );

        return accountRepository.save(transaction.getAccount())
                .then(transactionRepository.save(transaction))
                .flatMap(account -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
                        .flatMap(repository::save)
                        .then(Mono.just(account)))
                .then(Mono.fromCallable(() -> {
                    accountAggregate.markEventsAsCommitted();
                    return accountAggregate.getTransaction();
                }));
    }

}