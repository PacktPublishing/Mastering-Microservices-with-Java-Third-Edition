package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.AbstractSaga;
import com.codebullets.sagalib.FunctionKeyReader;
import com.codebullets.sagalib.HeaderName;
import com.codebullets.sagalib.KeyReader;
import com.codebullets.sagalib.describe.DescribesHandlers;
import com.codebullets.sagalib.describe.HandlerDescription;
import com.codebullets.sagalib.describe.HandlerDescriptions;
import com.google.common.collect.Lists;
import com.packtpub.mmj.booking.domain.model.entity.Booking;
import com.packtpub.mmj.booking.domain.valueobject.BillingVO;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BookingProcessSaga extends AbstractSaga<BookingState> implements DescribesHandlers,
    ApplicationContextAware {

  private static final Logger LOG = LoggerFactory.getLogger(BookingProcessSaga.class);
  private static ApplicationContext appContext;
  private SagaInterceptor interceptor;
  private Originator originator;

  public BookingProcessSaga() {
  }

  public BookingProcessSaga(SagaInterceptor interceptor) {
    this.interceptor = interceptor;
    this.originator = appContext.getBean(Originator.class);
  }

  public void bookingPlaced(final Booking booking) {
    String bookingRequestId = booking.getName();
    state().setBookingRequestId(bookingRequestId);
    state().setOriginator(originator);
    state().addInstanceKey(bookingRequestId);
    //requestTimeout(60, TimeUnit.SECONDS);
    LOG.info(
        "\n\n\n   SAGA started...\n   State keys: {}\n   Saga in process: {}\n   interceptor: {}\n\n",
        state().instanceKeys(), state().getSagaId(), interceptor);
  }

  public void billingVO(final BillingVO billingVO) {
    LOG.info(
        "\n\n\n   Billing handle started...\n   State keys: {}\n   Saga in process: {}\n   interceptor: {}\n   getFoundExecutionHeaders: {}\n\n",
        state().instanceKeys(), state().getSagaId(), interceptor,
        interceptor.getFoundExecutionHeaders());
    HeaderName<String> headerName = HeaderName.forName("billingStatus");
    Object headerValue = interceptor.getFoundExecutionHeaders().get(headerName);
    if (headerValue != null && headerValue.equals("BILLING_DONE")) {
      state().getOriginator().bookingConfirmed(billingVO.getBookingId(), billingVO.getId());
    } else {
      LOG.warn("Billing Response: {}, therefore initiating compensating", headerValue);
      // Either add logic for retries based on billing status
      // or
      // Compensate booking order

      boolean txnCompleted = state().getOriginator().compensateBooking(billingVO.getBookingId());
      // add logic for retry in case of failure
    }
    setFinished();
    SagaConfig.messageStreamMap.remove(billingVO.getBookingId());
  }

  @Override
  public void createNewState() {
    setState(new BookingState());
  }

  @Override
  public Collection<KeyReader> keyReaders() {
    KeyReader reader = FunctionKeyReader.create(
        BillingVO.class,
        BillingVO::getBookingId
    );
    return Lists.newArrayList(reader);
    /*
    // or you can
    Collection<KeyReader> readers = new ArrayList<>(1);
    readers.add(KeyReaders.forMessage(
        BillingVO.class,
        verifiedMessage -> verifiedMessage.getBookingId()));
    return readers;*/
  }

  @Override
  public HandlerDescription describeHandlers() {
    return HandlerDescriptions.startedBy(Booking.class).usingMethod(this::bookingPlaced)
        .handleMessage(BillingVO.class).usingMethod(this::billingVO)
        .finishDescription();
  }

  @Override
  public void setApplicationContext(ApplicationContext appContext) throws BeansException {
    this.appContext = appContext;
  }
}
