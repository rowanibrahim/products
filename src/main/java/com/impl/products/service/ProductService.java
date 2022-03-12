package com.impl.products.service;

import com.impl.products.model.product.Product;
import com.impl.products.model.user.User;
import com.impl.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<Product>();
        repository.findAll().forEach(user -> products.add(user));
        return products;
    }
}
