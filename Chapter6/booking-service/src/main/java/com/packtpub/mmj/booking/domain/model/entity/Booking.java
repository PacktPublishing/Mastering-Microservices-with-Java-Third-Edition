package com.packtpub.mmj.booking.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Sourabh Sharma
 */
public class Booking extends BaseEntity<String> {

  private String restaurantId;
  private String userId;
  private LocalDate date;
  private LocalTime time;
  private String tableId;

  /**
   *
   * @return
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
   * @return
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
   * @return
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
   * @return
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
   * @return
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
  public Booking(String id, String name, String restaurantId, String tableId, String userId,
      LocalDate date, LocalTime time) {
    super(id, name);
    this.restaurantId = restaurantId;
    this.tableId = tableId;
    this.userId = userId;
    this.date = date;
    this.time = time;
  }

  private Booking(String id, String name) {
    super(id, name);
  }

  public static Booking getDummyBooking() {
    return new Booking(null, null);
  }

  /**
   * Overridden toString() method that return String presentation of the Object
   */
  @Override
  public String toString() {
    return String.format(
        "{id: %s, name: %s, userId: %s, restaurantId: %s"
            + ", tableId: %s, date: %s, time: %s}",
        id, name, userId, restaurantId,
        tableId, date, time);
  }
}
