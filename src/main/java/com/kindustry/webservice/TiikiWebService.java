package com.kindustry.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "TiikiService", endpointInterface = "com.kindustry.webservice.TiikiWebService", targetNamespace = "http://webservice.kindustry.api")
public interface TiikiWebService {

  @WebMethod
  String getTiikiList(String dtoTiiki);

}
