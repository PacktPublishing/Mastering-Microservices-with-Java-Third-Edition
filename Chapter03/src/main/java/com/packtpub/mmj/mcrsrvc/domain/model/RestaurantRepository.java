package com.packtpub.mmj.mcrsrvc.domain.model;

/**
 * @param <Restaurant>
 * @param <String>
 * @author Sourabh Sharma
 */
public interface RestaurantRepository<Restaurant, String> extends Repository<Restaurant, String> {

  /**
   * @param name
   */
  boolean containsName(String name);
}
