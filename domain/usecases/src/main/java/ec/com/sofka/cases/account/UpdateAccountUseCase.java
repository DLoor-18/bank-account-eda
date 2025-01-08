package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.cases.user.FindUserByIdUseCase;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCaseExecute<AccountRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public UpdateAccountUseCase(IEventStore repository, AccountRepository accountRepository, FindUserByIdUseCase findUserByIdUseCase) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Override
    public Publisher<AccountResponse> execute(AccountRequest request) {
        AccountAggregate accountAggregate = new AccountAggregate();

//        return repository.findAggregate(request.getAggregateId())
//                .collectList()
//                .map(eventsList -> AccountAggregate.from(request.getAggregateId(), eventsList))
//                .flatMap(accountAgg ->
//                        accountRepository.findByNumber(accountAgg.getAccount().getId().value())
//                                .map(AccountMapper::mapToModelFromDTO)
//                );
//
//        return accountRepository.save(transaction.getAccount())
//                .then(transactionRepository.save(transaction))
//                .flatMap(account -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
//                        .flatMap(repository::save)
//                        .then(Mono.just(account)))
//                .then(Mono.fromCallable(() -> {
//                    accountAggregate.markEventsAsCommitted();
//                    return accountAggregate.getTransaction();
//                }));
        return null;
    }
}
