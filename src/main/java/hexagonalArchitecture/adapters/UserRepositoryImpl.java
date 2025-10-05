package hexagonalArchitecture.adapters;

import hexagonalArchitecture.domain.User;
import hexagonalArchitecture.ports.UserRepository;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> storage = new HashMap<>();
    private final File dbFile;
    private static final Logger log = Logger.getLogger("UserRepo");

    public UserRepositoryImpl(String fileName) {
        this.dbFile = new File(fileName);
        load();
    }

    @Override
    public void save(User user) {
        storage.put(user.id(), user);
        persist();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    private void load() {
        if (!dbFile.exists()) {
            persist();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(line -> sb.append(line).append("\n"));
            JsonArray arr = new JsonArray(sb.toString());
            for (int i = 0; i < arr.size(); i++) {
                JsonObject o = arr.getJsonObject(i);
                storage.put(o.getString("userId"), new User(o.getString("userId"), o.getString("userName")));
            }
        } catch (Exception e) {
            log.warning("Failed to load users: " + e.getMessage());
        }
    }

    private void persist() {
        try (FileWriter writer = new FileWriter(dbFile)) {
            JsonArray arr = new JsonArray();
            for (User u : storage.values()) {
                arr.add(new JsonObject()
                        .put("userId", u.id())
                        .put("userName", u.name()));
            }
            writer.write(arr.encodePrettily());
        } catch (Exception e) {
            log.warning("Failed to persist users: " + e.getMessage());
        }
    }
}
