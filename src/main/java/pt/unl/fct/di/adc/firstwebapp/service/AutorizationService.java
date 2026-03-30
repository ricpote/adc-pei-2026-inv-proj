package pt.unl.fct.di.adc.firstwebapp.service;

import pt.unl.fct.di.adc.firstwebapp.model.Role;
import pt.unl.fct.di.adc.firstwebapp.model.User;

public class AutorizationService {
    public AutorizationService() {
    }

    public boolean canShowUsers(Role role) {
        return role == Role.ADMIN || role == Role.BOFFICER;
    }

    public boolean canDeleteAccount(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canShowAuthenticatedSessions(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canShowUserRole(Role role) {
        return role == Role.ADMIN || role == Role.BOFFICER;
    }

    public boolean canChangeUserRole(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canModifyAccount(User requester, User target) {
        if (requester.getRole() == Role.ADMIN)
            return true;

        if (requester.getRole() == Role.USER) {
            return requester.getUserId().equals(target.getUserId());
        }

        if (requester.getRole() == Role.BOFFICER) {
            return requester.getUserId().equals(target.getUserId())
                    || target.getRole() == Role.USER;
        }

        return false;
    }

    public boolean canChangePassword(User requester, User target) {
        if (requester.getRole() == Role.ADMIN) {
            return true;
        }
        return requester.getUsername().equals(target.getUsername());
    }

    public boolean canLogout(User requester, String targetUserId) {
        if (requester.getRole() == Role.ADMIN)
            return true;
        return requester.getUserId().equals(targetUserId);
    }
}