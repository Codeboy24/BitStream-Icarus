package org.example.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class StudentHandler {
  public static Connection initDB() {
    var url = "jdbc:sqlite:bitstream.db";
    var sql = "CREATE TABLE IF NOT EXISTS students ("
            + " id INTEGER PRIMARY KEY,"
            + " name text NOT NULL,"
            + " year INTEGER,"
            + " program text"
            + ");";

    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url);
      var stmt = conn.createStatement();

      System.out.println("Connection to SQLite has been established.");
      stmt.execute(sql);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

  public static boolean addStudent(Connection conn, String name, int year, String program) {
    String sql = "INSERT INTO students(name, year, program) VALUES(?, ?, ?)";

    try (var pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, name);
      pstmt.setInt(2, year);
      pstmt.setString(3, program);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Error adding student: " + e.getMessage());
      return false;
    }
  }

  public static List<Map<String, Object>> getAllStudents(Connection conn) {
    String sql = "SELECT id, name, year, program FROM students";
    List<Map<String, Object>> studentsList = new ArrayList<>();

    try (var stmt = conn.createStatement();
         var rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Map<String, Object> student = new HashMap<>();
        student.put("id", rs.getInt("id"));
        student.put("name", rs.getString("name"));
        student.put("year", rs.getInt("year"));
        student.put("program", rs.getString("program"));

        studentsList.add(student);
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return studentsList;
  }

  public static Map<String, Object> getStudentById(Connection conn, int studentId) {
    String sql = "SELECT id, name, year, program FROM students WHERE id = ?";
    Map<String, Object> student = null;

    try (var pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, studentId);

      try (var rs = pstmt.executeQuery()) {
        if (rs.next()) {
          student = new HashMap<>();
          student.put("id", rs.getInt("id"));
          student.put("name", rs.getString("name"));
          student.put("year", rs.getInt("year"));
          student.put("program", rs.getString("program"));
        }
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return student;
  }

  public static boolean updateStudent(Connection conn, int id, String name, int year, String program) {
    String sql = "UPDATE students SET name = ?, year = ?, program = ? WHERE id = ?";

    try (var pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, name);
      pstmt.setInt(2, year);
      pstmt.setString(3, program);
      pstmt.setInt(4, id);
      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println("Error updating student: " + e.getMessage());
      return false;
    }
  }
}