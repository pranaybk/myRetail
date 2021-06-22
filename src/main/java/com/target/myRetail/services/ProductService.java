package com.target.myRetail.services;

import com.target.myRetail.controllers.ProductController;
import com.target.myRetail.entity.ProductPrice;
import com.target.myRetail.models.ProductInfo;
import com.target.myRetail.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductService {
    private Logger log= LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductController productController;

    @GetMapping("/products/{id}")
    private ProductInfo getProductDetails(@PathVariable("id") String id){
        log.info("Value from API "+id);
        ProductInfo productInfo=productController.getProductDetails(id);
        return productInfo;
    }

    @GetMapping("/products")
    private List<ProductPrice> getProductDetailsTemp(){
        return productRepository.findAll();
    }

    @PostMapping("/addProduct")
    private String saveProduct(@RequestBody ProductPrice productPrice){
        log.info("Request Schema : "+productPrice);
        productRepository.save(productPrice);
        return "Added product with Id : "+productPrice.getProductId();
    }

    @PutMapping("/products/{id}")
    private String updateProduct(@RequestBody ProductPrice productPrice){
        log.info("Request Schema : "+productPrice);
        productRepository.save(productPrice);
        return "Added product with Id : "+productPrice.getProductId();
    }
}
