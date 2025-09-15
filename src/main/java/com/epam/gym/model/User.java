package com.epam.gym.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected UUID id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    protected boolean isActive;
}
