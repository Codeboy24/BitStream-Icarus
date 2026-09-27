
package org.example.controllers;

import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentHandler {
  public static void initDB() {
    var url = "jdbc:sqlite:c:/sqlite/db/chinook.db";
    try (var conn = DriverManager.getConnection(url)) {
      System.out.println("Connection to SQLite has been established.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
