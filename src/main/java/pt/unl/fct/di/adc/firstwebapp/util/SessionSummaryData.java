package pt.unl.fct.di.adc.firstwebapp.util;

import pt.unl.fct.di.adc.firstwebapp.model.Role;

public record SessionSummaryData(String sessionId, String username, Role role, long expiresAt) {
    public SessionSummaryData {
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("Session ID cannot be null or blank");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (expiresAt <= 0) {
            throw new IllegalArgumentException("Expiration time must be a positive value");
        }
    }
}