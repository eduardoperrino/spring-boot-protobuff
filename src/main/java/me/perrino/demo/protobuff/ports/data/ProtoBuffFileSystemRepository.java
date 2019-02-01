package me.perrino.demo.protobuff.ports.data;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import me.perrino.demo.protobuff.ports.data.model.UserProtos;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProtoBuffFileSystemRepository implements ProtoBuffRepository {

    // Only for demo purposes
    private Map<Integer, String> index = new HashMap<>();

    @Override
    public boolean save(UserProtos.User user) {
        return writeToDisk(user).onSuccess( t -> index.put(user.getId(), t)).onFailure( t ->
                log.error("Failed due to {}", t.getMessage())).isSuccess();
    }

    @Override
    public Optional<UserProtos.User> findById(Integer id) {
        return Optional.ofNullable(readFromDisk(index.get(id)).onFailure(t ->
                log.error("Failed due to {}", t.getMessage())).getOrNull());
    }


    private Try<String> writeToDisk(UserProtos.User user) {
        String prefix = "user_";
        String suffix = ".tmp";
        return Try.of(() -> {
            File tempFile = File.createTempFile(prefix, suffix);
            FileOutputStream fos = new FileOutputStream(tempFile);
            user.writeTo(fos);
            return tempFile.getCanonicalPath();
        });
    }


    private Try<UserProtos.User> readFromDisk(String filePath) {
        return Try.of(() -> UserProtos.User.newBuilder().mergeFrom(new FileInputStream(filePath)).build());
    }


}
