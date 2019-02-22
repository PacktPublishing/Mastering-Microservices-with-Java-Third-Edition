package com.packtpub.mmj.mcrsrvc.domain.model;

import java.util.List;
import java.util.Optional;

/**
 * @author Sourabh Sharma
 */
public class Restaurant extends BaseEntity<String> {

  private Optional<List<Table>> tables;

  /**
   * @param name
   * @param id
   * @param tables
   */
  public Restaurant(String name, String id, Optional<List<Table>> tables) {
    super(id, name);
    this.tables = tables;
  }

  /**
   * @param tables
   */
  public void setTables(Optional<List<Table>> tables) {
    this.tables = tables;
  }

  /**
   *
   * @return
   */
  public Optional<List<Table>> getTables() {
    return tables;
  }

  /**
   * Overridden toString() method that return String presentation of the Object
   */
  @Override
  public String toString() {
    return new StringBuilder("{id: ").append(id).append(", name: ")
        .append(name).append(", tables: ").append(tables).append("}").toString();
  }
}
