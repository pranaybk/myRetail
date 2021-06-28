package com.target.myRetail;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myRetail.entity.ProductPrice;
import com.target.myRetail.models.Price;
import com.target.myRetail.models.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("stage")
public class ProductDetailsControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void givenProduct_sucess200() throws Exception{
        mvc.perform(get("/products/{id}","13264003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.name").value("Jif Natural Creamy Peanut Butter - 40oz"));
    }

    @Test
    public void givenProduct_failure() throws Exception{
        mvc.perform(get("/products/{id}","1326400312")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateProduct_sucess200() throws Exception{
        ProductInfo productInfo = new ProductInfo(13264003,"Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
                new Price(42.30,"USD"));
        String body = asJSONString(productInfo);
        mvc.perform(put("/products/{id}","13264003")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct_notMatchingIds() throws Exception{
        ProductInfo productInfo = new ProductInfo(132640032,"Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
                new Price(42.30,"USD"));
        String body = asJSONString(productInfo);
        mvc.perform(put("/products/{id}","13264003")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    public static String asJSONString(Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
