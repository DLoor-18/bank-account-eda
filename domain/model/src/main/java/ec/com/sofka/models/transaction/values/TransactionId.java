package ec.com.sofka.models.transaction.values;

import ec.com.sofka.generics.shared.Identity;

public class TransactionId extends Identity {

    public TransactionId() {
        super();
    }

    public TransactionId(final String value) {
        super(value);
    }

    public static TransactionId of(final String id) {
        return new TransactionId(id);
    }

}