package com.iq47.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.time.Duration;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
@EnableTransactionManagement
@EntityScan(basePackages = "com.iq47.booking.model.entity.*")
@EnableJpaRepositories(basePackages = "com.iq47.booking.*",
        entityManagerFactoryRef = "defaultManagerFactory",
        transactionManagerRef= "defaultTransactionManager")
public class SpringApplicationConfig {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationConfig.class, args);
    }

    @Autowired
    private Environment env;

    @Bean
    @Primary
    @QuartzDataSource
    public DataSource defaultDataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = "defaultManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean defaultManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(defaultDataSource())
                .packages("com.iq47.booking.model.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager defaultTransactionManager(
            final @Qualifier("defaultManagerFactory") LocalContainerEntityManagerFactoryBean managerFactory) {
        return new JpaTransactionManager(managerFactory.getObject());
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(2000))
                .setReadTimeout(Duration.ofMillis(2000))
                .build();
    }

    @Bean
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return headers;
    }
}
