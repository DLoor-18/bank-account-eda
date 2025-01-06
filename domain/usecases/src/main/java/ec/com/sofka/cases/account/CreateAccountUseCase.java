package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {
    private final IEventStore repository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ErrorBusMessage errorBusMessage;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository, UserRepository userRepository, ErrorBusMessage errorBusMessage) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.errorBusMessage = errorBusMessage;
    }

    public Mono<AccountResponse> execute(AccountRequest accountRequest) {
        AccountAggregate accountAggregate = new AccountAggregate();

        return userRepository.findById(accountRequest.getUserId())
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("User not found", "Create Account"));
                    return Mono.error(new RecordNotFoundException("User not found."));
                }))
                .map(UserMapper::mapToModelFromDTO)
                .flatMap(user -> {
                    accountAggregate.createAccount(
                            accountRequest.getAccountNumber(),
                            accountRequest.getBalance(),
                            accountRequest.getStatus(),
                            user
                    );

                    return accountRepository.save(AccountMapper.mapToDTOFromModel(accountAggregate.getAccount()))
                            .thenMany(Flux.fromIterable(accountAggregate.getUncommittedEvents()))
                            .flatMap(event -> Mono.fromCallable(() -> repository.save(event)))
                            .then(Mono.fromCallable(() -> {
                                accountAggregate.markEventsAsCommitted();
                                return AccountMapper.mapToResponseFromModel(accountAggregate.getAccount());
                            }));
                });

    }

}