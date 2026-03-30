package pt.unl.fct.di.adc.firstwebapp.repository;

import pt.unl.fct.di.adc.firstwebapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void delete(String userId);

    void update(User user);
}