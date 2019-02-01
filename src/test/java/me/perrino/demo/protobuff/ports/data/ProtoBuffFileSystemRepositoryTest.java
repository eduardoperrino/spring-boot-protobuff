package me.perrino.demo.protobuff.ports.data;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.perrino.demo.protobuff.ports.data.model.UserProtos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@NoArgsConstructor
@Slf4j
public class ProtoBuffFileSystemRepositoryTest {

    @TestConfiguration
    static class ProtoBuffFileSystemRepositoryTestContextConfiguration {
        @Bean
        public ProtoBuffRepository protoBuffRepository() {
            return new ProtoBuffFileSystemRepository();
        }
    }

    @Autowired
    private ProtoBuffRepository protoBuffRepository;

    @Test
    public void givenUser_whenSaveIt_thenReturnSuccess() {
        UserProtos.User user = UserProtos.User.newBuilder().setId(1).setName("Lucas").build();
        assertThat(protoBuffRepository.save(user)).isTrue();
    }


    @Test
    public void givenUser_whenFindIt_thenReturnTheUser() {
        Integer id = 1;
        UserProtos.User user = UserProtos.User.newBuilder().setId(id).setName("Lucas").build();
        assertThat(protoBuffRepository.save(user)).isTrue();
        UserProtos.User readUser = protoBuffRepository.findById(id).orElse(null);
        assertThat(readUser).isNotNull();
        assertThat(readUser.getId()).isEqualTo(user.getId());
        assertThat(readUser.getName()).isEqualTo(user.getName());
    }

    @Test
    public void givenWrongUser_whenFindIt_thenErrorIsReturned() {
        UserProtos.User readUser = protoBuffRepository.findById(1).orElse(null);
        assertThat(readUser).isNull();
    }
}
