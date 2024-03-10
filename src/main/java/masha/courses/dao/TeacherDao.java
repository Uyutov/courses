package masha.courses.dao;

import masha.courses.models.Teacher;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeacherDao {
    private final JdbcTemplate jdbcTemplate;
    public TeacherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Teacher> getAllTeachers()
    {
        return jdbcTemplate.query("Select * from teacher", new BeanPropertyRowMapper<>(Teacher.class));
    }
    public Teacher getTeacher(String name_surname)
    {
        String[] fullName= name_surname.split("_");
        return jdbcTemplate.query("Select * from teacher where name=? and surname=?",
                new Object[]{fullName[0], fullName[2]}, new BeanPropertyRowMapper<>(Teacher.class)).stream().findAny().orElse(null);
    }
    public Optional<Teacher> getTeacherById(int teacher_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from teacher where teacher_id=?",
                new Object[]{teacher_id}, new BeanPropertyRowMapper<>(Teacher.class)).stream().findAny().orElse(null));
    }
    public Optional<Teacher> getTeacherByEmail(String email)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from teacher where email=?",
                new Object[]{email}, new BeanPropertyRowMapper<>(Teacher.class)).stream().findAny().orElse(null));
    }
    public void createTeacher(Teacher newTeacher)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        jdbcTemplate.update("insert into teacher(name, surname, email, country, password) values (?,?,?,?,?)",
                newTeacher.getName(), newTeacher.getSurname(), newTeacher.getEmail(), newTeacher.getCountry(), newTeacher.getPassword());
    }

    public void updateTeacher(Teacher newTeacher)
    {
        jdbcTemplate.update("update teacher set name=?, surname=?,email=?, country=?, password=? where teacher_id=?",
                newTeacher.getName(), newTeacher.getSurname(), newTeacher.getEmail(),
                newTeacher.getCountry(), newTeacher.getPassword(), newTeacher.getTeacher_id());
    }
    public void deleteTeacher(int teacher_id)
    {
        jdbcTemplate.update("Delete from teacher where teacher_id=?", teacher_id);
    }
}
