package masha.courses.models;

public class Teacher {
    private Integer teacher_id;
    private String name;
    private String surname;
    private String email;
    private String country;
    private String password;
    private String role = "ROLE_TEACHER";
    public Teacher(Integer teacher_id, String name, String surname, String email, String password, String country) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.email = email;
        this.password = password;
    }
    public Teacher(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
