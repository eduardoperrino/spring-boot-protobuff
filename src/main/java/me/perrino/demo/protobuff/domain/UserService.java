package me.perrino.demo.protobuff.domain;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);

}
