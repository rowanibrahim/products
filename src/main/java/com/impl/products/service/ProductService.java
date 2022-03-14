package com.impl.products.service;

import com.impl.products.dto.ProductRequestBody;
import com.impl.products.dto.RequestParams;
import com.impl.products.helper.ExcelHelper;
import com.impl.products.model.product.Product;
import com.impl.products.model.user.User;
import com.impl.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    @Value("${role.admin}")
    private String roleAdmin;

    public List<Product> getAllProducts(User user, RequestParams params){
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());
        List<Product> products = new ArrayList<Product>();
        repository.findAll(pageable).forEach(product -> {
            products.add(product);
        });
        return products;
    }
    public void save(MultipartFile file) {
        try {
            List<Product> products = ExcelHelper.excelToProducts(file.getInputStream());
            repository.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
    public void activateProduct(long productId, ProductRequestBody body){
        Optional<Product> optional = repository.findById(productId);
        if(optional.isPresent()){
            Product product = optional.get();
            product.setActive(body.isActive());
            repository.save(product);
        }else{
            throw new RuntimeException("Product not found!");
        }

    }
}
