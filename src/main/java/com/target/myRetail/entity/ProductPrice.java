package com.target.myRetail.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "PriceInfo")
public class ProductPrice {

    @Id
    private String productId;
    private String price;
    private String currency_code;
}
