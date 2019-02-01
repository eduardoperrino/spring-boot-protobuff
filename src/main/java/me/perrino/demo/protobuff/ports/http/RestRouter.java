package me.perrino.demo.protobuff.ports.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RestRouter {

    @Bean
    public RouterFunction<ServerResponse> route(RestHandler restHandler) {
        return RouterFunctions
                .route(POST("/").and(accept(APPLICATION_JSON)), restHandler::save);
    }
}
