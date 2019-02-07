package com.packtpub.mmj.conductor.domain.valueobject;

import java.util.Date;

public class BookingEvent {
  private BookingProcessUpdate bookingProcessUpdate;

  private Date timeStamp;

  @Override
  public String toString() {
    return "BookingEvent{" +
        "bookingProcessUpdate=" + bookingProcessUpdate +
        ", timeStamp=" + timeStamp +
        '}';
  }

  public BookingProcessUpdate getBookingProcessUpdate() {
    return bookingProcessUpdate;
  }

  public void setBookingProcessUpdate(final BookingProcessUpdate bookingProcessUpdate) {
    this.bookingProcessUpdate = bookingProcessUpdate;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(final Date timeStamp) {
    this.timeStamp = timeStamp;
  }
}
