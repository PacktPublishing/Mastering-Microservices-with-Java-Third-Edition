package com.packtpub.mmj.booking.domain.valueobject;

/**
 * @author Sourabh Sharma
 */
public class UserVO {

  private String name;
  private String id;
  private String address;
  private String city;
  private String phoneNo;

  /**
   * Custom Constructor
   *
   * @param name
   * @param id
   * @param address
   * @param city
   * @param phoneNo
   */
  public UserVO(String id, String name, String address, String city, String phoneNo) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.city = city;
    this.phoneNo = phoneNo;
  }

  /**
   * Default Constructor
   */
  public UserVO() {
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

  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   */
  public void setId(String id) {
    this.id = id;
  }
}
