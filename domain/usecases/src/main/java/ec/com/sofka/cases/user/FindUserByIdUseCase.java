package ec.com.sofka.cases.user;

import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Mono;

public class FindUserByIdUseCase {
    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserResponse> apply(String id) {
        return userRepository.findById(id)
                .map(UserMapper::mapToResponseFromDTO);
    }

}