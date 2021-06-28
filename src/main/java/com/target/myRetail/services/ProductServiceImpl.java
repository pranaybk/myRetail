package com.target.myRetail.services;
        import com.target.myRetail.util.ProductUtil;
        import com.target.myRetail.entity.ProductPrice;
        import com.target.myRetail.exception.InvalidPriceException;
        import com.target.myRetail.exception.ProductNotFoundException;
        import com.target.myRetail.models.Price;
        import com.target.myRetail.models.ProductInfo;
        import com.target.myRetail.repositories.ProductRepository;
        import org.json.JSONObject;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.util.StringUtils;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.HttpClientErrorException;

        import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private Logger log= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductUtil productUtil;

    @Override
    public ProductInfo getProductDetails(int id){
        ProductInfo productInfo = new ProductInfo();
        Optional<ProductPrice> priceInfo;
        String title;
        try {
             title = productUtil.getProductTitle(id);
             priceInfo = productRepository.findById(id);
        }
        catch(HttpClientErrorException he){
            throw new ProductNotFoundException("NOT FOUND: Product details not found");
        }
        catch(Exception e){
            log.error("Unable to fetch product Details");
            throw  e;
        }
        if(title==null||!priceInfo.isPresent()){
            throw new ProductNotFoundException("NOT FOUND: requested Product Information not found");
        }
            Price price = new Price();
            price.setValue(priceInfo.get().getPrice());
            price.setCurrency_code(priceInfo.get().getCurrency_code());
            productInfo.setId(Integer.valueOf(id));
            productInfo.setName(title);
            productInfo.setCurrent_price(price);

        return productInfo;
    }

    //Add price docs in mango DB
    @PostMapping("/addProduct")
    private String saveProduct(@RequestBody ProductPrice productPrice){
        log.info("Request Schema : "+productPrice);
        productRepository.save(productPrice);
        return "Added product with Id : "+productPrice.getProductId();
    }

    public void updateProduct(int id,ProductInfo productInfo){
        ProductPrice pp=new ProductPrice();
        JSONObject jObject = new JSONObject();
        pp.setProductId(id);

            if(productInfo==null||id!=productInfo.getId()){
                throw new InvalidPriceException("Product Id is null or not valid");
            }
            if(productInfo.getCurrent_price()!=null){
                pp.setPrice(productInfo.getCurrent_price().getValue());
                pp.setCurrency_code(productInfo.getCurrent_price().getCurrency_code());
                productRepository.save(pp);
            }
    }
}
