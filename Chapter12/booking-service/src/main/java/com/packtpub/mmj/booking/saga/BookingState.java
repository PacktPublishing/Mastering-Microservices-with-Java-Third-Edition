package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.AbstractSagaState;

public class BookingState extends AbstractSagaState<String> {
  private String bookingRequestId;
  private Originator originator;

  public String getBookingRequestId() {
    return bookingRequestId;
  }

  public void setBookingRequestId(final String requestId) {
    bookingRequestId = requestId;
  }

  public Originator getOriginator() {
    return originator;
  }

  public void setOriginator(final Originator originator) {
    this.originator = originator;
  }
}
