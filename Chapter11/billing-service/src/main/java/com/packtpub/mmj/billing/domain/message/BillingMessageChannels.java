package com.packtpub.mmj.billing.domain.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface BillingMessageChannels {

  public final static String BOOKING_ORDER_INPUT = "bookingOrderInput";

  @Input(BOOKING_ORDER_INPUT)
  MessageChannel bookingOrderInput();
}
