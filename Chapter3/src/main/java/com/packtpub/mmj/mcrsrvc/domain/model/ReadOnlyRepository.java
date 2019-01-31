package com.packtpub.mmj.mcrsrvc.domain.model;

import java.util.Collection;

/**
 * @param <E>
 * @param <T>
 * @author Sourabh Sharma
 */
public interface ReadOnlyRepository<E, T> {

  /**
   * @param id
   */
  boolean contains(T id);

  /**
   * @param id
   */
  E get(T id);

  /**
   *
   * @return
   */
  Collection<E> getAll();
}
