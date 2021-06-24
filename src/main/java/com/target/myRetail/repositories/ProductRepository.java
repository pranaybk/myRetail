package com.target.myRetail.repositories;

import com.target.myRetail.entity.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductPrice,Integer> {


}
