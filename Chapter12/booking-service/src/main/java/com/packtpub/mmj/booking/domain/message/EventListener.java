package com.packtpub.mmj.booking.domain.message;

import com.codebullets.sagalib.HeaderName;
import com.codebullets.sagalib.MessageStream;
import com.google.common.collect.ImmutableMap;
import com.packtpub.mmj.billing.domain.valueobject.avro.BillingBookingResponse;
import com.packtpub.mmj.booking.AppConfig;
import com.packtpub.mmj.booking.domain.service.BookingServiceImpl;
import com.packtpub.mmj.booking.domain.valueobject.BillingVO;
import com.packtpub.mmj.booking.saga.SagaConfig;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

  private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

  @StreamListener(BookingMessageChannels.BILLING_INPUT)
  public void consumeBilling(BillingBookingResponse billingResponse) {
    try {
      HeaderName<String> headerName = HeaderName.forName("billingStatus");
      Map<HeaderName<?>, Object> headers = ImmutableMap
          .of(headerName, billingResponse.getStatus().toString());

      MessageStream messageStream = SagaConfig.messageStreamMap.get(billingResponse.getBookingId().toString());
      LOG.info("\n\n\n   Received billing event: {}\n   messageStream: {}\n\n", billingResponse, messageStream);
      messageStream.addMessage(new BillingVO(billingResponse.getBillId().toString(),
              billingResponse.getName().toString(),
              billingResponse.getRestaurantId().toString(), billingResponse.getBookingId().toString(),
              billingResponse.getTableId().toString(), "User", LocalDate.now(), LocalTime.now()),
          headers);
    } catch (Exception ex) {
      ex.printStackTrace();
      LOG.error(ex.getMessage());
    }
  }
}
