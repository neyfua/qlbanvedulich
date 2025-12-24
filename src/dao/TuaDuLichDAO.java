package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TuaDuLich;

public class TuaDuLichDAO {
  private static final String INSERT_SQL =
      "INSERT INTO tour (tour_name, destination, price, departure_date, "
      + "return_date, description) VALUES (?, ?, ?, ?, ?, ?)";

  public void insert(TuaDuLich tua) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
      ps.setString(1, tua.getTourName());
      ps.setString(2, tua.getDestination());
      ps.setBigDecimal(3, tua.getPrice());
      if (tua.getDepartureDate() != null) {
        ps.setDate(4, Date.valueOf(tua.getDepartureDate()));
      } else {
        ps.setNull(4, java.sql.Types.DATE);
      }
      if (tua.getReturnDate() != null) {
        ps.setDate(5, Date.valueOf(tua.getReturnDate()));
      } else {
        ps.setNull(5, java.sql.Types.DATE);
      }
      ps.setString(6, tua.getDescription());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<TuaDuLich> findAll() {
    List<TuaDuLich> list = new ArrayList<>();
    String sql = "SELECT * FROM tour";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        TuaDuLich tua = new TuaDuLich();
        tua.setId(rs.getInt("id"));
        tua.setTourName(rs.getString("tour_name"));
        tua.setDestination(rs.getString("destination"));
        tua.setPrice(rs.getBigDecimal("price"));

        Date dep = rs.getDate("departure_date");
        if (dep != null) {
          tua.setDepartureDate(dep.toLocalDate());
        }

        Date ret = rs.getDate("return_date");
        if (ret != null) {
          tua.setReturnDate(ret.toLocalDate());
        }

        tua.setDescription(rs.getString("description"));

        list.add(tua);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return list;
  }

  public void update(TuaDuLich tua) {
    String sql = """
  UPDATE tour
  SET tour_name = ?, destination = ?, price = ?,
      departure_date = ?, return_date = ?, description = ?
  WHERE id = ?
""";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, tua.getTourName());
      ps.setString(2, tua.getDestination());
      ps.setBigDecimal(3, tua.getPrice());

      if (tua.getDepartureDate() != null) {
        ps.setDate(4, Date.valueOf(tua.getDepartureDate()));
      } else {
        ps.setNull(4, java.sql.Types.DATE);
      }

      if (tua.getReturnDate() != null) {
        ps.setDate(5, Date.valueOf(tua.getReturnDate()));
      } else {
        ps.setNull(5, java.sql.Types.DATE);
      }

      ps.setString(6, tua.getDescription());
      ps.setInt(7, tua.getId());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(int id) {
    String sql = "DELETE FROM tour WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, id);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
