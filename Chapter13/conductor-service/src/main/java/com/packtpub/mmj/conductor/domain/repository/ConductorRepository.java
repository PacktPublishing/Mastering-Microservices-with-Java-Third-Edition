package com.packtpub.mmj.conductor.domain.repository;

import java.util.Collection;

/**
 * @param <Booking>
 * @param <String>
 * @author Sourabh Sharma
 */
public interface ConductorRepository<Booking, String> extends Repository<Booking, String> {

  /**
   * @param name
   */
  boolean containsName(String name);

  /**
   * @param name
   */
  Collection<Booking> findByName(String name) throws Exception;
}
