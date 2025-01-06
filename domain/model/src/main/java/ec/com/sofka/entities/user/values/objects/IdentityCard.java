package ec.com.sofka.entities.user.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public record IdentityCard(String value) implements IValueObject<String> {

    public IdentityCard(final String value) {
        this.value = isValid(value);
    }

    public static IdentityCard of(final String value) {
        return new IdentityCard(value);
    }

    private String isValid(final String value) {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid identity card number.");
        }
        return value;
    }

    @Override
    public String value() {
        return this.value;
    }

}