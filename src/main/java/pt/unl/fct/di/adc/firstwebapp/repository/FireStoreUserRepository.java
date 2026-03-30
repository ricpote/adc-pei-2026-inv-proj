package pt.unl.fct.di.adc.firstwebapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import pt.unl.fct.di.adc.firstwebapp.model.Role;
import pt.unl.fct.di.adc.firstwebapp.model.User;



public class FireStoreUserRepository implements UserRepository {

    private static final String KIND = "User";
    private final Datastore datastore = DatastoreProvider.getDatastore();

    @Override
    public void save(User user) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(user.getUsername());

        Entity entity = Entity.newBuilder(key)
                .set("username", user.getUsername())
                .set("password", user.getPassword())
                .set("email", user.getEmail() == null ? "" : user.getEmail())
                .set("phone", user.getPhone() == null ? "" : user.getPhone())
                .set("address", user.getAddress() == null ? "" : user.getAddress())
                .set("role", user.getRole().name())
                .build();

        datastore.put(entity);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return findByUsername(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(username);
        Entity entity = datastore.get(key);

        if (entity == null) {
            return Optional.empty();
        }

        User user = new User(
                entity.getString("username"),
                entity.getString("username"),
                entity.getString("password"),
                entity.getString("email"),
                entity.getString("phone"),
                entity.getString("address"),
                Role.fromString(entity.getString("role")));

        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(KIND).build();
        QueryResults<Entity> results = datastore.run(query);

        List<User> users = new ArrayList<>();
        while (results.hasNext()) {
            Entity entity = results.next();
            users.add(new User(
                    entity.getString("username"),
                    entity.getString("username"),
                    entity.getString("password"),
                    entity.getString("email"),
                    entity.getString("phone"),
                    entity.getString("address"),
                    Role.fromString(entity.getString("role"))));
        }
        return users;
    }

    @Override
    public void delete(String userId) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(userId);
        datastore.delete(key);
    }

    @Override
    public void update(User user) {
        save(user);
    }
}