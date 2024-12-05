package com.epam.training.ticketservice.core.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> login(String username, String password);
    Optional<UserDTO> logout();
    Optional<UserDTO> describe();
    void register(String username, String password);
    List<User> getUsers() throws IllegalAccessException;
    boolean isAdmin();
    String toString();
}
