package pt.unl.fct.di.adc.firstwebapp.service;

import pt.unl.fct.di.adc.firstwebapp.repository.FireStoreUserRepository;
import pt.unl.fct.di.adc.firstwebapp.repository.FirestoreSessionRepository;
import pt.unl.fct.di.adc.firstwebapp.repository.SessionRepository;
import pt.unl.fct.di.adc.firstwebapp.repository.UserRepository;

public class ServiceRegistry {

    private static final UserRepository userRepository = new FireStoreUserRepository();
    private static final SessionRepository sessionRepository = new FirestoreSessionRepository();
    private static final UserService userService = new UserService(userRepository, sessionRepository);

    public static UserService userService() {
        return userService;
    }
}
