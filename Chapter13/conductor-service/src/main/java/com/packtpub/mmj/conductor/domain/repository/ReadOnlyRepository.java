package com.packtpub.mmj.conductor.domain.repository;

import java.util.Collection;

/**
 * @param <TE>
 * @param <T>
 * @author Sourabh Sharma
 */
public interface ReadOnlyRepository<TE, T> {

  //long Count;

  /**
   * @param id
   */
  boolean contains(T id);

  /**
   * @param id
   */
  TE get(T id);

  /**
   *
   * @return
   */
  Collection<TE> getAll();
}
