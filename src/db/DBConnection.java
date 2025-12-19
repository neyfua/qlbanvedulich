package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  public static final String URL = "jdbc:mysql://localhost:3306/qlbanvedulich";
  public static final String USER = "root";
  public static final String PASSWORD = "";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  public static void main(String[] args) {
    System.out.println("Connecting database...");
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
      System.out.println("Database connected!");
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect the database", e);
    }
  }
}
