package com.curso.springboot.app.springboot_crud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.springboot_crud.entities.Product;
import com.curso.springboot.app.springboot_crud.services.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable Long id) {
        
        Optional<Product> optionalProduct = productService.findById(id);
        
        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }

        Map<String, Object> res = new HashMap<>();
        res.put("message", "No se pudo encontrar el producto.");
        res.put("status", 404);
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping()
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product productSaved = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {

        Optional<Product> optionalProductUpdated = productService.update(id, product);

        if (optionalProductUpdated.isPresent()) {
            Product productUpdated = optionalProductUpdated.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(productUpdated);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);

        Optional<Product> optionalProduct = productService.delete(product);
        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }
}
