package com.example.RESTful.Library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dbDialect;
    @Value("${spring.jpa.properties.hibernate.show_sql}")
    private boolean dbShowSql;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoSql;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    @Primary
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.example.RESTful");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dbDialect);
        hibernateProperties.put("hibernate.show_sql", dbShowSql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", ddlAutoSql);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}