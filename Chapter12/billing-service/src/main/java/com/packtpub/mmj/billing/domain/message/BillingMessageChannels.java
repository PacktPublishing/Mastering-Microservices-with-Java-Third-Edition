package com.packtpub.mmj.billing.domain.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BillingMessageChannels {

  public static final String BOOKING_ORDER_INPUT = "bookingOrderInput";
  public static final String BILLING_OUTPUT = "billingOutput";

  @Input(BOOKING_ORDER_INPUT)
  MessageChannel bookingOrderInput();

  @Output(BILLING_OUTPUT)
  MessageChannel billingOutput();
}
