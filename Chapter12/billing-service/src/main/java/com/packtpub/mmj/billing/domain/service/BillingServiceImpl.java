package com.packtpub.mmj.billing.domain.service;

import com.packtpub.mmj.billing.domain.model.entity.Billing;
import com.packtpub.mmj.billing.domain.model.entity.Entity;
import com.packtpub.mmj.billing.domain.repository.BillingRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sourabh Sharma
 */
@Service("billingService")
public class BillingServiceImpl extends BaseService<Billing, String>
    implements BillingService {

  private BillingRepository<Billing, String> billingRepository;

  /**
   * @param billingRepository
   */
  @Autowired
  public BillingServiceImpl(BillingRepository<Billing, String> billingRepository) {
    super(billingRepository);
    this.billingRepository = billingRepository;
  }

  @Override
  public void add(Billing billing) throws Exception {
    if (billingRepository.containsName(billing.getName())) {
      throw new Exception(
          String.format("There is already a entity with the name - %s", billing.getName()));
    }

    if (billing.getName() == null || "".equals(billing.getName())) {
      throw new Exception("Billing name cannot be null or empty string.");
    }
    super.add(billing);
  }

  /**
   * @param name
   */
  @Override
  public Collection<Billing> findByName(String name) throws Exception {
    return billingRepository.findByName(name);
  }

  /**
   * @param billing
   */
  @Override
  public void update(Billing billing) throws Exception {
    billingRepository.update(billing);
  }

  /**
   * @param id
   */
  @Override
  public void delete(String id) throws Exception {
    billingRepository.remove(id);
  }

  /**
   * @param id
   */
  @Override
  public Entity findById(String id) throws Exception {
    return billingRepository.get(id);
  }

  /**
   * @param name
   */
  @Override
  public Collection<Billing> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
    throw new UnsupportedOperationException(
        "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
