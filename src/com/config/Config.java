package com.config;


import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration//注解该类为配置类
@Import(value=WebConfig.class)
@ComponentScan("com")//通知spring扫描基础包
@EnableWebMvc//spring通用配置，包括支持json等开关
@EnableTransactionManagement//对事务注解的支持
@MapperScan(basePackages={"com.dao"},sqlSessionFactoryRef="sqlSessionFactoryBean")
@EnableScheduling//打开对quaze的支持
//mybatis的DAO的接口存放的路径
public class Config {
	/**
	 * 属性占位符配置器 声明：classpath:类路径，即src下
	 * 
	 * @return PropertyPlaceholderConfigurer
	 */
	@Bean
	public PropertyPlaceholderConfigurer placeholderConfigurer() {
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		configurer.setLocations(new ClassPathResource("jdbc.properties"));
		return configurer;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		return sessionFactory;
	}

	
	/**
	 * 数据源
	 * 
	 * @param driverClass
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws PropertyVetoException
	 */
	@Bean
	public DataSource dataSource(@Value("${jdbc.driverClass}") String driverClass, @Value("${jdbc.url}") String url,
			@Value("${jdbc.username}") String username, @Value("${jdbc.password}") String password)
			throws PropertyVetoException {
		ComboPooledDataSource CPDS = new ComboPooledDataSource();
		CPDS.setDriverClass(driverClass);
		CPDS.setJdbcUrl(url);
		CPDS.setUser(username);
		CPDS.setPassword(password);
		CPDS.setMaxIdleTime(3600*7);//最大空闲时间，多少秒内未使用则连接被丢弃。若为0则永不丢弃。默认值: 0
//		CPDS.setIdleConnectionTestPeriod(3600*7);//每隔多少秒检查所有连接池中的空闲连接。Default: 0
		return CPDS;
	}
	
	/**
	 * 配置hibernate会话工厂
	 * @param dataSource
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sessionFactory.setMapperLocations(resolver
				.getResources("classpath:com/mapper/*.xml"));
		return sessionFactory.getObject();
	}
	
	
	/**
	 * 事务管理器
	 * @param sessionFactory
	 * @return
	 * @throws PropertyVetoException 
	 */
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) throws PropertyVetoException {
		return new DataSourceTransactionManager(dataSource);
	}

}
