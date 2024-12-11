package com.crud.usuarios.services;

import com.crud.usuarios.models.Role;
import com.crud.usuarios.models.User;
import com.crud.usuarios.repository.RoleRepository;
import com.crud.usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private RoleRepository roleRepository;

    //Inyecto la configuracion para encriiptar la password
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        Optional<Role> roles = roleRepository.findByName("ROLE_USER");
        List<Role> list_roles= new ArrayList<>();

        if(roles.isPresent()) {
            list_roles.add(roles.orElseThrow());

        }

        if(user.isAdmin()){
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole.isPresent()){
                list_roles.add(adminRole.orElseThrow());
            }

        }

        //encripto la password con la configuracion ya hecha de spring security
        String passwordEncrypted = passwordEncoder.encode(user.getPassword());
        //asigno la password ya encriptada
        user.setPassword(passwordEncrypted);

        return userRepository.save(user);
    }
}
