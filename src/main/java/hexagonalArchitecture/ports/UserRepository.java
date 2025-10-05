package hexagonalArchitecture.ports;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hexagonalArchitecture.domain.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
}
