package com.target.myRetail.util;

import com.target.myRetail.exception.ProductNotFoundException;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Configuration
@PropertySource(value="classpath:application-${spring.profiles.active}.yml")
public class ProductUtil {
    private Logger log= LoggerFactory.getLogger(ProductUtil.class);


    @Value("${application.myRetail.apiUrl}")
    private String apiURL;

    @Value("${application.myRetail.excludes}")
    private String apiExcludes;


    public String getProductTitle(int id) {
        String completeURL=apiURL+id+apiExcludes;
        RestTemplate restTemplate=new RestTemplate();
        Object objectTitle=new Object();
        try {
            //String result = restTemplate.getForObject(completeURL, String.class);
            ResponseEntity<String> response = restTemplate.getForEntity(completeURL, String.class);
            HttpStatus responseCode = response.getStatusCode();
            if (!(responseCode == HttpStatus.OK)) {
                throw new ProductNotFoundException("BAD REQUEST: Product api is down");
            }

            Object itemObjectPrd = getObjectBasedOnName(response.getBody(), "product");
            Object itemObject = getObjectBasedOnName(itemObjectPrd.toString(), "item");
            Object itemObjectDesc = getObjectBasedOnName(itemObject.toString(), "product_description");
            objectTitle = getObjectBasedOnName(itemObjectDesc.toString(), "title");
        }catch (Exception e){
            log.error("Exception occured "+e);
        }
        return objectTitle.toString();
    }

    private Object getObjectBasedOnName(String result, String item) throws JSONException {
        if(result==null){
            throw new ProductNotFoundException("NOT FOUND: Product information not found");
        }
        JSONObject jsonObjectProduct = new JSONObject(result);
        return jsonObjectProduct.get(item);
    }
}
