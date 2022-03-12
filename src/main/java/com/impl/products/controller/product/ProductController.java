package com.impl.products.controller.product;

import com.impl.products.controller.security.AbstractController;
import com.impl.products.model.product.Product;
import com.impl.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController {
    @Autowired
    ProductService service;
    @GetMapping("/products")
    private List<Product> getAllProducts() {
        return service.getAllProducts();
    }

}
