package com.epam.training.ticketservice.core.entity;

import com.epam.training.ticketservice.core.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

    private static final User SAMPLE_USER = new User("test", "password");

    @Test
    void testUserUsernameGetter() {
        //Given
        String expected = "test";
        //When
        String actual = SAMPLE_USER.getUsername();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testUserRoleGetter() {
        //Given
        User.Role expected = User.Role.USER;
        //When
        User.Role actual = SAMPLE_USER.getRole();
        //Then
        assertEquals(expected, actual);
    }
}
