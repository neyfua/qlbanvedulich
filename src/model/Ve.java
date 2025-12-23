package model;

import java.math.BigDecimal;

public class Ve {
  private int id;
  private int tourId;
  private LoaiVe ticketType;
  private BigDecimal price;

  public Ve() {}

  public Ve(int tourId, LoaiVe ticketType, BigDecimal price) {
    this.setTourId(tourId);
    this.setTicketType(ticketType);
    this.setPrice(price);
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getTourId() { return tourId; }
  public void setTourId(int tourId) {
    if (tourId <= 0) {
      throw new IllegalArgumentException("ID tua du lịch không hợp lệ.");
    }
    this.tourId = tourId;
  }

  public LoaiVe getTicketType() { return ticketType; }
  public void setTicketType(LoaiVe ticketType) {
    if (ticketType == null) {
      throw new IllegalArgumentException("Loại vé không được trống.");
    }
    this.ticketType = ticketType;
  }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) {
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Giá không hợp lệ.");
    }
    this.price = price;
  }

  @Override
  public String toString() {
    return id + " (" + ticketType + ")";
  }
}
