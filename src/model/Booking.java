package model;

import java.time.LocalDate;

public class Booking {
  private int id;
  private int customerId;
  private String customerName;
  private String tourName;
  private int ticketId;
  private LoaiVe ticketType;
  private int quantity;
  private LocalDate createdDate;
  private boolean paid;

  public Booking() {}

  public Booking(int customerId, int ticketId, int quantity) {
    this.setCustomerId(customerId);
    this.setCustomerName(customerName);
    this.setTicketId(ticketId);
    this.setQuantity(quantity);
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getCustomerId() { return customerId; }
  public void setCustomerId(int customerId) {
    if (customerId <= 0) {
      throw new IllegalArgumentException("ID Khách Hàng không hợp lệ.");
    }
    this.customerId = customerId;
  }

  public String getCustomerName() { return customerName; }
  public void setCustomerName(String customerName) {
    if (customerName == null || customerName.isBlank()) {
      throw new IllegalArgumentException("Tên Khách Hàng không hợp lệ.");
    }
    this.customerName = customerName;
  }

  public String getTourName() { return tourName; }
  public void setTourName(String tourName) {
    if (tourName == null || tourName.isBlank()) {
      throw new IllegalArgumentException("Tên Tua không hợp lệ.");
    }
    this.tourName = tourName;
  }

  public int getTicketId() { return ticketId; }
  public void setTicketId(int ticketId) {
    if (ticketId <= 0) {
      throw new IllegalArgumentException("ID Vé không hợp lệ.");
    }
    this.ticketId = ticketId;
  }

  public LoaiVe getTicketType() { return ticketType; }
  public void setTicketType(LoaiVe ticketType) { this.ticketType = ticketType; }

  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
    }
    this.quantity = quantity;
  }

  public LocalDate getCreatedDate() { return createdDate; }
  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public boolean isPaid() { return paid; }
  public void setPaid(boolean paid) { this.paid = paid; }
}
