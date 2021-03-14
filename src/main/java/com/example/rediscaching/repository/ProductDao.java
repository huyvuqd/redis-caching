package com.example.rediscaching.repository;

import com.example.rediscaching.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    public static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate template;
    public Product save(Product product){
        template.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }
    public List<Product> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }
    public Product findById(int id){
        System.out.println("findById from DB");
        return (Product) template.opsForHash().get(HASH_KEY,id);
    }
    public String delete(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "Product deleted";
    }
}
