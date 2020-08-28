package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@ConditionalOnProperty(name = "spring.datasource.url")
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.product",
        entityManagerFactoryRef = "productEntityManager",
        transactionManagerRef = "productTransactionManager"
)

public class ProductConfig {
    @Bean(name = "productDataSourceProperties")
    @ConfigurationProperties("spring.datasource-product")
    public DataSourceProperties productDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "productDataSource")
    public DataSource productDataSource(@Qualifier("productDataSourceProperties") DataSourceProperties productDataSourceProperties) {
        return productDataSourceProperties.initializeDataSourceBuilder().build();
    }
    @Bean(name = "productJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa-product")
    public JpaProperties productJpaProperties() {
        return new JpaProperties();
    }


    @Bean(name = "productHibernateProperties")
    @ConfigurationProperties(prefix = "spring.jpa-product.hibernate")
    public HibernateProperties productHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean(name = "productEntityManager")
    public LocalContainerEntityManagerFactoryBean productEntityManager(@Qualifier("productJpaProperties") JpaProperties jpaProperties,
                                                                       @Qualifier("productHibernateProperties") HibernateProperties hibernateProperties,
                                                                       @Qualifier("productDataSource")DataSource productDataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(productDataSource);
        em.setPackagesToScan(
                new String[] {"com.example.demo.product.model"}
        );
        for (String s : jpaProperties.getProperties().keySet()) {
            System.out.println("key : "+s+ " value : "+jpaProperties.getProperties().get(s));
        }
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        if (jpaProperties.getDatabase() != null) {
            vendorAdapter.setDatabase(jpaProperties.getDatabase());
        }
        if (jpaProperties.getDatabasePlatform() != null) {
            vendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        }
        System.out.println("product getDatabase() "+jpaProperties.getDatabase());
        System.out.println("product getDatabasePlatForm() "+jpaProperties.getDatabasePlatform());
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        Map<String, Object> jpaPropertiesMap = new LinkedHashMap<>(jpaProperties.getProperties());
        Map<String, Object> hibernatePropertiesMap = new LinkedHashMap<>(hibernateProperties
                .determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings().ddlAuto(() -> "none")));

        em.setJpaVendorAdapter(vendorAdapter);
        em.getJpaPropertyMap().putAll(jpaPropertiesMap);
        em.getJpaPropertyMap().putAll(hibernatePropertiesMap);
        em.setPersistenceUnitName("productUnit");
        return em;
    }
    @Bean(name = "productDataSource")
    public DataSource productDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:tcp://localhost/~/test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager productTransactionManager(@Qualifier("productEntityManager") EntityManagerFactory entityManagerFactory){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return new JpaTransactionManager(entityManagerFactory);

    }
}
