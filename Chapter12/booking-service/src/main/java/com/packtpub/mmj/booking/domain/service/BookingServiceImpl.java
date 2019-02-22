package com.packtpub.mmj.booking.domain.service;

import com.codebullets.sagalib.MessageStream;
import com.packtpub.mmj.booking.common.DuplicateBookingException;
import com.packtpub.mmj.booking.common.InvalidBookingException;
import com.packtpub.mmj.booking.domain.message.BookingEventHandler;
import com.packtpub.mmj.booking.domain.model.entity.Booking;
import com.packtpub.mmj.booking.domain.model.entity.Entity;
import com.packtpub.mmj.booking.domain.repository.BookingRepository;
import com.packtpub.mmj.booking.saga.SagaConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sourabh Sharma
 */
@Service("bookingService")
public class BookingServiceImpl extends BaseService<Booking, String>
    implements BookingService {

  private final static Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

  private BookingRepository<Booking, String> bookingRepository;

  @Autowired
  private SagaConfig sagaConfig;

  @Autowired
  private BookingEventHandler bookingMessageEventHandler;

  /**
   * @param bookingRepository
   */
  @Autowired
  public BookingServiceImpl(BookingRepository<Booking, String> bookingRepository) {
    super(bookingRepository);
    this.bookingRepository = bookingRepository;
  }

  @Override
  public void add(Booking booking) throws Exception {
    if (bookingRepository.containsName(booking.getName())) {
      Object[] args = {booking.getName()};
      throw new DuplicateBookingException("duplicateBooking", args);
    }

    if (booking.getName() == null || "".equals(booking.getName())) {
      Object[] args = {"Booking with null or empty name"};
      throw new InvalidBookingException("invalidBooking", args);
    }
    super.add(booking);

    // initiate saga
    MessageStream messageStream = sagaConfig.getMessageStreamInstance();
    LOG.info("\n\n\n   Init saga... \n   messageStream: {}\n\n", messageStream);
    SagaConfig.messageStreamMap.put(booking.getName(), messageStream);
    messageStream.add(booking);
    bookingMessageEventHandler.produceBookingOrderEvent(booking);
  }

  /**
   * @param name
   */
  @Override
  public Collection<Booking> findByName(String name) throws Exception {
    return bookingRepository.findByName(name);
  }

  /**
   * @param booking
   */
  @Override
  public void update(Booking booking) throws Exception {
    bookingRepository.update(booking);
  }

  /**
   * @param id
   */
  @Override
  public void delete(String id) throws Exception {
    bookingRepository.remove(id);
  }

  /**
   * @param id
   */
  @Override
  public Entity findById(String id) throws Exception {
    return bookingRepository.get(id);
  }

  /**
   * @param name
   */
  @Override
  public Collection<Booking> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
    throw new UnsupportedOperationException(
        "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
