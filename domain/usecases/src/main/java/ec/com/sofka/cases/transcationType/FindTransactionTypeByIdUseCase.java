package ec.com.sofka.cases.transcationType;

import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.responses.TransactionTypeResponse;
import reactor.core.publisher.Mono;

public class FindTransactionTypeByIdUseCase {
    private final TransactionTypeRepository transactionTypeRepository;

    public FindTransactionTypeByIdUseCase(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Mono<TransactionTypeResponse> apply(String id) {
        return transactionTypeRepository.findById(id)
                .map(TransactionTypeMapper::mapToResponseFromDTO);
    }

}