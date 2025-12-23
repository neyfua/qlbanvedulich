package model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class TuaDuLich {
  private int id;
  private String tourName;
  private String destination;
  private BigDecimal price;
  private LocalDate departureDate;
  private LocalDate returnDate;
  private String description;

  public TuaDuLich() {}

  public TuaDuLich(String tourName, String destination, BigDecimal price,
                   LocalDate departureDate, LocalDate returnDate,
                   String description) {
    this.setTourName(tourName);
    this.setDestination(destination);
    this.setPrice(price);
    this.setDepartureDate(departureDate);
    this.setReturnDate(returnDate);
    this.setDescription(description);
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getTourName() { return tourName; }
  public void setTourName(String tourName) {
    if (tourName == null || tourName.isBlank()) {
      throw new IllegalArgumentException("Tên Tua Du Lịch không được trống.");
    }
    this.tourName = tourName;
  }

  public String getDestination() { return destination; }
  public void setDestination(String destination) {
    if (destination == null || destination.isBlank()) {
      throw new IllegalArgumentException("Địa Điểm không được trống.");
    }
    this.destination = destination;
  }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) {
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Giá không hợp lệ.");
    }
    this.price = price;
  }

  public String getFormattedPrice() {
    if (price == null)
      return "0đ";

    NumberFormat vnFormat =
        NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
    return vnFormat.format(price) + "đ";
  }

  public LocalDate getDepartureDate() { return departureDate; }
  public void setDepartureDate(LocalDate departureDate) {
    this.departureDate = departureDate;
  }

  public LocalDate getReturnDate() { return returnDate; }
  public void setReturnDate(LocalDate returnDate) {
    if (departureDate != null && returnDate != null &&
        returnDate.isBefore(departureDate)) {
      throw new IllegalArgumentException(
          "Ngày Kết Thúc phải sau Ngày Khởi Hành.");
    }
    this.returnDate = returnDate;
  }

  public String getDescription() { return description; }
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return getId() + " - " + getTourName();
  }
}
