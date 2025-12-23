package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;

public class KhachHangDAO {
  public List<KhachHang> findAll() {
    List<KhachHang> list = new ArrayList<>();

    String sql = "SELECT id, name, age, phone, email FROM customer";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        KhachHang kh = new KhachHang();
        kh.setId(rs.getInt("id"));
        kh.setName(rs.getString("name"));
        kh.setAge(rs.getInt("age"));
        kh.setPhone(rs.getString("phone"));
        kh.setEmail(rs.getString("email"));
        list.add(kh);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }

  public void insert(KhachHang kh) {
    String sql =
        "INSERT INTO customer (name, age, phone, email) VALUES (?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, kh.getName());
      ps.setString(1, kh.getName());
      ps.setInt(2, kh.getAge());
      ps.setString(3, kh.getPhone());
      ps.setString(4, kh.getEmail());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(KhachHang kh) {
    String sql = """
            UPDATE customer
            SET name = ?, age = ?, phone = ?, email = ? WHERE id = ?
            """;
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, kh.getName());
      ps.setInt(2, kh.getAge());
      ps.setString(3, kh.getPhone());
      ps.setString(4, kh.getEmail());
      ps.setInt(5, kh.getId());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void delete(int id) {
    String sql = "DELETE FROM customer WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, id);
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
