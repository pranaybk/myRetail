package com.target.myRetail.controllers;

import com.target.myRetail.exception.ProductNotFoundException;
import lombok.*;
import org.bson.json.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

    public String getProductTitle(int id) {
        String completeURL=apiURL+id+apiExcludes;
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.getForObject(completeURL,String.class);

        Object itemObjectprd = getObject(result, "product");
        Object itemObject = getObject(itemObjectprd.toString(), "item");
        Object itemObjectDesc = getObject(itemObject.toString(), "product_description");
        Object objectTitle = getObject(itemObjectDesc.toString(), "title");

        return objectTitle.toString();
    }

    private Object getObject(String result, String item) throws JSONException {
        if(result==null){
            throw new ProductNotFoundException("NOT FOUND: Product information not found");
        }
        JSONObject jsonObjectProduct = new JSONObject(result);
        return jsonObjectProduct.get(item);
    }
}
