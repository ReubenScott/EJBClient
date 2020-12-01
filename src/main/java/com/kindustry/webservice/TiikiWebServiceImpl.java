package com.kindustry.webservice;

import org.springframework.stereotype.Service;

@Service
public class TiikiWebServiceImpl implements TiikiWebService {

  // @Autowired
  // TiikiService tiikiService;

  @Override
  public String getTiikiList(String dtoTiiki) {
    System.out.println(dtoTiiki);
    return dtoTiiki;
  }

}