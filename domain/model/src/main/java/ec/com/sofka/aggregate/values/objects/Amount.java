package ec.com.sofka.aggregate.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public record Amount(BigDecimal value) implements IValueObject<BigDecimal> {

    public Amount(final BigDecimal value) {
        this.value = isValid(value);
    }

    public static Amount of(final BigDecimal value) {
        return new Amount(value);
    }

    private BigDecimal isValid(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return value;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }
}