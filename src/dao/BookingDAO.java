package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.LoaiVe;

public class BookingDAO {
  private static final String INSERT_SQL =
      "INSERT INTO booking (customer_id, ticket_id, quantity, paid) VALUES "
      + "(?, ?, ?, ?)";

  private static final String SELECT_ALL_SQL = """
      SELECT
          b.id,
          b.customer_id,
          c.name        AS customer_name,
          b.ticket_id,
          v.ticket_type AS ticket_type,
          tr.tour_name  AS tour_name,
          b.quantity,
          b.created_date,
          b.paid
      FROM booking b
      JOIN customer c ON b.customer_id = c.id
      JOIN ticket v   ON b.ticket_id = v.id
      JOIN tour tr    ON v.tour_id = tr.id
  """;

  public void insert(Booking bk) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
      ps.setInt(1, bk.getCustomerId());
      ps.setInt(2, bk.getTicketId());
      ps.setInt(3, bk.getQuantity());
      ps.setBoolean(4, false);

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Booking> findAll() {
    List<Booking> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {

      while (rs.next()) {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setCustomerId(rs.getInt("customer_id"));
        b.setCustomerName(rs.getString("customer_name"));

        b.setTicketId(rs.getInt("ticket_id"));
        b.setTicketType(LoaiVe.valueOf(rs.getString("ticket_type")));

        b.setTourName(rs.getString("tour_name"));

        b.setQuantity(rs.getInt("quantity"));
        b.setCreatedDate(rs.getDate("created_date").toLocalDate());
        b.setPaid(rs.getBoolean("paid"));

        list.add(b);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public void delete(int id) {
    String sql = "DELETE FROM booking WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void markAsPaid(int bookingId) {
    String sql = "UPDATE booking SET paid = 1 WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, bookingId);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
