package com.packtpub.mmj.user.domain.repository;

import com.packtpub.mmj.user.common.UserNotFoundException;
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
  TE get(T id) throws UserNotFoundException;

  /**
   *
   * @return
   */
  Collection<TE> getAll();
}
