package ec.com.sofka.models.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;


public class AccountNumber implements IValueObject<String> {
    private final String value;

    private AccountNumber(final String value) {
        this.value = validate(value);
    }

    public static AccountNumber of(final String value) {
        return new AccountNumber(value);
    }

    @Override
    public String value() {
        return value;
    }

    private String validate(final String value) {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid number account.");
        }
        return value;
    }

}