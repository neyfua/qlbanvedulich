package view.panels;

import dao.BookingDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Booking;

public class ThanhToanPanel extends JPanel {

  private JTable table;
  private DefaultTableModel model;
  private JLabel lblTotal;

  private BookingDAO dao = new BookingDAO();

  public ThanhToanPanel() {
    setLayout(new BorderLayout(10, 10));
    initUI();
    loadData();
  }

  private void initUI() {
    add(createButtonPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);
    add(createPaymentPanel(), BorderLayout.SOUTH);
  }

  // ---------- Buttons ----------
  private JPanel createButtonPanel() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnPay = new JButton("Thanh toán");

    btnPay.addActionListener(e -> pay());

    p.add(btnPay);
    return p;
  }

  // ---------- Table ----------
  private JScrollPane createTablePanel() {
    model = new DefaultTableModel(
        new String[] {"ID", "ID Khách Hàng", "Vé", "SL", "Đã thanh toán"}, 0) {
      public boolean isCellEditable(int r, int c) { return false; }
    };

    table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> updateTotal());

    return new JScrollPane(table);
  }

  // ---------- Payment ----------
  private JPanel createPaymentPanel() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    p.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

    lblTotal = new JLabel("Tổng tiền: 0 VND");
    lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));

    p.add(lblTotal);
    return p;
  }

  // ---------- Logic ----------
  private void loadData() {
    model.setRowCount(0);
    List<Booking> list = dao.findAll();

    for (Booking b : list) {
      model.addRow(new Object[] {b.getId(), b.getCustomerId(), b.getTicketId(),
                                 b.getQuantity(), b.isPaid() ? "✔" : "✘"});
    }
  }

  private void updateTotal() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;

    int qty = (int)model.getValueAt(r, 3);
    // demo: mỗi vé = 1,000,000
    long total = qty * 1_000_000L;
    lblTotal.setText("Tổng tiền: " + total + " VND");
  }

  private void pay() {
    int r = table.getSelectedRow();
    if (r == -1)
      return;

    int id = (int)model.getValueAt(r, 0);
    dao.markAsPaid(id);
    loadData();
    lblTotal.setText("Tổng tiền: 0 VND");

    JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
  }
}
