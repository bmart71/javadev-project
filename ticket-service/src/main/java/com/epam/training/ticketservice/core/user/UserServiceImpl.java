package com.epam.training.ticketservice.core.user;

import exceptions.NoAuthorizationException;
import exceptions.UnauthorizedMethodException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDTO currentUser = null;

    @Override
    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        currentUser = new UserDTO(user.get().getUsername(), user.get().getRole());
        return describe();
    }

    @Override
    public Optional<UserDTO> logout() {
        Optional<UserDTO> target = describe();
        currentUser = null;
        return target;
    }

    @Override
    public Optional<UserDTO> describe() {
        return Optional.ofNullable(currentUser);
    }

    @Override
    public void register(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() throws NoAuthorizationException, UnauthorizedMethodException {
        if (currentUser == null) {
            throw new NoAuthorizationException();
        }
        if (currentUser.role() != User.Role.ADMIN) {
            throw new UnauthorizedMethodException();
        }
        return (List<User>) userRepository.findAll();
    }

    @Override
    public boolean isAdmin() {
        if (currentUser == null) {
            return false;
        }
        return currentUser.role() == User.Role.ADMIN;
    }
}
