package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.events.TransactionCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.models.transaction.Transaction;
import ec.com.sofka.models.transaction.values.TransactionId;
import ec.com.sofka.models.transaction.values.objects.Amount;
import ec.com.sofka.models.transaction.values.objects.ProcessingDate;
import ec.com.sofka.models.transaction.values.objects.TransactionAccount;

public class TransactionHandler extends DomainActionsContainer {

    public TransactionHandler(AccountAggregate accountAggregate) {
        addDomainActions((TransactionCreated event) -> {
            Transaction transaction = new Transaction(
                    new TransactionId(),
                    TransactionAccount.of(event.getTransactionAccount()),
                    event.getDetails(),
                    Amount.of(event.getAmount()),
                    ProcessingDate.of(event.getProcessingDate()),
                    event.getAccount(),
                    event.getTransactionType());

            accountAggregate.setTransaction(transaction);
        });
    }

}