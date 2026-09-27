
package org.example.controllers;

import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentHandler {
  public static void initDB() {
    var url = "jdbc:sqlite:bitstream.db";
    var sql = "CREATE TABLE IF NOT EXISTS students ("
        + "	id INTEGER PRIMARY KEY,"
        + "	name text NOT NULL,"
        + "	year INTEGER,"
        + "program text"
        + ");";
    try (var conn = DriverManager.getConnection(url); var stmt = conn.createStatement()) {
      System.out.println("Connection to SQLite has been established.");
      stmt.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

}
