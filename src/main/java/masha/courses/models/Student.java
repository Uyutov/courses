package masha.courses.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Student {
    private Integer student_id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    private String name;
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30")
    private String surname;
    @NotEmpty(message ="Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, max = 30, message = "Password should be at least 4 letters long and 30 at max")
    private String password;
    @Size(max = 30, message = "Country should be less than 30 characters long")
    private String country;
    private String role = "ROLE_STUDENT";
    private String photo;
    public Student(Integer student_id, String name, String surname,
                   String email, String password,
                   String country) {
        this.student_id = student_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.country = country;
    }
    public Student() {}

    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
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

    public String getRole() {
        return role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoImagePath(){
        if(photo == null) return "student-photos/profileimg.svg";
        return "student-photos/" + student_id + "/" + photo;
    }
    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
