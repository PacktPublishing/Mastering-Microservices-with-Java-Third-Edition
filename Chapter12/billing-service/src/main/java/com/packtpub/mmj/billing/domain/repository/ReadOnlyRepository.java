package com.packtpub.mmj.billing.domain.repository;

import java.util.Collection;

/**
 *
 * @author Sourabh Sharma
 * @param <TE>
 * @param <T>
 */
public interface ReadOnlyRepository<TE, T> {

    //long Count;
    /**
     *
     * @param id
     * @return
     */
    boolean contains(T id);

    /**
     *
     * @param id
     * @return
     */
    TE get(T id);

    /**
     *
     * @return
     */
    Collection<TE> getAll();
}
