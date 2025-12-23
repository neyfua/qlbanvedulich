package view.panels;

import dao.BookingDAO;
import dao.KhachHangDAO;
import dao.VeDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Booking;
import model.KhachHang;
import model.Ve;

public class BookingPanel extends JPanel {

  private JTable table;
  private DefaultTableModel model;

  private JComboBox<KhachHang> cbCustomer;
  private JComboBox<Ve> cbTicket;
  private JTextField txtQty;

  private BookingDAO dao = new BookingDAO();

  public BookingPanel() {
    setLayout(new BorderLayout(10, 10));
    initUI();
    loadData();
  }

  private void initUI() {
    add(createButtonPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);
    add(createFormPanel(), BorderLayout.SOUTH);
  }

  // ---------- Buttons ----------
  private JPanel createButtonPanel() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnAdd = new JButton("Tạo Booking");
    JButton btnDelete = new JButton("Xóa");
    JButton btnClear = new JButton("Làm mới");

    btnAdd.addActionListener(e -> addBooking());
    btnDelete.addActionListener(e -> deleteBooking());
    btnClear.addActionListener(e -> clearForm());

    p.add(btnAdd);
    p.add(btnDelete);
    p.add(btnClear);

    return p;
  }

  // ---------- Table ----------
  private JScrollPane createTablePanel() {
    model = new DefaultTableModel(
        new String[] {"ID", "Khách Hàng", "Vé", "Số lượng"}, 0) {
      public boolean isCellEditable(int r, int c) { return false; }
    };

    table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> fillForm());

    return new JScrollPane(table);
  }

  // ---------- Form ----------

  private JPanel createFormPanel() {
    JPanel p = new JPanel(new GridLayout(1, 6, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Thông tin booking"));

    cbCustomer = new JComboBox<>();
    cbTicket = new JComboBox<>();
    txtQty = new JTextField();

    loadCustomers();
    loadTickets();

    p.add(new JLabel("Khách hàng"));
    p.add(cbCustomer);
    p.add(new JLabel("Vé"));
    p.add(cbTicket);
    p.add(new JLabel("Số lượng"));
    p.add(txtQty);

    return p;
  }

  private void loadCustomers() {
    cbCustomer.removeAllItems();
    for (KhachHang kh : new KhachHangDAO().findAll()) {
      cbCustomer.addItem(kh);
    }
  }

  private void loadTickets() {
    cbTicket.removeAllItems();
    for (Ve v : new VeDAO().findAll()) {
      cbTicket.addItem(v);
    }
  }

  // ---------- Logic ----------
  private void loadData() {
    model.setRowCount(0);
    List<Booking> list = dao.findAll();

    for (Booking b : list) {
      model.addRow(new Object[] {b.getId(), b.getCustomerId(), b.getTicketId(),
                                 b.getQuantity()});
    }
  }

  private void addBooking() {
    try {
      KhachHang kh = (KhachHang)cbCustomer.getSelectedItem();
      Ve ve = (Ve)cbTicket.getSelectedItem();

      if (kh == null || ve == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách và vé");
        return;
      }

      Booking b = new Booking();
      b.setCustomerId(kh.getId());
      b.setTicketId(ve.getId());
      b.setQuantity(Integer.parseInt(txtQty.getText().trim()));

      dao.insert(b);
      loadData();
      clearForm();

      JOptionPane.showMessageDialog(this, "Tạo booking thành công!");

    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private void deleteBooking() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int)model.getValueAt(row, 0);
    dao.delete(id);
    loadData();
    clearForm();
  }

  private void fillForm() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;

    int customerId = (int)model.getValueAt(r, 1);
    int ticketId = (int)model.getValueAt(r, 2);

    // select customer in combo box
    for (int i = 0; i < cbCustomer.getItemCount(); i++) {
      if (cbCustomer.getItemAt(i).getId() == customerId) {
        cbCustomer.setSelectedIndex(i);
        break;
      }
    }

    // select ticket in combo box
    for (int i = 0; i < cbTicket.getItemCount(); i++) {
      if (cbTicket.getItemAt(i).getId() == ticketId) {
        cbTicket.setSelectedIndex(i);
        break;
      }
    }

    txtQty.setText(model.getValueAt(r, 3).toString());
  }

  private void clearForm() {
    cbCustomer.setSelectedIndex(-1);
    cbTicket.setSelectedIndex(-1);
    txtQty.setText("");
    table.clearSelection();
  }
}
