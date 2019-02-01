package me.perrino.demo.protobuff.ports.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.perrino.demo.protobuff.domain.User;
import me.perrino.demo.protobuff.domain.UserService;
import me.perrino.demo.protobuff.ports.data.model.UserProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProtoBuffUserService implements UserService {

    @Autowired
    private ProtoBuffRepository protoBuffRepository;

    @Override
    public Mono<User> save(User user) {
        return protoBuffRepository.save(ProtoBuffConverter.convertToProtos(user)) ?  Mono.just(user) : Mono.empty();
    }


    @AllArgsConstructor
    @Data
    private static class ProtoBuffConverter {

        static UserProtos.User convertToProtos(User user) {
            return UserProtos.User.newBuilder().setId(user.getId()).setName(user.getName()).build();
        }

        static User convertFromProtos(UserProtos.User user) {
            return User.builder().id(user.getId()).name(user.getName()).build();
        }

    }


}
