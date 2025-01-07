package ec.com.sofka.cases.transcationType;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.generics.interfaces.IUseCaseGetElement;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.TransactionTypeResponse;
import reactor.core.publisher.Mono;

public class FindTransactionTypeByIdUseCase implements IUseCaseGetElement<GetElementRequest, TransactionTypeResponse> {
    private final TransactionTypeRepository transactionTypeRepository;
    private final IEventStore eventStore;

    public FindTransactionTypeByIdUseCase(TransactionTypeRepository transactionTypeRepository, IEventStore eventStore) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Mono<TransactionTypeResponse> get(GetElementRequest request) {

        return eventStore.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> AccountAggregate.from(request.getAggregateId(), eventsList))
                .flatMap(accountAggregate ->
                        transactionTypeRepository.findById(accountAggregate.getTransactionType().getId().value())
                                .map(TransactionTypeMapper::mapToResponseFromDTO)
                );
    }

}