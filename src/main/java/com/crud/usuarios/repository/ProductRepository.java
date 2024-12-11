package com.crud.usuarios.repository;

import com.crud.usuarios.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
