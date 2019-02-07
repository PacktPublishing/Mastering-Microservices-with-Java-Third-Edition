package com.packtpub.mmj.conductor.domain.valueobject;

public class Event {

  private BookingEvent bookingEvent;
  private String userID;

  public BookingEvent getBookingEvent() {
    return bookingEvent;
  }

  public void setEvent(final BookingEvent event) {
    this.bookingEvent = event;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(final String userID) {
    this.userID = userID;
  }

  @Override
  public String toString() {
    return "Event{" +
        "event=" + bookingEvent +
        ", userID='" + userID + '\'' +
        '}';
  }
}
