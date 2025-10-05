package hexagonalArchitecture.adapters;

import hexagonalArchitecture.ports.UserRepository;
import hexagonalArchitecture.ports.UserService;
import hexagonalArchitecture.domain.User;

public class UserServiceImpl implements UserService {
    private final UserRepository users;
    private int counter = 0;

    public UserServiceImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public User registerUser(String userName) {
        counter++;
        String id = "user-" + counter;
        User user = new User(id, userName);
        users.save(user);
        return user;
    }
}
