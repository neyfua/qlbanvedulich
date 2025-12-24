package view;

import java.awt.*;
import javax.swing.*;
import view.panels.*;

public class MainPanel extends JFrame {
  private static final String CARD_KHACH_HANG = "KHACH_HANG";
  private static final String CARD_TOUR = "TOUR";
  private static final String CARD_BOOKING = "BOOKING";
  private static final String CARD_THANH_TOAN = "THANH_TOAN";

  private JPanel sidebar;
  private JPanel content;
  private CardLayout card;
  private JButton activeButton;

  public MainPanel() {
    setTitle("✈️ Tour Booking Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new BorderLayout());

    add(createHeader(), BorderLayout.NORTH);
    add(createSidebar(), BorderLayout.WEST);
    add(createContent(), BorderLayout.CENTER);
  }

  private JPanel createHeader() {
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(new Color(41, 84, 146));
    header.setPreferredSize(new Dimension(0, 70));

    JLabel title = new JLabel("✈️ APP QUẢN LÝ BÁN VÉ DU LỊCH");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setForeground(Color.WHITE);
    title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

    header.add(title, BorderLayout.WEST);
    return header;
  }

  private JPanel createSidebar() {
    sidebar = new JPanel();
    sidebar.setBackground(new Color(31, 64, 112));
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setPreferredSize(new Dimension(260, 0));

    sidebar.add(Box.createVerticalStrut(20));

    sidebar.add(menuButton("👥 Khách Hàng", CARD_KHACH_HANG));
    sidebar.add(menuButton("✈️ Tour", CARD_TOUR));
    sidebar.add(menuButton("📋 Booking", CARD_BOOKING));
    sidebar.add(menuButton("💳 Thanh Toán", CARD_THANH_TOAN));

    sidebar.add(Box.createVerticalGlue());
    return sidebar;
  }

  private JPanel createContent() {
    card = new CardLayout();
    content = new JPanel(card);

    content.add(new KhachHangPanel(), CARD_KHACH_HANG);
    content.add(new TuaDuLichPanel(), CARD_TOUR);
    content.add(new BookingPanel(), CARD_BOOKING);
    content.add(new ThanhToanPanel(), CARD_THANH_TOAN);

    card.show(content, CARD_KHACH_HANG);
    return content;
  }

  private JButton menuButton(String text, String cardName) {
    JButton btn = new JButton(text);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
    btn.setForeground(Color.WHITE);
    btn.setBackground(new Color(31, 64, 112));
    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
    btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setFocusPainted(false);
    btn.setOpaque(true);
    btn.setBorderPainted(false);

    btn.addActionListener(e -> {
      card.show(content, cardName);
      setActive(btn);
    });

    if (activeButton == null) {
      activeButton = btn;
      btn.setBackground(new Color(52, 152, 219));
    }

    return btn;
  }

  private void setActive(JButton btn) {
    for (Component c : sidebar.getComponents()) {
      if (c instanceof JButton b) {
        b.setBackground(new Color(31, 64, 112));
      }
    }
    btn.setBackground(new Color(52, 152, 219));
    activeButton = btn;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainPanel().setVisible(true));
  }
}
