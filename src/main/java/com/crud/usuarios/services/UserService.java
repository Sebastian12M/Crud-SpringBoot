package com.crud.usuarios.services;

import com.crud.usuarios.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);


}
