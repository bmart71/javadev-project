package com.epam.training.ticketservice.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDTO currentUser = null;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
