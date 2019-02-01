package me.perrino.demo.protobuff.ports.http;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestPortTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void givenRightBody_whenPost_thenDataIsProcessed() {
        Map<String, Object> data = Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("id", 1L);
                put("name", "Alfred");
            }
        });
        webTestClient.post().uri("/")
                .accept(APPLICATION_JSON_UTF8).body(Mono.just(data), Map.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8);
    }

    @Test
    public void givenWrongBody_whenPost_thenErrorIsReturned() {
        Map<String, Object> data = Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("id", "zero");
                put("name", "Alfred");
            }
        });
        webTestClient.post().uri("/")
                .accept(APPLICATION_JSON_UTF8).body(Mono.just(data), Map.class)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(APPLICATION_JSON_UTF8);
    }

}
