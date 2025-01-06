package ec.com.sofka.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

public class UserCreated extends DomainEvent {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String identityCard;
    private final String email;
    private final String password;
    private final StatusEnum status;

    public UserCreated(String userId, String firstName, String lastName, String identityCard, String email, String password, StatusEnum status) {
        super(EventsEnum.USER_CREATED.name());
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getUserId() {
        return userId;
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
        return status;
    }

}