package pt.unl.fct.di.adc.firstwebapp.util;

import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;
import pt.unl.fct.di.adc.firstwebapp.model.Role;

import java.util.UUID;

public class TokenUtil {

    private static final long TOKEN_DURATION_MS = 1000L * 60 * 60 * 2; // 2 hours

    private TokenUtil() {
    }

    public static AuthToken generateToken(String userId, Role role) {
        long now = System.currentTimeMillis();

        return new AuthToken(
                UUID.randomUUID().toString(),
                userId,
                role.name(),
                now,
                now + TOKEN_DURATION_MS);
    }
}