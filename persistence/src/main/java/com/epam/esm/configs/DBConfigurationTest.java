package com.epam.esm.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;


@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:dbConnTest.properties")
public class DBConfigurationTest {


    @Bean
    public DriverManagerDataSource dataSource(@Value("${db.user}") String user,
                                 @Value("${db.password}") String password,
                                 @Value("${db.driver}") String className,
                                 @Value("${db.url}") String connectionUrl) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(className);
        dataSource.setUrl(connectionUrl);

        Resource initData= new ClassPathResource("creatingTestTables.sql");
        Resource fillData= new ClassPathResource("fillingTestTables.sql");


        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
