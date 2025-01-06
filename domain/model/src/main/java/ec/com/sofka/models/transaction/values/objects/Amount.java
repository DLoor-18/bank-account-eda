package ec.com.sofka.models.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public record Amount(BigDecimal value) implements IValueObject<BigDecimal> {

    public Amount(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Amount of(final BigDecimal value) {
        return new Amount(value);
    }

    private BigDecimal validate(final BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The quantity must be positive and non-zero.");
        }
        return value;
    }

    @Override
    public BigDecimal value() {
        return this.value;
    }

}