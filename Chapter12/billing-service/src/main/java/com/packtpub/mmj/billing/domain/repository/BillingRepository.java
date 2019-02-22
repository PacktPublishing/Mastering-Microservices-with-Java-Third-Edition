package com.packtpub.mmj.billing.domain.repository;

import java.util.Collection;

/**
 * @param <Booking>
 * @param <String>
 * @author Sourabh Sharma
 */
public interface BillingRepository<Booking, String> extends Repository<Booking, String> {

  /**
   * @param name
   */
  boolean containsName(String name);

  /**
   * @param name
   */
  public Collection<Booking> findByName(String name) throws Exception;
}
