package com.crud.usuarios.controllers;

import com.crud.usuarios.models.Product;
import com.crud.usuarios.services.ProductService;
import com.crud.usuarios.services.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/obtener")
    public List<Product> getProducts() {

        return productService.findAll();
    }

    @GetMapping("/obetenerid/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        Optional<Product> product =productService.findById(id);

        if(product.isPresent()){
            Product productDb = product.get();
            return ResponseEntity.ok(productDb);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid  @RequestBody Product producto, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Product productoDb = productService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@Valid @RequestBody Product product, BindingResult result, Long id ){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        product.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        Optional<Product> producto = productService.findById(id);
        if(producto.isPresent()){
            Product productoDb= producto.get();
            productService.deleteById(productoDb.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> validation= new HashMap<>();
        result.getFieldErrors().forEach(err-> {
            validation.put(err.getField(), "El campo "+ err.getField()+ " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
    }

}
