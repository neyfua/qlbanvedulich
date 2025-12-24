package view.panels;

import dao.TuaDuLichDAO;
import dao.VeDAO;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.LoaiVe;
import model.TuaDuLich;
import model.Ve;
;

public class VePanel extends JPanel {

  private JTable table;
  private DefaultTableModel tableModel;
  private JTextField txtPrice;
  private JComboBox<TuaDuLich> cbTour;
  private JComboBox<LoaiVe> cbType;
  private VeDAO dao = new VeDAO();

  public VePanel() {
    setLayout(new BorderLayout(10, 10));
    initUI();
    loadData();
  }

  private void initUI() {
    add(createButtons(), BorderLayout.NORTH);
    add(createTable(), BorderLayout.CENTER);
    add(createForm(), BorderLayout.SOUTH);
  }

  private JPanel createButtons() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnAdd = new JButton("Thêm");
    JButton btnEdit = new JButton("Sửa");
    JButton btnDelete = new JButton("Xóa");
    JButton btnClear = new JButton("Làm mới");

    btnAdd.addActionListener(e -> addVe());
    btnEdit.addActionListener(e -> editVe());
    btnDelete.addActionListener(e -> deleteVe());
    btnClear.addActionListener(e -> clearForm());

    p.add(btnAdd);
    p.add(btnEdit);
    p.add(btnDelete);
    p.add(btnClear);

    return p;
  }

  private JScrollPane createTable() {
    tableModel = new DefaultTableModel(
        new String[] {"ID", "Tua Du Lịch", "Loại vé", "Giá"}, 0) {
      public boolean isCellEditable(int r, int c) { return false; }
    };

    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> fillForm());

    return new JScrollPane(table);
  }

  private JPanel createForm() {
    JPanel p = new JPanel(new GridLayout(1, 6, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Thông tin vé"));

    cbTour = new JComboBox<>();
    cbType = new JComboBox<>(LoaiVe.values());
    txtPrice = new JTextField();

    loadTours();

    p.add(new JLabel("Tua Du Lịch"));
    p.add(cbTour);
    p.add(new JLabel("Loại"));
    p.add(cbType);
    p.add(new JLabel("Giá"));
    p.add(txtPrice);

    return p;
  }

  private void loadData() {
    tableModel.setRowCount(0);
    for (Ve v : dao.findAll()) {
      tableModel.addRow(new Object[] {v.getId(),
                                      v.getTourName(), // displayed
                                      v.getTicketType(), v.getPrice()});
    }
  }

  private void loadTours() {
    cbTour.removeAllItems();
    TuaDuLichDAO tuaDAO = new TuaDuLichDAO();
    for (TuaDuLich t : tuaDAO.findAll()) {
      cbTour.addItem(t);
    }
  }

  private void addVe() {
    try {
      TuaDuLich selectedTour = (TuaDuLich)cbTour.getSelectedItem();
      LoaiVe selectedType = (LoaiVe)cbType.getSelectedItem();

      if (selectedTour == null || selectedType == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn tour và loại vé");
        return;
      }

      Ve v = new Ve();
      v.setTourId(selectedTour.getId());
      v.setTicketType(selectedType);
      v.setPrice(new BigDecimal(txtPrice.getText().trim()));

      dao.insert(v);

      loadData();
      clearForm();

      JOptionPane.showMessageDialog(this, "Thêm vé thành công!");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private void editVe() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng chọn một tua để sửa!");
      return;
    }

    try {
      Ve v = new Ve();

      v.setId((int)tableModel.getValueAt(row, 0));
      TuaDuLich selectedTour = (TuaDuLich)cbTour.getSelectedItem();
      if (selectedTour != null) {
        v.setTourId(selectedTour.getId());
      }
      v.setTicketType((LoaiVe)cbType.getSelectedItem());
      v.setPrice(new BigDecimal(txtPrice.getText().trim()));

      dao.update(v);
      loadData();

      JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
  }

  private void deleteVe() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;
    dao.delete((int)tableModel.getValueAt(r, 0));
    loadData();
  }

  private void fillForm() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int tourId = (int)tableModel.getValueAt(row, 1);

    for (int i = 0; i < cbTour.getItemCount(); i++) {
      if (cbTour.getItemAt(i).getId() == tourId) {
        cbTour.setSelectedIndex(i);
        break;
      }
    }

    LoaiVe type = (LoaiVe)tableModel.getValueAt(row, 3);
    cbType.setSelectedItem(type);

    txtPrice.setText(tableModel.getValueAt(row, 4).toString());
  }

  private void clearForm() {
    cbTour.setSelectedIndex(-1);
    cbType.setSelectedIndex(-1);
    txtPrice.setText("");
    table.clearSelection();
  }
}
