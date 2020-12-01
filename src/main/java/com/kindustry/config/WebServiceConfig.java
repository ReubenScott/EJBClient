package com.kindustry.config;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kindustry.webservice.TiikiWebService;

/**
 * CXF WebServive Config
 * 
 * @author tanoshi
 *
 */
@Configuration
public class WebServiceConfig {

  @Autowired
  private Bus bus;

  @Resource
  private TiikiWebService tiikiWebService;

  @Bean
  public Endpoint endpointTiikiService() {
    EndpointImpl endpoint = new EndpointImpl(this.bus, this.tiikiWebService); // 绑定要发布的服务
    endpoint.publish("/TiikiService"); // 显示要发布的名称
    return endpoint;
  }

}
