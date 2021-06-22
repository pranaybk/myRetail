package com.target.myRetail.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductInfo {
    private int id;
    private String name;
    private Price current_price;
}
