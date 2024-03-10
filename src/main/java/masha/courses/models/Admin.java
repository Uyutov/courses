package masha.courses.models;

public class Admin {
    private Integer admin_id;

    private String email;

    private String role = "ROLE_ADMIN";

    private String password;

    public Admin(Integer admin_id, String email, String password) {
        this.admin_id = admin_id;
        this.email = email;
        this.password = password;
    }

    public  Admin(){}

    public String getRole() {
        return role;
    }
    public int getAdmin_id() {
        return admin_id;
    }

    public String getPassword() {
        return password;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "admin_id=" + admin_id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
