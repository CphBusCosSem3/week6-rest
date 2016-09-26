/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.webtrade.rest_demo;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author plaul1
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method.
   * It is automatically populated with
   * all resources defined in the project.
   * If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(dk.webtrade.rest_demo.Quote.class);
    resources.add(exceptions.AllExceptionMapper.class);
    resources.add(exceptions.NotFoundExceptionMapper.class);
    resources.add(exceptions.QuoteNotFoundExceptionMapper.class);
  }
  
}
