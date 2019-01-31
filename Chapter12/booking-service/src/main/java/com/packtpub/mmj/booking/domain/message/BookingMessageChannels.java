package com.packtpub.mmj.booking.domain.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BookingMessageChannels {

    public static final String BOOKING_ORDER_OUTPUT = "bookingOrderOutput";
    public static final String BILLING_INPUT = "billingInput";

    @Input(BILLING_INPUT)
    MessageChannel billingInput();

    @Output(BOOKING_ORDER_OUTPUT)
    MessageChannel bookingOrderOutput();
}
