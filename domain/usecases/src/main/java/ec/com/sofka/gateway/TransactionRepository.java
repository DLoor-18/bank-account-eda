package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    Mono<TransactionDTO> save(TransactionDTO transaction);

    Flux<TransactionDTO> findAll();

}