package me.perrino.demo.protobuff.ports.data;

import me.perrino.demo.protobuff.ports.data.model.UserProtos;
import java.util.Optional;


public interface ProtoBuffRepository {

    boolean save(UserProtos.User user);
    Optional<UserProtos.User> findById(Integer id);

}