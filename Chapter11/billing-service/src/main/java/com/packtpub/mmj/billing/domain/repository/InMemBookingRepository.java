package com.packtpub.mmj.billing.domain.repository;

import com.packtpub.mmj.billing.domain.model.entity.Billing;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sourabh Sharma
 */
@Repository("billingRepository")
public class InMemBookingRepository implements BillingRepository<Billing, String> {

    private Map<String, Billing> entities;

    /**
     * Initialize the in-memory Billing Repository with sample Map
     */
    public InMemBookingRepository() {
        entities = new HashMap();
        Billing billing = new Billing("1", "Billing 1", "1", "1", "1", LocalDate.now(), LocalTime.now());
        entities.put("1", billing);
        Billing billing2 = new Billing("2", "Billing 2", "2", "2", "2", LocalDate.now(), LocalTime.now());
        entities.put("2", billing2);
    }

    /**
     * Check if given billing name already exist.
     *
     * @param name
     * @return true if already exist, else false
     */
    @Override
    public boolean containsName(String name) {
        try {
            return this.findByName(name).size() > 0;
        } catch (Exception ex) {
            //Exception Handler
        }
        return false;
    }

    /**
     *
     * @param entity
     */
    @Override
    public void add(Billing entity) {
        entities.put(entity.getId(), entity);
    }

    /**
     *
     * @param id
     */
    @Override
    public void remove(String id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    /**
     *
     * @param entity
     */
    @Override
    public void update(Billing entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean contains(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Billing get(String id) {
        return entities.get(id);
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Billing> getAll() {
        return entities.values();
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Collection<Billing> findByName(String name) throws Exception {
        Collection<Billing> billings = new ArrayList();
        int noOfChars = name.length();
        entities.forEach((k, v) -> {
            if (v.getName().toLowerCase().contains(name.toLowerCase().subSequence(0, noOfChars))) {
                billings.add(v);
            }
        });
        return billings;
    }

}
