package masha.courses.dao;

import masha.courses.models.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentDao {
    private final JdbcTemplate jdbcTemplate;

    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getAllStudents()
    {
        return jdbcTemplate.query("Select * from student", new BeanPropertyRowMapper<>(Student.class));
    }
    public Student getStudentById(int id)
    {
        return jdbcTemplate.query("Select * from student where student_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Student.class)).stream().findAny().orElse(null);
    }
    public Optional<Student> getStudentByEmail(String email)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from student where email=?",
                new Object[]{email}, new BeanPropertyRowMapper<>(Student.class)).stream().findAny().orElse(null));
    }
    public void createStudent(Student newStudent)
    {
        jdbcTemplate.update("insert into student(name, surname, email, country, photo, password) values (?,?,?,?,?,?)",
                newStudent.getName(), newStudent.getSurname(), newStudent.getEmail(), newStudent.getCountry(),newStudent.getPhoto(), newStudent.getPassword());
    }
    public void addCourseToStudent(int student_id, int course_id)
    {
        jdbcTemplate.update("insert into student_to_course(student_id, course_id) values(?,?)",
                            student_id, course_id);
    }
    public void updateStudent(Student newStudent)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        jdbcTemplate.update("update student set name=?, surname=?,email=?, country=?, photo=?, password=? where student_id=?",
                newStudent.getName(), newStudent.getSurname(), newStudent.getEmail(), newStudent.getCountry(), newStudent.getPhoto(), newStudent.getPassword(), newStudent.getStudent_id());
    }
}
