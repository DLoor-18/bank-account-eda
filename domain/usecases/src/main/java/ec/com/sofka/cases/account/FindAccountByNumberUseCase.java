package ec.com.sofka.cases.account;

import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Mono;

public class FindAccountByNumberUseCase {
    private final AccountRepository accountRepository;

    public FindAccountByNumberUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<AccountResponse> apply(String number) {
        return accountRepository.findByNumber(number)
                .map(AccountMapper::mapToResponseFromDTO);
    }

}