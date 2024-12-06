package com.epam.training.ticketservice.core.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;

    private String password;

    private Role role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public enum Role{
        ADMIN,
        USER
    }
}
