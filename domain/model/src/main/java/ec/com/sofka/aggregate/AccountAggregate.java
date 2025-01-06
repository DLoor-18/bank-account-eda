package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.handlers.AccountHandler;
import ec.com.sofka.aggregate.handlers.TransactionHandler;
import ec.com.sofka.aggregate.handlers.TransactionTypeHandler;
import ec.com.sofka.aggregate.handlers.UserHandler;
import ec.com.sofka.aggregate.values.AccountId;
import ec.com.sofka.events.*;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.shared.AggregateRoot;
import ec.com.sofka.entities.account.Account;
import ec.com.sofka.entities.transaction.Transaction;
import ec.com.sofka.entities.transaction.values.TransactionId;
import ec.com.sofka.entities.transactionType.TransactionType;
import ec.com.sofka.entities.transactionType.values.TransactionTypeId;
import ec.com.sofka.entities.user.User;
import ec.com.sofka.entities.user.values.UserId;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;
import java.util.List;

public class AccountAggregate extends AggregateRoot<AccountId> {
    private User user;
    private TransactionType transactionType;
    private Account account;
    private Transaction transaction;

    public AccountAggregate() {
        super(new AccountId());
        setSubscription(new UserHandler(this));
        setSubscription(new TransactionTypeHandler(this));
        setSubscription(new AccountHandler(this));
        setSubscription(new TransactionHandler(this));
    }

    public AccountAggregate(final String id) {
        super(AccountId.of(id));
        setSubscription(new UserHandler(this));
    }

    public void createUser(String firstName, String lastName, String identityCard, String email, String password, StatusEnum statusEnum) {
        addEvent(new UserCreated(new UserId().value(), firstName, lastName, identityCard, email, password, statusEnum)).apply();
    }

    public void createTransactionType(String type, String description, BigDecimal value, Boolean transactionCost, Boolean discount, StatusEnum statusEnum) {
        addEvent(new TransactionTypeCreated(new TransactionTypeId().value(), type, description, value, transactionCost, discount, statusEnum)).apply();
    }

    public void createAccount(String accountNumber, BigDecimal balance, StatusEnum statusEnum, User user) {
        addEvent(new AccountCreated(new AccountId().value(), accountNumber, balance, statusEnum, user)).apply();
    }

    public void updateAccount(String accountId, String accountNumber, BigDecimal balance, StatusEnum statusEnum, User user) {
        addEvent(new AccountCreated(AccountId.of(accountId).value(), accountNumber, balance, statusEnum, user)).apply();
    }

    public void createTransaction(String transactionAccount, String details, BigDecimal amount, String processingDate, Account account, TransactionType transactionType) {
        addEvent(new TransactionCreated(new TransactionId().value(), transactionAccount, details, amount, processingDate, account, transactionType)).apply();
    }

    public static AccountAggregate from(final String id, List<DomainEvent> events) {
        AccountAggregate accountAggregate = new AccountAggregate(id);
        events.forEach((event) -> accountAggregate.addEvent(event).apply());
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .forEach((event) -> accountAggregate.addEvent(event).apply());
        return accountAggregate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}