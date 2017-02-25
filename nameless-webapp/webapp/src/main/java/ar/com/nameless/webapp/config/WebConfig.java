package ar.com.nameless.webapp.config;

import ar.com.nameless.webapp.controller.EntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by root on 12/26/16.
 */

@EnableTransactionManagement
@ComponentScan({ "ar.com.nameless.webapp.controller", "ar.com.nameless.service", "ar.com.nameless.persistence" })
@Configuration
public class WebConfig {

    //Hibernate

    @Bean
    public DataSource dataSource(){
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://localhost/nameless");
        ds.setUsername("root");
        ds.setPassword("root");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.com.nameless.model");
        factoryBean.setDataSource(dataSource());
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        properties.setProperty("hibernate.show_sql", "true");// REMOVE IN DEPLOYMENT
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }


    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public EntityMapper entityMapper() {
        return new EntityMapper();
    }

}
