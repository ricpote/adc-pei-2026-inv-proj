package pt.unl.fct.di.adc.firstwebapp.util;

import pt.unl.fct.di.adc.firstwebapp.model.Role;

public record UserSummaryData(String username, Role role) {
  public UserSummaryData {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be null or blank");
    }
    if (role == null) {
      throw new IllegalArgumentException("Role cannot be null");
    }
  }

}
