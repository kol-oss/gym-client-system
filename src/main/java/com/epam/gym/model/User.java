package com.epam.gym.model;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class User {
    protected UUID id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    protected boolean isActive;
}
