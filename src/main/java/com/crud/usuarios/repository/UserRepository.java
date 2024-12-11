package com.crud.usuarios.repository;

import com.crud.usuarios.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
