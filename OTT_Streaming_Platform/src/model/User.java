package model;

public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String status;

    public User(int userId, String fullName, String email, String password, String phone, String status) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }

    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getStatus() { return status; }
}