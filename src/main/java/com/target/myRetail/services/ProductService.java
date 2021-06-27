package com.target.myRetail.services;

import com.target.myRetail.controllers.ProductController;
import com.target.myRetail.entity.ProductPrice;
import com.target.myRetail.exception.InvalidPriceException;
import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.Price;
import com.target.myRetail.models.PriceInfo;
import com.target.myRetail.models.ProductInfo;
import com.target.myRetail.repositories.ProductRepository;
import org.json.JSONObject;
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

    //Add price docs in mango DB
    @PostMapping("/addProduct")
    private String saveProduct(@RequestBody ProductPrice productPrice){
        log.info("Request Schema : "+productPrice);
        productRepository.save(productPrice);
        return "Added product with Id : "+productPrice.getProductId();
    }

    @PutMapping("/products/{id}")
    private String updateProduct(@PathVariable("id") int id,@RequestBody ProductInfo productInfo){
        ProductPrice pp=new ProductPrice();
        JSONObject jObject = new JSONObject();
        pp.setProductId(id);
        try {
            if(productInfo==null||id!=productInfo.getId()){
                jObject.put("ErrorCode", "410");
                jObject.put("ErrorResponse", "ids did not match");
                return jObject.toString();
            }
            if(productInfo!=null&&productInfo.getCurrent_price()!=null){
                pp.setPrice(productInfo.getCurrent_price().getValue());
                pp.setCurrency_code(productInfo.getCurrent_price().getCurrency_code());
                productRepository.save(pp);
            }else{
                jObject.put("ErrorCode", "411");
                jObject.put("ErrorResponse", "Price value is empty");
                return jObject.toString();

            }

        }catch(Exception ex){
            throw new InvalidPriceException("Bad Request: "+ex);
        }

        jObject.put("Sucess", "200");
        jObject.put("SucessResponse", "Price value is updated for Id "+productInfo.getId());
        return jObject.toString();
    }
}
