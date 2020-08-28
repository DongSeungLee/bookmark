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
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@ConditionalOnProperty(name = "spring.datasource.url")
//EnableJapRepositories는 큰영역, JpaRepository Bean 등록하는 영역 설정!
@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.example.demo.user",
                "com.example.demo.member",
                "com.example.demo.book"
        },
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)

public class UserConfig {
    @Bean(name = "userDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "userDataSource")
    @Primary
    public DataSource userDataSource(@Qualifier("userDataSourceProperties") DataSourceProperties userDataSourceProperties) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:tcp://localhost/~/test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");

        return userDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "userJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties primaryJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = "userHibernateProperties")
    @ConfigurationProperties(prefix = "spring.jpa.hibernate")
    public HibernateProperties primaryHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean(name = "userEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager(@Qualifier("userJpaProperties") JpaProperties jpaProperties,
                                                                    @Qualifier("userHibernateProperties") HibernateProperties hibernateProperties,
                                                                    @Qualifier("userDataSource") DataSource userDataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource);
        em.setPackagesToScan(
                new String[]{
                        //여기는 entityManager에서 entity와 DB를 연결해주는 영역!
                        "com.example.demo.user.model",
                        "com.example.demo.member.model",
                        "com.example.demo.book.model"
                }
        );
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        if (jpaProperties.getDatabase() != null) {
            vendorAdapter.setDatabase(jpaProperties.getDatabase());
        }
        System.out.println("user getDatabasePlatForm() " + jpaProperties.getDatabasePlatform());
        if (jpaProperties.getDatabasePlatform() != null) {
            vendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        }
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        Map<String, Object> jpaPropertiesMap = new LinkedHashMap<>(jpaProperties.getProperties());
        Map<String, Object> hibernatePropertiesMap = new LinkedHashMap<>(hibernateProperties
                .determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings().ddlAuto(() -> "none")));

        em.setJpaVendorAdapter(vendorAdapter);
        em.getJpaPropertyMap().putAll(jpaPropertiesMap);
        em.getJpaPropertyMap().putAll(hibernatePropertiesMap);
        em.setPersistenceUnitName("userUnit");
        return em;
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManager") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
        //return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "hibernateSessionFactory")
    public LocalSessionFactoryBean localSessionFactoryBean(@Qualifier("userDataSource") DataSource userDataSource,
                                                           @Qualifier("userHibernateProperties") HibernateProperties hibernateProperties,
                                                           @Qualifier("userJpaProperties") JpaProperties jpaProperties){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(userDataSource);
        localSessionFactoryBean.setPackagesToScan(
                new String[]{
                        //여기는 entityManager에서 entity와 DB를 연결해주는 영역!
                        "com.example.demo.user.model",
                        "com.example.demo.member.model",
                        "com.example.demo.book.model"
                }
        );
        Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
}
