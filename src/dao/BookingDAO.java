package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Booking;

public class BookingDAO {

  private static final String INSERT_SQL =
      "INSERT INTO booking (customer_id, ticket_id, quantity, paid) VALUES "
      + "(?, ?, ?)";

  private static final String SELECT_ALL_SQL =
      "SELECT id, customer_id, ticket_id, quantity, paid FROM booking";

  private static final String DELETE_SQL = "DELETE FROM booking WHERE id = ?";

  private static final String MARK_PAID_SQL =
      "UPDATE booking SET paid = TRUE WHERE id = ?";

  public void insert(Booking bk) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

      ps.setInt(1, bk.getCustomerId());
      ps.setInt(2, bk.getTicketId());
      ps.setInt(3, bk.getQuantity());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Booking> findAll() {
    List<Booking> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {

      while (rs.next()) {
        Booking bk = new Booking();
        bk.setId(rs.getInt("id"));
        bk.setCustomerId(rs.getInt("customer_id"));
        bk.setTicketId(rs.getInt("ticket_id"));
        bk.setQuantity(rs.getInt("quantity"));
        list.add(bk);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public void delete(int id) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

      ps.setInt(1, id);
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void markAsPaid(int bookingId) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(MARK_PAID_SQL)) {

      ps.setInt(1, bookingId);
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
