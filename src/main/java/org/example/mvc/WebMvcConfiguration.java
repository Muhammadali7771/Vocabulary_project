package org.example.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.sql.DataSource;

@Configuration
@ComponentScan("org.example")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment env;

    @Autowired
    public WebMvcConfiguration(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
        this.env = env;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/*")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/*")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/static/*")
                .addResourceLocations("classpath:/static/");
    }
    @Bean
    public DriverManagerDataSource dataSource(){
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
       dataSource.setUrl(env.getRequiredProperty("spring.datasource.jdbc.url"));
       dataSource.setUsername(env.getRequiredProperty("spring.datasource.jdbc.username"));
       dataSource.setPassword(env.getRequiredProperty("spring.datasource.jdbc.password"));
       dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.jdbc.driver"));
       dataSource.setSchema(env.getRequiredProperty("spring.datasource.jdbc.schema"));
       return dataSource;
    }
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
