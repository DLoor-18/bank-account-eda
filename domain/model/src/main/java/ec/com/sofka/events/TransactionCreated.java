package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.entities.account.Account;
import ec.com.sofka.entities.transactionType.TransactionType;
import ec.com.sofka.utils.enums.EventsEnum;

import java.math.BigDecimal;

public class TransactionCreated extends DomainEvent {
    private final String transactionId;
    private final String transactionAccount;
    private final String details;
    private final BigDecimal amount;
    private final String processingDate;
    private final Account account;
    private final TransactionType transactionType;

    public TransactionCreated(String transactionId, String transactionAccount, String details, BigDecimal amount, String processingDate, Account account, TransactionType transactionType) {
        super(EventsEnum.TRANSACTION_CREATED.name());
        this.transactionId = transactionId;
        this.transactionAccount = transactionAccount;
        this.details = details;
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public String getDetails() {
        return details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

}