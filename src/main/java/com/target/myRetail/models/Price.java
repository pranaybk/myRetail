package com.target.myRetail.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Price {
    private double value;
    private String currency_code;

}
