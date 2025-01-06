package ec.com.sofka.cases.user;

import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Mono;

public class FindUserByCiUseCase {
    private final UserRepository userRepository;

    public FindUserByCiUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserResponse> apply(String ci) {
        return userRepository.findByCi(ci)
                .map(UserMapper::mapToResponseFromDTO);
    }

}