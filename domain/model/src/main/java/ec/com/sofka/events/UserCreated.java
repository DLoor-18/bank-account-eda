package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

public class UserCreated extends DomainEvent {
    private final String firstName;
    private final String lastName;
    private final String identityCard;
    private final String email;
    private final String password;
    private final StatusEnum statusEnum;

    public UserCreated(String firstName, String lastName, String identityCard, String email, String password, StatusEnum statusEnum) {
        super(EventsEnum.USER_CREATED.name());
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.email = email;
        this.password = password;
        this.statusEnum = statusEnum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public StatusEnum getStatus() {
        return statusEnum;
    }

}