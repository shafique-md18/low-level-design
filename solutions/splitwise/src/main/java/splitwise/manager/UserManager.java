package splitwise.manager;

import splitwise.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public User addUser(String name, String email) {
        User user = new User(UUID.randomUUID().toString(), name, email);
        users.put(user.getId(), user);
        return user;
    }
}
