
package org.example.controllers;

import java.sql.Connection;
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

  public static void addStudent(Connection conn, String name, int year, String program) {
    String sql = "INSERT INTO students(name, year, program) VALUES(?, ?, ?)";

    try (var pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, name);
      pstmt.setInt(2, year);
      pstmt.setString(3, program);

      pstmt.executeUpdate();
      System.out.println("Student added successfully!");
    } catch (SQLException e) {
      System.out.println("Error adding student: " + e.getMessage());
    }
  }
}
