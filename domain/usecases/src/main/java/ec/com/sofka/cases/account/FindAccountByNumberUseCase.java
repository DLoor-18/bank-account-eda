package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseGetElement;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.AccountResponse;
import ec.com.sofka.responses.TransactionTypeResponse;
import reactor.core.publisher.Mono;

public class FindAccountByNumberUseCase implements IUseCaseGetElement<GetElementRequest, AccountResponse> {
    private final AccountRepository accountRepository;
    private final IEventStore eventStore;

    public FindAccountByNumberUseCase(AccountRepository accountRepository, IEventStore eventStore) {
        this.accountRepository = accountRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Mono<AccountResponse> get(GetElementRequest request) {

        return eventStore.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> AccountAggregate.from(request.getAggregateId(), eventsList))
                .flatMap(accountAggregate ->
                        accountRepository.findByNumber(accountAggregate.getAccount().getId().value())
                                .map(AccountMapper::mapToResponseFromDTO)
                );
    }

}