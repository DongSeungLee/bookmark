package com.example.demo.product;

import com.example.demo.ProductConfig;
import com.example.demo.product.model.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
@ConditionalOnBean(ProductConfig.class)
@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Transactional("productTransactionManager")
    public void initial(){
        ProductEntity p1 = ProductEntity.builder().name("p1").price(12.5).build();
        ProductEntity p2 = ProductEntity.builder().name("p2").price(13.5).build();
        ProductEntity p3 = ProductEntity.builder().name("p3").price(14.5).build();
        ProductEntity p4 = ProductEntity.builder().name("p4").price(15.5).build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        log.info("Product service initial : {}", TransactionSynchronizationManager.isActualTransactionActive());
    }
}
