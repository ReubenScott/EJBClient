package com.kindustry;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author
 *
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SoapWebserviceApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SoapWebserviceApplication.class, args);
  }

  // 注册Servlet组件
  @Bean
  public ServletRegistrationBean<CXFServlet> registCXFServlet(ApplicationContext context) {
    return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/services/*");
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SoapWebserviceApplication.class);
  }
}
