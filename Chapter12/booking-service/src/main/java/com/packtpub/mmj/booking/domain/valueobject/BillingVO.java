package com.packtpub.mmj.booking.domain.valueobject;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Sourabh Sharma
 */
public class BillingVO {

  private final String id;
  private final String name;
  private String restaurantId;
  private String userId;
  private String bookingId;
  private LocalDate date;
  private LocalTime time;
  private String tableId;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   *
   */
  public String getTableId() {
    return tableId;
  }

  /**
   * @param tableId
   */
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }

  /**
   *
   */
  public String getRestaurantId() {
    return restaurantId;
  }

  /**
   * @param restaurantId
   */
  public void setRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
  }

  /**
   *
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   *
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * @param date
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   *
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * @param time
   */
  public void setTime(LocalTime time) {
    this.time = time;
  }

  /**
   * @param name
   * @param id
   * @param restaurantId
   * @param userId
   * @param time
   * @param date
   */
  public BillingVO(String id, String name, String restaurantId, String bookingId, String tableId,
      String userId, LocalDate date, LocalTime time) {
    this.id = id;
    this.name = name;
    this.restaurantId = restaurantId;
    this.tableId = tableId;
    this.userId = userId;
    this.bookingId = bookingId;
    this.date = date;
    this.time = time;
  }

  /**
   * Overridden toString() method that return String presentation of the Object
   */
  @Override
  public String toString() {
    return new StringBuilder("{id: ").append(id).append(", name: ")
        .append(name).append(", userId: ").append(userId)
        .append(", bookingId: ").append(bookingId)
        .append(", restaurantId: ").append(restaurantId)
        .append(", tableId: ").append(tableId)
        .append(", date: ").append(date).append(", time: ").append(time).append("}").toString();
  }

  public String getBookingId() {
    return bookingId;
  }

  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }
}
