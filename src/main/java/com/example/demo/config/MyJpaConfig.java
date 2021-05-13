package com.example.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class MyJpaConfig {

	
	@Autowired
	private Environment env;

	@Bean(name = "dataSourceMySql")
	public DataSource dataSource() {

		DriverManagerDataSource src = new DriverManagerDataSource();
		src.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		src.setUrl(env.getProperty("jdbc.url"));
		src.setUsername(env.getProperty("jdbc.username"));
		src.setPassword(env.getProperty("jdbc.password"));

		return src;
	}
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSourceMySql") DataSource ds) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(ds);
		em.setPackagesToScan(new String[] { "com.example.demo.board.model" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.setProperty("hibernate.show_sql", "true");
		//props.setProperty("hibernate.jdbc.batch_size", "3");
		props.setProperty("hibernate.hbm2ddl.auto", "update");		 
		em.setJpaProperties(props);

		return em;
    }
	
	
	
	
}
