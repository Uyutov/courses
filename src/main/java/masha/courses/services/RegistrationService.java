package masha.courses.services;

import masha.courses.dao.AdminDao;
import masha.courses.dao.StudentDao;
import masha.courses.dao.TeacherDao;
import masha.courses.models.Admin;
import masha.courses.models.Student;
import masha.courses.models.Teacher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final AdminDao adminDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public RegistrationService(AdminDao adminDao, TeacherDao teacherDao, StudentDao studentDao, BCryptPasswordEncoder encoder) {
        this.adminDao = adminDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
    }

    public void registerStudent(Student student)
    {
        student.setPassword(encoder.encode(student.getPassword()));
        studentDao.createStudent(student);
    }
    public void registerAdmin(Admin admin)
    {
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminDao.createAdmin(admin);
    }
    public void registerTeacher(Teacher teacher)
    {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        teacherDao.createTeacher(teacher);
    }
}
