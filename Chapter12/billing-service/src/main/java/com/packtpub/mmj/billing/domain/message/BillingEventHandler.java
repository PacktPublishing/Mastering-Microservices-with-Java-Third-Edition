package com.packtpub.mmj.billing.domain.message;

import com.packtpub.mmj.billing.domain.model.entity.Billing;
import com.packtpub.mmj.billing.domain.valueobject.avro.BillingBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class BillingEventHandler {

  @Autowired
  @Qualifier(BillingMessageChannels.BILLING_OUTPUT)
  private MessageChannel billingMessageChannel;

  public void produceBillingEvent(Billing billing, String bookingName) {
    final BillingBookingResponse.Builder responseBuilder = BillingBookingResponse.newBuilder();
    if (billing != null) {
      responseBuilder.setBillId(billing.getId());
      responseBuilder.setBookingId(bookingName);
      responseBuilder.setName(billing.getName());
      responseBuilder.setRestaurantId(billing.getRestaurantId());
      responseBuilder.setTableId(billing.getTableId());
      responseBuilder.setStatus("BILLING_DONE");
      responseBuilder.setDate(billing.getDate().toString());
      responseBuilder.setTime(billing.getTime().toString());
    } else {
      // You could also raise another event with error response
      // for keeping code and logic same event is used for failed event as a workaround
      responseBuilder.setBillId("");
      responseBuilder.setBookingId(bookingName);
      responseBuilder.setName("");
      responseBuilder.setRestaurantId("");
      responseBuilder.setTableId("");
      responseBuilder.setDate("");
      responseBuilder.setTime("");
      responseBuilder.setStatus("BILLING_FAILED");
    }
    BillingBookingResponse response = responseBuilder.build();

    final Message<BillingBookingResponse> message = MessageBuilder.withPayload(response)
        .setHeader("Content-Type", "application/*+avro").build();
    billingMessageChannel.send(message);
  }
}
