package com.packtpub.mmj.restaurant.domain.valueobject;

import com.packtpub.mmj.restaurant.domain.model.entity.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Sourabh Sharma
 */
public class RestaurantVO {

  private Optional<List<Table>> tables = Optional.empty();
  private String name;
  private String id;
  private String address;

  /**
   * Constructor
   */
  public RestaurantVO() {
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

  /**
   *
   * @return
   */
  public Optional<List<Table>> getTables() {
    return tables;
  }

  /**
   * @param tables
   */
  public void setTables(Optional<List<Table>> tables) {
    this.tables = tables;
  }

  /**
   * Overridden toString() method that return String presentation of the Object
   */
  @Override
  public String toString() {
    return String.format("{id: %s, name: %s, address: %s, tables: %s}", this.getId(),
        this.getName(), this.getAddress(), this.getTables());
  }
}
