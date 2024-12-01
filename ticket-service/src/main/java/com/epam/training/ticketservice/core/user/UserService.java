package com.epam.training.ticketservice.core.user;

import java.util.List;

public interface UserService {

    void register(String username, String password);
    List<User> getUsers();
}
