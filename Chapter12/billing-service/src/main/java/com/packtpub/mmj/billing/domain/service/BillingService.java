package com.packtpub.mmj.billing.domain.service;

import com.packtpub.mmj.billing.domain.model.entity.Billing;
import com.packtpub.mmj.billing.domain.model.entity.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author Sourabh Sharma
 */
public interface BillingService {

  /**
   * @param billing
   */
  void add(Billing billing) throws Exception;

  /**
   * @param billing
   */
  void update(Billing billing) throws Exception;

  /**
   * @param id
   */
  void delete(String id) throws Exception;

  /**
   * @param id
   */
  Entity findById(String id) throws Exception;

  /**
   * @param name
   */
  Collection<Billing> findByName(String name) throws Exception;

  /**
   * @param name
   */
  Collection<Billing> findByCriteria(Map<String, ArrayList<String>> name) throws Exception;
}
