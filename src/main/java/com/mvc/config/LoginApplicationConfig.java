package com.mvc.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.mvc.repository",
						entityManagerFactoryRef = "dialerEntityManagerFactory", 
						transactionManagerRef = "dialerTransactionManager")
@EnableTransactionManagement
@Configuration
@ComponentScan({ "com.mvc.*" })
@PropertySource("classpath:application.properties")
@Import(value = { LoginSecurityConfig.class })
public class LoginApplicationConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean(name = "dataSource")
	 public DriverManagerDataSource dataSource() {
	     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	     driverManagerDataSource.setDriverClassName(env.getProperty("jdbc.driver.class.name"));
	     driverManagerDataSource.setUrl(env.getProperty("jdbc.connection.url"));
	     driverManagerDataSource.setUsername(env.getProperty("jdbc.username"));
	     driverManagerDataSource.setPassword(env.getProperty("jdbc.password"));
	     return driverManagerDataSource;
	 }
	
	@Bean(name = "dialerEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    //JpaVendorAdapteradapter can be autowired as well if it's configured in application properties.
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(false);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    //Add package to scan for entities.
	    factory.setPackagesToScan("com.mvc.entity");
	    factory.setDataSource(dataSource());
	    factory.setJpaProperties(hibernateProps());
	    return factory;
	}
	Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "create");
		return properties;
	}

	@Bean( name = "dialerTransactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	}
	
}