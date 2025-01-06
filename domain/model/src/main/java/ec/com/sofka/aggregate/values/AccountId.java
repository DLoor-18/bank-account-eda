package ec.com.sofka.aggregate.values;

import ec.com.sofka.generics.shared.Identity;

public class AccountId extends Identity {

    public AccountId() {
        super();
    }

    public AccountId(final String value) {
        super(value);
    }

    public static AccountId of(final String value) {
        return new AccountId(value);
    }

}