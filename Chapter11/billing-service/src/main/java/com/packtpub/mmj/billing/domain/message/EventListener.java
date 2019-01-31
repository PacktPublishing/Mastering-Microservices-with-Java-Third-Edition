package com.packtpub.mmj.billing.domain.message;

import com.packtpub.mmj.booking.domain.valueobject.avro.BookingOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;

public class EventListener {

  private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

  @StreamListener(BillingMessageChannels.BOOKING_ORDER_INPUT)
  public void consumeBookingOrder(BookingOrder bookingOrder) {
    LOG.info("Received BookingOrder: {}", bookingOrder);
  }
}
