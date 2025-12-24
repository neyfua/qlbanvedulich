package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.LoaiVe;
import model.Ve;

public class VeDAO {
  public void insert(Ve ve) {
    String sql =
        "INSERT INTO ticket (tour_id, ticket_type, price) VALUES (?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, ve.getTourId());
      ps.setString(2, ve.getTicketType().name());
      ps.setBigDecimal(3, ve.getPrice());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Ve> findAll() {
    String sql = """
      SELECT t.id,
             t.tour_id,
             tr.tour_name AS tour_name,
             t.ticket_type,
             t.price
      FROM ticket t
      JOIN tour tr ON t.tour_id = tr.id
  """;

    List<Ve> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

      while (rs.next()) {
        Ve ve = new Ve();
        ve.setId(rs.getInt("id"));
        ve.setTourId(rs.getInt("tour_id"));
        ve.setTourName(rs.getString("tour_name"));
        ve.setTicketType(LoaiVe.valueOf(rs.getString("ticket_type")));
        ve.setPrice(rs.getBigDecimal("price"));

        list.add(ve);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }

  public void update(Ve v) {
    String sql = """
            UPDATE ticket
            SET tour_id = ?, ticket_type = ?, price = ? WHERE id = ?
            """;
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, v.getTourId());
      ps.setString(2, v.getTicketType().name());
      ps.setBigDecimal(3, v.getPrice());
      ps.setInt(4, v.getId());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void delete(int id) {
    String sql = "DELETE FROM ticket WHERE id=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
