package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.events.UserCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.models.user.User;
import ec.com.sofka.models.user.values.UserId;
import ec.com.sofka.models.user.values.objects.IdentityCard;

public class UserHandler extends DomainActionsContainer {

    public UserHandler(AccountAggregate accountAggregate) {
        addDomainActions((UserCreated event) -> {
            User user = new User(new UserId(),
                    event.getFirstName(),
                    event.getLastName(),
                    IdentityCard.of(event.getIdentityCard()),
                    event.getEmail(),
                    event.getPassword(),
                    event.getStatus());

            accountAggregate.setUser(user);
        });
    }

}