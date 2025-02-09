package ec.com.sofka.mapper;

import ec.com.sofka.aggregate.values.objects.Amount;
import ec.com.sofka.gateway.dto.TransactionTypeDTO;
import ec.com.sofka.aggregate.entities.transactionType.TransactionType;
import ec.com.sofka.aggregate.entities.transactionType.values.TransactionTypeId;
import ec.com.sofka.responses.TransactionTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeMapper {

    public static TransactionType mapToModelFromDTO(TransactionTypeDTO transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionType(
                TransactionTypeId.of(transactionType.getId()),
                transactionType.getType(),
                transactionType.getDescription(),
                Amount.of(transactionType.getValue()),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus()
        );
    }

    public static TransactionTypeResponse mapToResponseFromModel(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeResponse(
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue().getValue(),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus()
        );
    }

    public static TransactionTypeResponse mapToResponseFromDTO(TransactionTypeDTO transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeResponse(
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue(),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus()
        );
    }

    public static TransactionTypeDTO mapToDTOFromModel(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeDTO(
                transactionType.getId().getValue(),
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue().getValue(),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus()
        );
    }

}