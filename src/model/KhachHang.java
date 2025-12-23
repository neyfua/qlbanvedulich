package model;

public class KhachHang {
  private int id;
  private String name;
  private int age;
  private String phone;
  private String email;

  public KhachHang() {}

  public KhachHang(int id, String name, int age, String phone, String email) {
    this.id = id;
    this.setName(name);
    this.setAge(age);
    this.setPhone(phone);
    this.setEmail(email);
  }

  public KhachHang(String name, int age, String phone, String email) {
    this.setName(name);
    this.setAge(age);
    this.setPhone(phone);
    this.setEmail(email);
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Tên Khách Hàng không được trống.");
    }
    this.name = name;
  }

  public int getAge() { return age; }
  public void setAge(int age) {
    if (age < 0 || age > 200) {
      throw new IllegalArgumentException("Tuổi không hợp lệ.");
    }
    this.age = age;
  }

  public String getPhone() { return phone; }
  public void setPhone(String phone) {
    if (phone == null || phone.isBlank()) {
      throw new IllegalArgumentException("Số Điện Thoại không hợp lệ.");
    }
    this.phone = phone;
  }

  public String getEmail() { return email; }
  public void setEmail(String email) {
    if (email != null && !email.contains("@")) {
      throw new IllegalArgumentException("Email không hợp lệ.");
    }
    this.email = email;
  }

  @Override
  public String toString() {
    return id + " - " + name;
  }
}
