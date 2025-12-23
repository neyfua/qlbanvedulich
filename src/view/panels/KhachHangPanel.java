package view.panels;

import dao.KhachHangDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;

public class KhachHangPanel extends JPanel {

  private JTable table;
  private DefaultTableModel tableModel;

  private JTextField txtName, txtAge, txtPhone, txtEmail;

  private KhachHangDAO dao = new KhachHangDAO();

  public KhachHangPanel() {
    setLayout(new BorderLayout(10, 10));
    initUI();
    loadData();
  }

  // ================= UI =================
  private void initUI() {
    add(createButtonPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);
    add(createFormPanel(), BorderLayout.SOUTH);
  }

  // ---------- Buttons ----------
  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnAdd = new JButton("Thêm");
    JButton btnEdit = new JButton("Sửa");
    JButton btnDelete = new JButton("Xóa");
    JButton btnClear = new JButton("Làm mới");

    btnAdd.addActionListener(e -> addCustomer());
    btnEdit.addActionListener(e -> editCustomer());
    btnDelete.addActionListener(e -> deleteCustomer());
    btnClear.addActionListener(e -> clearForm());

    panel.add(btnAdd);
    panel.add(btnEdit);
    panel.add(btnDelete);
    panel.add(btnClear);

    return panel;
  }

  // ---------- Table ----------
  private JScrollPane createTablePanel() {
    tableModel = new DefaultTableModel(
        new String[] {"ID", "Tên", "Tuổi", "SĐT", "Email"}, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    table.getSelectionModel().addListSelectionListener(
        e -> fillFormFromTable());

    return new JScrollPane(table);
  }

  // ---------- Form ----------
  private JPanel createFormPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

    txtName = new JTextField();
    txtAge = new JTextField();
    txtPhone = new JTextField();
    txtEmail = new JTextField();

    panel.add(new JLabel("Tên"));
    panel.add(txtName);
    panel.add(new JLabel("Tuổi"));
    panel.add(txtAge);

    panel.add(new JLabel("SĐT"));
    panel.add(txtPhone);
    panel.add(new JLabel("Email"));
    panel.add(txtEmail);

    return panel;
  }

  // ================= Logic =================
  private void loadData() {
    tableModel.setRowCount(0);

    List<KhachHang> list = dao.findAll();
    for (KhachHang kh : list) {
      tableModel.addRow(new Object[] {kh.getId(), kh.getName(), kh.getAge(),
                                      kh.getPhone(), kh.getEmail()});
    }
  }

  private void addCustomer() {
    try {
      String name = txtName.getText().trim();
      String ageText = txtAge.getText().trim();
      String phone = txtPhone.getText().trim();
      String email = txtEmail.getText().trim();

      // ---- Validation ----
      if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
        JOptionPane.showMessageDialog(
            this, "Vui lòng nhập đầy đủ Tên, Tuổi và SĐT", "Thiếu thông tin",
            JOptionPane.WARNING_MESSAGE);
        return;
      }

      int age = Integer.parseInt(ageText);

      KhachHang kh = new KhachHang(name, age, phone, email);

      dao.insert(kh);
      loadData();
      clearForm();

      JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!",
                                    "Thành công",
                                    JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Tuổi phải là số hợp lệ!",
                                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private void editCustomer() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng chọn một tua để sửa!");
      return;
    }

    try {
      KhachHang kh = new KhachHang();
      kh.setId((int)tableModel.getValueAt(row, 0));
      kh.setName(txtName.getText().trim());
      //   kh.setAge(Integer.parseInt(tableModel.getValueAt(row,
      //   2).toString()));
      kh.setAge(Integer.parseInt(txtAge.getText().trim()));
      kh.setPhone(txtPhone.getText().trim());
      kh.setEmail(txtEmail.getText().trim());

      dao.update(kh);
      loadData();
      clearForm();

      JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
  }

  private void deleteCustomer() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa");
      return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      int id = (int)tableModel.getValueAt(row, 0);
      dao.delete(id);
      loadData();
      clearForm();
    }
  }

  private void fillFormFromTable() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    txtName.setText(tableModel.getValueAt(row, 1).toString());
    txtAge.setText(tableModel.getValueAt(row, 2).toString());
    txtPhone.setText(tableModel.getValueAt(row, 3).toString());
    txtEmail.setText(tableModel.getValueAt(row, 4).toString());
  }

  private void clearForm() {
    txtName.setText("");
    txtAge.setText("");
    txtPhone.setText("");
    txtEmail.setText("");
    table.clearSelection();
  }
}
