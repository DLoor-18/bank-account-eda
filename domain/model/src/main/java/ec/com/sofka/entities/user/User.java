package ec.com.sofka.entities.user;

import ec.com.sofka.generics.shared.Entity;
import ec.com.sofka.entities.user.values.UserId;
import ec.com.sofka.entities.user.values.objects.IdentityCard;
import ec.com.sofka.utils.enums.StatusEnum;

public class User extends Entity<UserId> {

    private String firstName;

    private String lastName;

    private final IdentityCard identityCard;

    private String email;

    private String password;

    private StatusEnum status;

    public User(UserId userId, String firstName, String lastName, IdentityCard identityCard, String email, String password, StatusEnum status) {
        super(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public IdentityCard getIdentityCard() {
        return identityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

}