package masha.courses.services;

import masha.courses.dao.AdminDao;
import masha.courses.dao.StudentDao;
import masha.courses.dao.TeacherDao;
import masha.courses.models.Admin;
import masha.courses.models.Student;
import masha.courses.models.Teacher;
import masha.courses.security.AdminDetails;
import masha.courses.security.StudentDetails;
import masha.courses.security.TeacherDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final TeacherDao teacherDao;
    private final AdminDao adminDao;
    private final StudentDao studentDao;
    public UsersDetailsService(TeacherDao teacherDao, AdminDao adminDao, StudentDao studentDao) {
        this.teacherDao = teacherDao;
        this.adminDao = adminDao;
        this.studentDao = studentDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminDao.getAdminByEmail(username);
        if(admin.isPresent())
        {
            return new AdminDetails(admin.get());
        }
        Optional<Teacher> teacher = teacherDao.getTeacherByEmail(username);
        if(teacher.isPresent()){
            return new TeacherDetails(teacher.get());
        }
        Optional<Student> student = studentDao.getStudentByEmail(username);
        if(student.isEmpty())
        {
            throw new UsernameNotFoundException("Email is incorrect");
        }
        return new StudentDetails(student.get());
    }
}
