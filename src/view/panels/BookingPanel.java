package view.panels;

import dao.BookingDAO;
import dao.KhachHangDAO;
import dao.TuaDuLichDAO;
import dao.VeDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Booking;
import model.KhachHang;
import model.LoaiVe;
import model.TuaDuLich;
import model.Ve;

public class BookingPanel extends JPanel {

  private JTable table;
  private DefaultTableModel tableModel;

  private JComboBox<KhachHang> cbCustomer;
  private JComboBox<Ve> cbTicket;
  private JComboBox<TuaDuLich> cbTour;
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

  private JScrollPane createTablePanel() {
    tableModel = new DefaultTableModel(new String[] {"ID", "Khách Hàng", "Vé",
                                                     "Tua Du Lịch", "Số lượng",
                                                     "Ngày tạo"},
                                       0) {
      public boolean isCellEditable(int r, int c) { return false; }
    };

    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> fillForm());

    return new JScrollPane(table);
  }

  private JPanel createFormPanel() {
    JPanel p = new JPanel(new GridLayout(1, 6, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Thông tin booking"));

    cbCustomer = new JComboBox<>();
    cbTicket = new JComboBox<>();
    cbTour = new JComboBox<>();
    txtQty = new JTextField();

    loadCustomers();
    loadTickets();
    loadTours();

    p.add(new JLabel("ID Khách hàng"));
    p.add(cbCustomer);
    p.add(new JLabel("Tua Du Lịch"));
    p.add(cbTour);
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

  private void loadTours() {
    cbTour.removeAllItems();
    for (TuaDuLich t : new TuaDuLichDAO().findAll()) {
      cbTour.addItem(t);
    }
  }

  private void loadData() {
    tableModel.setRowCount(0);
    List<Booking> list = dao.findAll();

    for (Booking b : list) {
      tableModel.addRow(new Object[] {b.getId(), b.getCustomerName(),
                                      b.getTicketType(), b.getTourName(),
                                      b.getQuantity(), b.getCreatedDate()});
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
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng chọn booking cần xóa");
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

  private void fillForm() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;

    String customerName = tableModel.getValueAt(r, 1).toString();
    LoaiVe ticketType = (LoaiVe)tableModel.getValueAt(r, 2);
    String tourName = tableModel.getValueAt(r, 3).toString();
    txtQty.setText(tableModel.getValueAt(r, 4).toString());

    // select customer
    for (int i = 0; i < cbCustomer.getItemCount(); i++) {
      if (cbCustomer.getItemAt(i).getName().equals(customerName)) {
        cbCustomer.setSelectedIndex(i);
        break;
      }
    }

    // select ticket
    for (int i = 0; i < cbTicket.getItemCount(); i++) {
      if (cbTicket.getItemAt(i).getTicketType() == ticketType) {
        cbTicket.setSelectedIndex(i);
        break;
      }
    }

    // select tour
    for (int i = 0; i < cbTour.getItemCount(); i++) {
      if (cbTour.getItemAt(i).getTourName().equals(tourName)) {
        cbTour.setSelectedIndex(i);
        break;
      }
    }
  }

  private void clearForm() {
    cbCustomer.setSelectedIndex(-1);
    cbTicket.setSelectedIndex(-1);
    cbTour.setSelectedIndex(-1);
    txtQty.setText("");
    table.clearSelection();
  }
}
