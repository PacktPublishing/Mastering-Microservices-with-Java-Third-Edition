package com.packtpub.mmj.mcrsrvc.domain.model;

/**
 * @param <TE>
 * @param <T>
 * @author Sourabh Sharma
 */
public abstract class ReadOnlyBaseService<TE, T> {

  private final ReadOnlyRepository<TE, T> repository;

  ReadOnlyBaseService(ReadOnlyRepository<TE, T> repository) {
    this.repository = repository;
  }
}
