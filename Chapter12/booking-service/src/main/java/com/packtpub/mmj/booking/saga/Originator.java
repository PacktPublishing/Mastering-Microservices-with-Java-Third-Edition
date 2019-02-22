package com.packtpub.mmj.booking.saga;

import com.packtpub.mmj.booking.domain.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Originator {

  private static final Logger LOG = LoggerFactory.getLogger(Originator.class);
  private BookingService bookingService;

  @Autowired
  @Lazy
  public void setBookingService(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  public void bookingConfirmed(String bookingId, String billNo) {
    // Add business logic here
    // for e.g. booking process completed and confirm the booking to Customer
    LOG.info("Booking SAGA completed with booking Id: {}", bookingId);
  }

  public boolean compensateBooking(String bookingId) {
    LOG.info("Booking SAGA: compensate booking with Id: {}", bookingId);
    boolean bookingCompenstate = false;
    try {
      bookingService.delete(bookingId);
      bookingCompenstate = true;
    } catch (Exception e) {
      e.printStackTrace();
      LOG.error(e.getMessage());
    }
    // Add business logic here
    // for e.g. booking process aborted and communicated back to Customer
    LOG.info("Booking SAGA compensated with booking Id: {}", bookingId);
    return bookingCompenstate;
  }
}
