package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.events.TransactionTypeCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.entities.transactionType.TransactionType;
import ec.com.sofka.entities.transactionType.values.TransactionTypeId;

public class TransactionTypeHandler extends DomainActionsContainer {

    public TransactionTypeHandler(AccountAggregate accountAggregate) {
        addDomainActions((TransactionTypeCreated event) -> {
            TransactionType transactionType = new TransactionType(
                    TransactionTypeId.of(event.getTransactionTypeId()),
                    event.getType(),
                    event.getDescription(),
                    event.getValue(),
                    event.getTransactionCost(),
                    event.getDiscount(),
                    event.getStatus());

            accountAggregate.setTransactionType(transactionType);
        });
    }

}