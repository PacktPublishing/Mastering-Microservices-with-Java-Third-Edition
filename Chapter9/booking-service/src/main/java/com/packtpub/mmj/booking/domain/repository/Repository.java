package com.packtpub.mmj.booking.domain.repository;

/**
 * @param <TE>
 * @param <T>
 * @author Sourabh Sharma
 */
public interface Repository<TE, T> extends ReadOnlyRepository<TE, T> {

  /**
   * @param entity
   */
  void add(TE entity);

  /**
   * @param id
   */
  void remove(T id);

  /**
   * @param entity
   */
  void update(TE entity);
}
