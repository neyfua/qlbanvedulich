package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ThanhToan {
  private int id;
  private int bookingId;
  private BigDecimal price;
  private String method;
  private LocalDate paymentDate;

  public ThanhToan() {}

  public ThanhToan(int bookingId, BigDecimal price, String method) {
    setBookingId(bookingId);
    setPrice(price);
    setMethod(method);
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getBookingId() { return bookingId; }
  public void setBookingId(int bookingId) {
    if (bookingId <= 0) {
      throw new IllegalArgumentException("ID booking không hợp lệ");
    }
    this.bookingId = bookingId;
  }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) {
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Giá không hợp lệ.");
    }
    this.price = price;
  }

  public String getMethod() { return method; }
  public void setMethod(String method) { this.method = method; }

  public LocalDate getPaymentDate() { return paymentDate; }
  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }
}
