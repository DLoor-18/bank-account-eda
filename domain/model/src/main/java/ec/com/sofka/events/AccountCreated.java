package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.models.user.User;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private final String accountNumber;
    private final BigDecimal balance;
    private final StatusEnum statusEnum;
    private final User user;

    public AccountCreated(String accountNumber, BigDecimal balance, StatusEnum statusEnum, User user) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.statusEnum = statusEnum;
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public StatusEnum getStatus() {
        return statusEnum;
    }

    public User getUser() {
        return user;
    }

}