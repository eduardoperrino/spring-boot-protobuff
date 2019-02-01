package me.perrino.demo.protobuff.ports.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.perrino.demo.protobuff.domain.User;
import me.perrino.demo.protobuff.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class RestHandler {

    @Autowired
    private UserService userService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .map(UserRequest::toUser)
                .flatMap(userService::save)
                .flatMap(t -> ok().contentType(APPLICATION_JSON_UTF8).body(Mono.just(t), User.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build());
    }

    @AllArgsConstructor
    @Data
    private static class UserRequest {

        private Integer id;
        private String name;


        User toUser() {
            return User.builder().id(this.id).name(this.name).build();
        }


    }
}
