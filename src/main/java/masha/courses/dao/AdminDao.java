package masha.courses.dao;

import masha.courses.models.Admin;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminDao {
    private final JdbcTemplate jdbcTemplate;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Admin> getAllAdmins()
    {
        return jdbcTemplate.query("Select * from admin", new BeanPropertyRowMapper<>(Admin.class));
    }
    public Optional<Admin> getAdminByEmail(String email)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from Admin where email=?",
                new Object[]{email}, new BeanPropertyRowMapper<>(Admin.class)).stream().findAny().orElse(null));
    }
    public Optional<Admin> getAdminById(int admin_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from Admin where admin_id=?",
                new Object[]{admin_id}, new BeanPropertyRowMapper<>(Admin.class)).stream().findAny().orElse(null));
    }
    public void createAdmin(Admin newAdmin)
    {
        jdbcTemplate.update("insert into admin(email, password) values (?,?)",
                newAdmin.getEmail(), newAdmin.getPassword());
    }

    public void updateAdmin(Admin newAdmin)
    {
        jdbcTemplate.update("update admin set email=?,password=? where admin_id=?",
                newAdmin.getEmail(), newAdmin.getPassword(), newAdmin.getAdmin_id());
    }

}
