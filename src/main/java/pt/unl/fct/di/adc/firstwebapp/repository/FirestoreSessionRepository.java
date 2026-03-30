package pt.unl.fct.di.adc.firstwebapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;

import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;

public class FirestoreSessionRepository implements SessionRepository {

    private static final String KIND = "Session";
    private final Datastore datastore = DatastoreProvider.getDatastore();

    @Override
    public void save(AuthToken token) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(token.getTokenId());

        Entity entity = Entity.newBuilder(key)
                .set("tokenId", token.getTokenId())
                .set("username", token.getUsername())
                .set("role", token.getRole())
                .set("issuedAt", token.getIssuedAt())
                .set("expiresAt", token.getExpiresAt())
                .build();

        datastore.put(entity);
    }

    @Override
    public Optional<AuthToken> findByTokenId(String tokenId) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(tokenId);
        Entity entity = datastore.get(key);

        if (entity == null) {
            return Optional.empty();
        }

        AuthToken token = new AuthToken(
                entity.getString("tokenId"),
                entity.getString("username"),
                entity.getString("role"),
                entity.getLong("issuedAt"),
                entity.getLong("expiresAt")
        );

        return Optional.of(token);
    }

    @Override
    public List<AuthToken> findAll() {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(KIND).build();
        QueryResults<Entity> results = datastore.run(query);

        List<AuthToken> sessions = new ArrayList<>();
        while (results.hasNext()) {
            Entity entity = results.next();
            sessions.add(new AuthToken(
                    entity.getString("tokenId"),
                    entity.getString("username"),
                    entity.getString("role"),
                    entity.getLong("issuedAt"),
                    entity.getLong("expiresAt")
            ));
        }
        return sessions;
    }

    @Override
    public void delete(String tokenId) {
        Key key = datastore.newKeyFactory().setKind(KIND).newKey(tokenId);
        datastore.delete(key);
    }

    @Override
    public void deleteByUserId(String username) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND)
                .setFilter(StructuredQuery.PropertyFilter.eq("username", username))
                .build();

        QueryResults<Entity> results = datastore.run(query);

        List<Key> keys = new ArrayList<>();
        while (results.hasNext()) {
            keys.add(results.next().getKey());
        }

        if (!keys.isEmpty()) {
            datastore.delete(keys.toArray(new Key[0]));
        }
    }
}