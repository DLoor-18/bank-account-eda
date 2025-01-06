package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class TransactionTypeCreated extends DomainEvent {
    private final String type;
    private final String description;
    private final BigDecimal value;
    private final Boolean transactionCost;
    private final Boolean discount;
    private final StatusEnum statusEnum;

    public TransactionTypeCreated(String type, String description, BigDecimal value, Boolean transactionCost, Boolean discount, StatusEnum statusEnum) {
        super(EventsEnum.TRANSACTION_TYPE_CREATED.name());
        this.type = type;
        this.description = description;
        this.value = value;
        this.transactionCost = transactionCost;
        this.discount = discount;
        this.statusEnum = statusEnum;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Boolean getTransactionCost() {
        return transactionCost;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public StatusEnum getStatus() {
        return statusEnum;
    }

}