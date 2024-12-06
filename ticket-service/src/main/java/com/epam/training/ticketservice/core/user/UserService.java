package com.epam.training.ticketservice.core.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> login(String username, String password);
    Optional<UserDTO> logout();
    Optional<UserDTO> describe();
    void register(User user);
    List<User> getUsers();
    String toString();
}
