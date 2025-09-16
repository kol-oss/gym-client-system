package com.epam.gym.service;

import com.epam.gym.model.User;

public interface UserService {
    User preCreateUser(User user);

    User preUpdateUser(User user);
}
