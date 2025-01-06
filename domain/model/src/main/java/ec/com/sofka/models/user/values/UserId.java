package ec.com.sofka.models.user.values;

import ec.com.sofka.generics.shared.Identity;

public class UserId extends Identity {

    public UserId() {
        super();
    }

    public UserId(final String id) {
        super(id);
    }

    public static UserId of(final String id) {
        return new UserId(id);
    }

}