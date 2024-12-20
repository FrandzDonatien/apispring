package frandz.api_test.service.impl;

import frandz.api_test.model.User;

import java.util.Optional;

public interface UserServiceImpl {
    Optional<User> getByEmail(String email);
}
