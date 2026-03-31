package pt.unl.fct.di.adc.firstwebapp.util;

import pt.unl.fct.di.adc.firstwebapp.model.Role;

public record SessionSummaryData(String sessionId, String username, Role role, long expiresAt) {
    public SessionSummaryData {
   
    }
}