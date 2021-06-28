package com.target.myRetail.services;

import com.target.myRetail.models.ProductInfo;

public interface ProductService {
    public ProductInfo getProductDetails(int id);
    public void updateProduct(int id, ProductInfo productInfo);
}
