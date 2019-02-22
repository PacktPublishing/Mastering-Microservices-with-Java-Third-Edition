package com.packtpub.mmj.user.domain.model.entity;

/**
 * @author Sourabh Sharma
 */
public class User extends BaseEntity<String> {

  private String address;
  private String city;
  private String phoneNo;

  /**
   * @param name
   * @param id
   * @param address
   * @param city
   * @param phoneNo
   */
  public User(String id, String name, String address, String city, String phoneNo) {
    super(id, name);
    this.address = address;
    this.city = city;
    this.phoneNo = phoneNo;
  }

  private User(String id, String name) {
    super(id, name);
  }

  public static User getDummyUser() {
    return new User(null, null);
  }

  /**
   *
   * @return
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   *
   * @return
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   *
   * @return
   */
  public String getPhoneNo() {
    return phoneNo;
  }

  /**
   * @param phoneNo
   */
  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  /**
   * Overridden toString() method that return String presentation of the Object
   */
  @Override
  public String toString() {
    return String.format("{id: %s, name: %s, address: %s, city: %s, phoneNo: %s}",
        id, name, address, city, phoneNo);
  }
}
