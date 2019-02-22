package com.packtpub.mmj.booking.domain.repository;

import static java.util.stream.Collectors.toList;

import com.packtpub.mmj.booking.common.BookingNotFoundException;
import com.packtpub.mmj.booking.domain.model.entity.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * @author Sourabh Sharma
 */
@Repository("bookingRepository")
public class InMemBookingRepository implements BookingRepository<Booking, String> {

  private static final Map<String, Booking> entities;

  /**
   * Initialize the in-memory Booking Map
   */
  static {
    entities = new ConcurrentHashMap<>(Map.ofEntries(
        new SimpleEntry<>("1",
            new Booking("1", "Booking 1", "1", "1", "1", LocalDate.now(), LocalTime.now())),
        new SimpleEntry<>("2",
            new Booking("2", "Booking 2", "2", "2", "2", LocalDate.now(), LocalTime.now()))
    ));
  }

  /**
   * Check if given booking name already exist.
   *
   * @param name
   * @return true if already exist, else false
   */
  @Override
  public boolean containsName(String name) {
    try {
      return !this.findByName(name).isEmpty();
    } catch (BookingNotFoundException ex) {
      return false;
    } catch (Exception ex) {
      //Exception Handler
    }
    return false;
  }

  /**
   * @param entity
   */
  @Override
  public void add(Booking entity) {
    entities.put(entity.getId(), entity);
  }

  /**
   * @param id
   */
  @Override
  public void remove(String id) {
    if (entities.containsKey(id)) {
      entities.remove(id);
    }
  }

  /**
   * @param entity
   */
  @Override
  public void update(Booking entity) {
    if (entities.containsKey(entity.getId())) {
      entities.put(entity.getId(), entity);
    }
  }

  /**
   * @param id
   */
  @Override
  public boolean contains(String id) {
    throw new UnsupportedOperationException(
        "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * @param id
   */
  @Override
  public Booking get(String id) {
    return entities.get(id);
  }

  /**
   *
   * @return
   */
  @Override
  public Collection<Booking> getAll() {
    return entities.values();
  }

  /**
   * @param name
   */
  @Override
  public Collection<Booking> findByName(String name) throws BookingNotFoundException {
    int noOfChars = name.length();
    Collection<Booking> bookings = entities.entrySet().stream()
        .filter(b -> b.getValue().getName().toLowerCase()
            .contains(name.toLowerCase().subSequence(0, noOfChars)))
        .collect(toList())
        .stream()
        .map(k -> k.getValue())
        .collect(toList());
    if (bookings != null && bookings.isEmpty()) {
      Object[] args = {name};
      throw new BookingNotFoundException("bookingNotFound", args);
    }
    return bookings;
  }

}
