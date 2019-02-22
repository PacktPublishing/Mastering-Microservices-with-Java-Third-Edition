package com.packtpub.mmj.billing.domain.message;

import com.packtpub.mmj.billing.domain.model.entity.Billing;
import com.packtpub.mmj.billing.domain.service.BillingService;
import com.packtpub.mmj.booking.domain.valueobject.avro.BookingOrder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

  private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

  private BillingService billingService;
  private BillingEventHandler billingEventHandler;
  private static final Random RANDOM = new Random();

  @Autowired
  public EventListener(BillingService billingService, BillingEventHandler billingEventHandler) {
    this.billingService = billingService;
    this.billingEventHandler = billingEventHandler;
  }

  @StreamListener(BillingMessageChannels.BOOKING_ORDER_INPUT)
  public void consumeBookingOrder(BookingOrder bookingOrder) {
    LOG.info("Received BookingOrder: {}", bookingOrder);
    // TODO: Add logic if booking order is already processed or in process
    long randomId = RANDOM.nextLong();
    if (randomId < 0) {
      LOG.info("\n\n\nGenerate failed billing event for negative randomId for testing\n\n\n");
      billingEventHandler.produceBillingEvent(null, bookingOrder.getName().toString());
    } else {
      String id = String.valueOf(randomId);
      LocalDate nowDate = LocalDate.now();
      LocalTime nowTime = LocalTime.now();
      try {
        Billing billing = new Billing(id, "bill-" + id,
            bookingOrder.getRestaurantId().toString(), bookingOrder.getName().toString(),
            bookingOrder.getTableId().toString(), bookingOrder.getUserId().toString(),
            nowDate, nowTime);
        billingService.add(billing);
        billingEventHandler.produceBillingEvent(billing, bookingOrder.getName().toString());
      } catch (Exception ex) {
        billingEventHandler.produceBillingEvent(null, bookingOrder.getName().toString());
      }
    }
  }
}
