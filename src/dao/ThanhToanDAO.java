package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.ThanhToan;

public class ThanhToanDAO {
  private static final String INSERT_SQL =
      "INSERT INTO payment (booking_id, price, method) VALUES (?, ?, ?)";

  public void insert(ThanhToan pay) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
      ps.setInt(1, pay.getBookingId());
      ps.setBigDecimal(2, pay.getPrice());
      ps.setString(3, pay.getMethod());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
