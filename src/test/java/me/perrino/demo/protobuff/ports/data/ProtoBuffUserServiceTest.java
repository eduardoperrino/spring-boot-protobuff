package me.perrino.demo.protobuff.ports.data;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.perrino.demo.protobuff.domain.User;
import me.perrino.demo.protobuff.domain.UserService;
import me.perrino.demo.protobuff.ports.data.model.UserProtos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@NoArgsConstructor
@Slf4j
public class ProtoBuffUserServiceTest {

    @TestConfiguration
    static class ProtoBuffUserServiceTestContextConfiguration {
        @Bean
        public ProtoBuffUserService protoBuffUserService() { return new ProtoBuffUserService(); }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private ProtoBuffRepository protoBuffRepository;


    @Test
    public void givenUser_whenSaveIt_thenError() {
        User user = User.builder().id(1).name("Lucas").build();
        Mockito.when(protoBuffRepository.save(any(UserProtos.User.class)))
                .thenReturn(false);
        Mono<User> result = userService.save(user);
        assertThat(result.block()).isNull();
    }

    @Test
    public void givenUser_whenSaveIt_thenSuccess() {
        User user = User.builder().id(1).name("Lucas").build();
        Mockito.when(protoBuffRepository.save(any(UserProtos.User.class)))
                .thenReturn(true);
        Mono<User> result = userService.save(user);
        assertThat(result.block()).isNotNull();
    }

}
