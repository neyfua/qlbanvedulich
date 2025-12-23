package model;

import java.time.LocalDate;

public class Booking {
  private int id;
  private int customerId;
  private int ticketId;
  private int quantity;
  private LocalDate createdDate;
  private boolean paid;

  public Booking() {}

  public Booking(int customerId, int ticketId, int quantity) {
    this.setCustomerId(customerId);
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

  public int getTicketId() { return ticketId; }
  public void setTicketId(int ticketId) {
    if (ticketId <= 0) {
      throw new IllegalArgumentException("ID Vé không hợp lệ.");
    }
    this.ticketId = ticketId;
  }

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
