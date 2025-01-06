package ec.com.sofka.rules.impl;

import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.exception.TransactionRejectedException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.rules.ValidateTransaction;
import ec.com.sofka.utils.enums.StatusEnum;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Component
public class ValidateTransactionImpl implements ValidateTransaction {

    private final AccountRepository AccountRepository;
    private final ErrorBusMessage errorBusMessage;

    public ValidateTransactionImpl(AccountRepository accountRepository, ErrorBusMessage errorBusMessage) {
        this.AccountRepository = accountRepository;
        this.errorBusMessage = errorBusMessage;
    }

    public Mono<TransactionDTO> validateTransaction(TransactionDTO transaction) {
        return AccountRepository.findByNumber(transaction.getAccountNumber())
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Account not found (" + transaction.getAccountNumber() + ")", "Validate Transaction"));
                    return Mono.error(new RecordNotFoundException("Account not found."));
                }))
                .map(account -> {
                    transaction.setAccount(account);
                    return transaction;
                })
                .flatMap(this::validateTransactionRules);
    }

    private Mono<TransactionDTO> validateTransactionRules(TransactionDTO transaction) {
        Predicate<TransactionDTO> isAccountActive = txn ->
                StatusEnum.ACTIVE.equals(txn.getAccount().getStatus());

        Predicate<TransactionDTO> isAccountRejected = txn ->
                txn.getTransactionType().getDiscount() &&
                        txn.getAccount().getBalance()
                                .subtract(txn.getTransactionType().getValue())
                                .compareTo(txn.getAmount()) < 0;

        return Mono.just(transaction)
                .filter(isAccountActive)
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Inactive or invalid account (" + transaction.getAccountNumber() + ")", "Validate Transaction Rules"));
                    return Mono.error(new TransactionRejectedException("Inactive or invalid account."));
                }))
                .filter(isAccountRejected.negate())
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Account does not have sufficient funds (" + transaction.getAccountNumber() + ")", "Validate Transaction Rules"));
                    return Mono.error(new TransactionRejectedException("The account does not have sufficient funds."));
                }));
    }

}