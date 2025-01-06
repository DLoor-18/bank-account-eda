package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.events.AccountCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.models.account.Account;
import ec.com.sofka.models.account.values.AccountId;
import ec.com.sofka.models.account.values.objects.AccountNumber;

public class AccountHandler extends DomainActionsContainer {

    public AccountHandler(AccountAggregate accountAggregate) {
        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    new AccountId(),
                    AccountNumber.of(event.getAccountNumber()),
                    event.getBalance(),
                    event.getStatus(),
                    event.getUser());

            accountAggregate.setAccount(account);
        });
    }

}