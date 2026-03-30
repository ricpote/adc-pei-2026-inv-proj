package pt.unl.fct.di.adc.firstwebapp.service;

import pt.unl.fct.di.adc.firstwebapp.dto.CreateAccountData;
import pt.unl.fct.di.adc.firstwebapp.exception.ApiException;
import pt.unl.fct.di.adc.firstwebapp.model.ErrorCode;
import pt.unl.fct.di.adc.firstwebapp.model.Role;
import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;
import pt.unl.fct.di.adc.firstwebapp.model.User;
import pt.unl.fct.di.adc.firstwebapp.repository.SessionRepository;
import pt.unl.fct.di.adc.firstwebapp.repository.UserRepository;
import pt.unl.fct.di.adc.firstwebapp.util.PasswordUtil;
import pt.unl.fct.di.adc.firstwebapp.util.SessionSummaryData;
import pt.unl.fct.di.adc.firstwebapp.util.UserSummaryData;
import pt.unl.fct.di.adc.firstwebapp.util.ValidationUtil;
import pt.unl.fct.di.adc.firstwebapp.dto.LoginData;
import pt.unl.fct.di.adc.firstwebapp.util.TokenUtil;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final AutorizationService autorizationService;
    private final SessionService sessionService;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.autorizationService = new AutorizationService();
        this.sessionService = new SessionService(sessionRepository);
    }

    public Map<String, Object> createAccount(CreateAccountData input) {
        ValidationUtil.requireNotBlank(input.getUsername());
        ValidationUtil.requireNotBlank(input.getPassword());
        ValidationUtil.requireNotBlank(input.getConfirmation());
        ValidationUtil.requireNotBlank(input.getRole());

        ValidationUtil.require(
                input.getPassword().equals(input.getConfirmation()),
                ErrorCode.INVALID_INPUT);

        if (userRepository.findByUsername(input.getUsername()).isPresent()) {
            throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Role role;
        try {
            role = Role.fromString(input.getRole());
        } catch (Exception e) {
            throw new ApiException(ErrorCode.INVALID_INPUT);
        }

        User user = new User(
                input.getUsername(),
                input.getUsername(),
                input.getPassword(),
                input.getEmail(),
                input.getPhone(),
                input.getAddress(),
                role);

        userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getUserId());
        result.put("username", user.getUsername());
        result.put("role", user.getRole().name());

        return result;
    }

    public Map<String, Object> login(LoginData input) {
        ValidationUtil.requireNotBlank(input.getUsername());
        ValidationUtil.requireNotBlank(input.getPassword());

        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        boolean validPassword = input.getPassword().equals(user.getPassword());

        if (!validPassword) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }

        AuthToken token = TokenUtil.generateToken(user.getUserId(),
                user.getRole());

        sessionRepository.save(token);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        return result;
    }

    public List<UserSummaryData> ShowUsers(AuthToken token) {

        AuthToken storedToken = sessionService.validateToken(token);
        Role role = Role.fromString(storedToken.getRole());
        if (autorizationService.canShowUsers(role)) {

            return userRepository.findAll().stream()
                    .map(user -> new UserSummaryData(user.getUsername(), user.getRole()))
                    .collect(Collectors.toList());
        } else {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }
    }

    public UserSummaryData ShowUserRole(AuthToken token, String username) {

        AuthToken storedToken = sessionService.validateToken(token);
        Role role = Role.fromString(storedToken.getRole());

        if (autorizationService.canShowUserRole(role)) {

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
            return new UserSummaryData(user.getUsername(), user.getRole());
        } else {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }
    }

    public List<SessionSummaryData> showAuthenticatedSessions(AuthToken token) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role role = Role.fromString(storedToken.getRole());

        if (!autorizationService.canShowAuthenticatedSessions(role)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        return sessionRepository.findAll().stream()
                .map(session -> new SessionSummaryData(
                        session.getTokenId(),
                        session.getUsername(),
                        Role.fromString(session.getRole()),
                        session.getExpiresAt()))
                .collect(Collectors.toList());
    }

    public String deleteAccount(AuthToken token, String username) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role role = Role.fromString(storedToken.getRole());

        if (!autorizationService.canDeleteAccount(role)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user.getUserId());
        sessionRepository.deleteByUserId(user.getUsername());
        return "Account deleted successfully";

    }

    public String changeUserRole(AuthToken token, String username, String newRole) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role requesterRole = Role.fromString(storedToken.getRole());

        if (!autorizationService.canChangeUserRole(requesterRole)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Role updatedRole;
        try {
            updatedRole = Role.fromString(newRole);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.INVALID_INPUT);
        }
        user.setRole(updatedRole);
        userRepository.update(user);

        return "Role updated successfully";
    }

    public String modifyAccountAtributes(AuthToken token, String username, Map<String, String> updates) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role requesterRole = Role.fromString(storedToken.getRole());

        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (!autorizationService.canModifyAccount(
                new User(storedToken.getUsername(), null, null, null, null, null, requesterRole),
                targetUser)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }
        if (updates.containsKey("username")) {
            throw new ApiException(ErrorCode.INVALID_INPUT);
        }

        if (updates.containsKey("email")) {
            targetUser.setEmail(updates.get("email"));
        }
        if (updates.containsKey("phone")) {
            targetUser.setPhone(updates.get("phone"));
        }
        if (updates.containsKey("address")) {
            targetUser.setAddress(updates.get("address"));
        }

        userRepository.update(targetUser);
        return "Updated successfully";
    }

    public String changePassword(AuthToken token, String username, String oldPassWord, String newPassword) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role requesterRole = Role.fromString(storedToken.getRole());

        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        if (!targetUser.getPassword().equals(oldPassWord)) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }
        if (!autorizationService.canChangePassword(
                new User(storedToken.getUsername(), storedToken.getUsername(), oldPassWord, null, null, null,
                        requesterRole),
                targetUser)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        targetUser.setPassword(newPassword);
        userRepository.update(targetUser);
        return "Password changed successfully";
    }

    public String logout(AuthToken token, String username) {
        AuthToken storedToken = sessionService.validateToken(token);
        Role requesterRole = Role.fromString(storedToken.getRole());

        if (!autorizationService.canLogout(
                new User(storedToken.getUsername(), null, null, null, null, null, requesterRole),
                username)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        sessionRepository.deleteByUserId(username);
        return "Logged out successfully";
    }

}
