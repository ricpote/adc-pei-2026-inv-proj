package pt.unl.fct.di.adc.firstwebapp.repository;

import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    void save(AuthToken token);

    Optional<AuthToken> findByTokenId(String tokenId);

    List<AuthToken> findAll();

    void delete(String tokenId);

    void deleteByUserId(String userId);
}