package org.example.models;

public class Student {
  private int id;
  private String name;
  private int year;
  private String program;

  public Student(int id, String name, int year, String program) {
    this.id = id;
    this.name = name;
    this.year = year;
    this.program = program;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getYear() {
    return year;
  }

  public String getProgram() {
    return program;
  }
}
