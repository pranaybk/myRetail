package com.target.myRetail.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PriceInfo {
    private int id;
    private Price price;
}
