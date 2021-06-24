package com.target.myRetail.services;

import com.target.myRetail.controllers.ProductController;
import com.target.myRetail.entity.ProductPrice;
import com.target.myRetail.exception.InvalidPriceException;
import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.Price;
import com.target.myRetail.models.PriceInfo;
import com.target.myRetail.models.ProductInfo;
import com.target.myRetail.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductService {
    private Logger log= LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductController productController;

    @GetMapping("/products/{id}")
    private ProductInfo getProductDetails(@PathVariable("id") int id){
        ProductInfo productInfo = new ProductInfo();
        try {
            String title = productController.getProductTitle(id);
            if (null == title) {
                throw new ProductNotFoundException("NOT FOUND: Product title information not found");
            }
            Optional<ProductPrice> priceInfo = productRepository.findById(id);
            if (priceInfo == null) {
                throw new InvalidPriceException("Bad Request: Unable to find Price Information ");
            }
            Price price = new Price();

            if (priceInfo.isPresent()) {
                price.setValue(priceInfo.get().getPrice());
                price.setCurrency_code(priceInfo.get().getCurrency_code());
            }
            productInfo.setId(Integer.valueOf(id));
            productInfo.setName(title);
            productInfo.setCurrent_price(price);
        }catch(Exception e){
            log.error("Exception while calling getProductDetails : " + e);
        }
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
    private String updateProduct(@PathVariable("id") int id,@RequestBody ProductInfo productInfo){
        ProductPrice pp=new ProductPrice();
        pp.setProductId(id);
        try {
            if(productInfo!=null&&productInfo.getCurrent_price()!=null){
                pp.setPrice(productInfo.getCurrent_price().getValue());
                pp.setCurrency_code(productInfo.getCurrent_price().getCurrency_code());
                productRepository.save(pp);
            }else{
                throw new InvalidPriceException("Bad Request:Required fields missing in the request");
            }

        }catch(Exception ex){
            log.error("Exception while updating DB : " + ex);
        }

        return "updated product price with Id : "+pp.getProductId();
    }
}
