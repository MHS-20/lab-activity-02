package hexagonalArchitecture.ports;

import hexagonalArchitecture.domain.User;

public interface UserService {
    User registerUser(String userName);

}
