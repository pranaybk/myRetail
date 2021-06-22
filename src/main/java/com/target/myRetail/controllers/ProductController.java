package com.target.myRetail.controllers;

import com.target.myRetail.models.JsonTemplete;
import com.target.myRetail.models.ProductInfo;
import com.target.myRetail.services.ProductService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Configuration
@PropertySource(value="classpath:application-${spring.profiles.active}.yml")
public class ProductController {
    private Logger log= LoggerFactory.getLogger(ProductController.class);


    @Value("${application.myRetail.apiUrl}")
    private String apiURL;

    @Value("${application.myRetail.excludes}")
    private String apiExcludes;

    public ProductInfo getProductDetails(String id) {
        log.info("apiURL "+apiURL+" apiExcludes "+apiExcludes);
        String completeURL=apiURL+id+apiExcludes;
        log.info("Complete URL "+completeURL);
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.getForObject(completeURL,String.class);
        log.info("Complete Text "+result);
        return null;
    }
}
