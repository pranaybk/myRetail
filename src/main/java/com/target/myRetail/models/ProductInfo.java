package com.target.myRetail.models;



import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductInfo {

    private int id;
    private String name;
    private Price current_price;
}
