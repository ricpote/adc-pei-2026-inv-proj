package pt.unl.fct.di.adc.firstwebapp.service;

import pt.unl.fct.di.adc.firstwebapp.exception.ApiException;
import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;
import pt.unl.fct.di.adc.firstwebapp.model.ErrorCode;
import pt.unl.fct.di.adc.firstwebapp.repository.SessionRepository;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public AuthToken validateToken(AuthToken inputToken) {
        if (inputToken == null || inputToken.getTokenId() == null || inputToken.getTokenId().isBlank()) {
            throw new ApiException(ErrorCode.INVALID_TOKEN);
        }

        AuthToken storedToken = sessionRepository.findByTokenId(inputToken.getTokenId())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_TOKEN));

        if (storedToken.getExpiresAt() < System.currentTimeMillis()) {
            throw new ApiException(ErrorCode.TOKEN_EXPIRED);
        }

        return storedToken;
    }

    public void saveSession(AuthToken token) {
        sessionRepository.save(token);
    }

    public void deleteSession(String tokenId) {
        sessionRepository.delete(tokenId);
    }

    public void deleteSessionsByUserId(String userId) {
        sessionRepository.deleteByUserId(userId);
    }
}