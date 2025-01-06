package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.entities.user.User;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class AccountUpdated extends DomainEvent {
    private final String AccountId;
    private final String accountNumber;
    private final BigDecimal balance;
    private final StatusEnum status;
    private final User user;

    public AccountUpdated(String accountId, String accountNumber, BigDecimal balance, StatusEnum status, User user) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        AccountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.user = user;
    }

    public String getAccountId() {
        return AccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

}