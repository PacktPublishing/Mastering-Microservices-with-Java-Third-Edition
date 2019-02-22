package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.Saga;
import com.codebullets.sagalib.processing.SagaProviderFactory;
import javax.inject.Provider;

public class BookingSagaProviderFactory implements SagaProviderFactory {

  private SagaInterceptor interceptor;

  BookingSagaProviderFactory(SagaInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  @Override
  public Provider<? extends Saga> createProvider(final Class sagaClass) {
    Provider<? extends Saga> provider = null;
    if (sagaClass.equals(BookingProcessSaga.class)) {
      /*      provider = new Provider<BookingProcessSaga>() {
        @Override
        public BookingProcessSaga get() {
          return bookingProcessSaga; // new BookingProcessSaga(bookingMessageEventHandler, interceptor);
        }
      };*/
      return () -> new BookingProcessSaga(interceptor);
    }
    return provider;
  }
}
