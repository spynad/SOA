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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.booking.controller.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.booking.job.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.booking.consumer.*"),
})
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
                .packages("org.iq47.model.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager defaultTransactionManager(
            final @Qualifier("defaultManagerFactory") LocalContainerEntityManagerFactoryBean managerFactory) {
        return new JpaTransactionManager(managerFactory.getObject());
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() throws NamingException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("java:/PizdecConnectionFactory");
        System.out.println(factory);
        return factory;
    }

    @Bean
    public Queue ticketQueue() throws NamingException {
        InitialContext context = new InitialContext();
        Queue queue = (Queue) context.lookup("queue/bookingQueue");
        System.out.println(queue);
        return queue;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
