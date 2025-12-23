package view.panels;

import dao.TuaDuLichDAO;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.TuaDuLich;

public class TuaDuLichPanel extends JPanel {

  private JTable table;
  private DefaultTableModel tableModel;

  private JTextField txtName, txtDest, txtPrice, txtDepart, txtReturn, txtDesc;

  private TuaDuLichDAO dao = new TuaDuLichDAO();

  public TuaDuLichPanel() {
    setLayout(new BorderLayout(10, 10));
    initUI();
    loadData();
  }

  private void initUI() {
    add(createButtonPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);
    add(createFormPanel(), BorderLayout.SOUTH);
  }

  private JPanel createButtonPanel() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnAdd = new JButton("Thêm");
    JButton btnEdit = new JButton("Sửa");
    JButton btnDelete = new JButton("Xóa");
    JButton btnClear = new JButton("Làm mới");

    btnAdd.addActionListener(e -> addTour());
    btnEdit.addActionListener(e -> editTour());
    btnDelete.addActionListener(e -> deleteTour());
    btnClear.addActionListener(e -> clearForm());

    p.add(btnAdd);
    p.add(btnEdit);
    p.add(btnDelete);
    p.add(btnClear);
    return p;
  }

  private JScrollPane createTablePanel() {
    tableModel = new DefaultTableModel(
        new String[] {"ID", "Tên", "Điểm đến", "Giá", "Khởi hành", "Trở về"},
        0) {
      public boolean isCellEditable(int r, int c) { return false; }
    };

    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> fillForm());

    return new JScrollPane(table);
  }

  private JPanel createFormPanel() {
    JPanel p = new JPanel(new GridLayout(3, 4, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Thông tin tua du lịch"));

    txtName = new JTextField();
    txtDest = new JTextField();
    txtPrice = new JTextField();
    txtDepart = new JTextField("2025-01-01");
    txtReturn = new JTextField("2025-01-05");
    txtDesc = new JTextField();

    p.add(new JLabel("Tên"));
    p.add(txtName);
    p.add(new JLabel("Điểm đến"));
    p.add(txtDest);

    p.add(new JLabel("Giá"));
    p.add(txtPrice);
    p.add(new JLabel("Khởi hành"));
    p.add(txtDepart);

    p.add(new JLabel("Trở về"));
    p.add(txtReturn);
    p.add(new JLabel("Mô tả"));
    p.add(txtDesc);

    return p;
  }

  private void loadData() {
    tableModel.setRowCount(0);
    List<TuaDuLich> list = dao.findAll();

    for (TuaDuLich t : list) {
      tableModel.addRow(new Object[] {t.getId(), t.getTourName(),
                                      t.getDestination(), t.getPrice(),
                                      t.getDepartureDate(), t.getReturnDate()});
    }
  }

  private void addTour() {
    try {
      TuaDuLich t = new TuaDuLich();
      t.setTourName(txtName.getText().trim());
      t.setDestination(txtDest.getText().trim());
      t.setPrice(new BigDecimal(txtPrice.getText().trim()));
      t.setDepartureDate(LocalDate.parse(txtDepart.getText().trim()));
      t.setReturnDate(LocalDate.parse(txtReturn.getText().trim()));
      t.setDescription(txtDesc.getText().trim());

      dao.insert(t);
      loadData();
      clearForm();

    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
    }
  }

  private void editTour() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng chọn một tua để sửa!");
      return;
    }

    try {
      TuaDuLich t = new TuaDuLich();
      t.setId((int)tableModel.getValueAt(row, 0)); // keep the original ID
      t.setTourName(txtName.getText().trim());
      t.setDestination(txtDest.getText().trim());
      t.setPrice(new BigDecimal(txtPrice.getText().trim()));
      t.setDepartureDate(LocalDate.parse(txtDepart.getText().trim()));
      t.setReturnDate(LocalDate.parse(txtReturn.getText().trim()));
      t.setDescription(txtDesc.getText().trim());

      dao.update(t);
      loadData();
      clearForm();

      JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
  }

  private void deleteTour() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int)tableModel.getValueAt(row, 0);
    dao.delete(id);
    loadData();
    clearForm();
  }

  private void fillForm() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;

    txtName.setText(tableModel.getValueAt(r, 1).toString());
    txtDest.setText(tableModel.getValueAt(r, 2).toString());
    txtPrice.setText(tableModel.getValueAt(r, 3).toString());
    txtDepart.setText(tableModel.getValueAt(r, 4).toString());
    txtReturn.setText(tableModel.getValueAt(r, 5).toString());
  }

  private void clearForm() {
    txtName.setText("");
    txtDest.setText("");
    txtPrice.setText("");
    txtDepart.setText("");
    txtReturn.setText("");
    txtDesc.setText("");
    table.clearSelection();
  }
}
