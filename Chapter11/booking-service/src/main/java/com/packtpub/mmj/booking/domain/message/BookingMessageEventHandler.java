package com.packtpub.mmj.booking.domain.message;

import com.packtpub.mmj.booking.domain.model.entity.Booking;
import com.packtpub.mmj.booking.domain.repository.BookingRepository;
import com.packtpub.mmj.booking.domain.service.BookingServiceImpl;
import com.packtpub.mmj.booking.domain.valueobject.avro.BookingOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class BookingMessageEventHandler {
  private static final Logger LOG = LoggerFactory.getLogger(BookingMessageEventHandler.class);

  @Autowired
  @Qualifier(BookingMessageChannels.BOOKING_ORDER_OUTPUT)
  private MessageChannel bookingMessageChannels;

//  /**
//   *
//   * @param bookingMessageChannels
//   */
//  @Autowired
//  public void setBookingMessageChannels(BookingMessageChannels bookingMessageChannels) {
//    this.bookingMessageChannels = bookingMessageChannels;
//  }

  /**
   *
   * @param booking
   * @throws Exception
   */
  public void produceBookingOrderEvent(Booking booking) throws Exception {
    final BookingOrder.Builder boBuilder = BookingOrder.newBuilder();
    boBuilder.setId(booking.getId());
    boBuilder.setName(booking.getName());
    boBuilder.setRestaurantId(booking.getRestaurantId());
    boBuilder.setTableId(booking.getTableId());
    boBuilder.setUserId(booking.getUserId());
    boBuilder.setDate(booking.getDate().toString());
    boBuilder.setTime(booking.getTime().toString());
    BookingOrder bo = boBuilder.build();
    final Message<BookingOrder> message = MessageBuilder.withPayload(bo)
        .setHeader("contentType", "application/*+avro").build();
    boolean isSent = bookingMessageChannels.send(message);
    if(isSent) LOG.info("new bookingOrder is published.");
  }

}
