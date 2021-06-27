package com.target.myRetail.controller;

import com.target.myRetail.entity.ProductPrice;
import com.target.myRetail.models.ProductInfo;
import com.target.myRetail.repositories.ProductRepository;
import com.target.myRetail.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class ProductDetailsController {
    private Logger log= LoggerFactory.getLogger(ProductDetailsController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productsService;

    @GetMapping("products/{id}")
    private ProductInfo getProductDetails(@PathVariable("id") int id){
        log.info("Product Id Rquested : "+id);
        ProductInfo productInfo=productsService.getProductDetails(id);
        return productInfo;
    }

    //Add price docs in mango DB
    @PostMapping("addProduct")
    private String saveProduct(@RequestBody ProductPrice productPrice){
        log.info("Request Schema : "+productPrice);
        productRepository.save(productPrice);
        return "Added product with Id : "+productPrice.getProductId();
    }

    @PutMapping("products/{id}")
    private String updateProduct(@PathVariable("id") int id,@RequestBody ProductInfo productInfo){
        log.info(" Id requested for Update: "+id);
        String productMessage=productsService.updateProduct(id,productInfo);
        return productMessage;
    }
}
