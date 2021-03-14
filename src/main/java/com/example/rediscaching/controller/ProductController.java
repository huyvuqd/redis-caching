package com.example.rediscaching.controller;

import com.example.rediscaching.entity.Product;
import com.example.rediscaching.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @PostMapping
    public Product save(@RequestBody Product product){
        return productDao.save(product);
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productDao.findAll();
    }
    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    public Product getProductById(@PathVariable int id){
        return productDao.findById(id);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(key="#id",value = "Product")
    public String remove(@PathVariable int id){
        return productDao.delete(id);
    }
}
