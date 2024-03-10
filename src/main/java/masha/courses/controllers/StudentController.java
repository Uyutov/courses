package masha.courses.controllers;

import masha.courses.dao.CourseDao;
import masha.courses.dao.StudentDao;
import masha.courses.models.Course;
import masha.courses.models.Student;
import masha.courses.security.StudentDetails;
import masha.courses.services.UsersDetailsService;
import masha.courses.utils.FileUploadUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final UsersDetailsService usersDetailsService;

    public StudentController(StudentDao studentDao, CourseDao courseDao, UsersDetailsService usersDetailsService) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.usersDetailsService = usersDetailsService;
    }
    @GetMapping("/profile")
    public String getProfilePage(Model model)
    {
        int student_id = ((StudentDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getStudent().getStudent_id();
        model.addAttribute("student", studentDao.getStudentById(student_id));
        List<Course> courses = Collections.emptyList();
        if(courseDao.checkIfStudentHasCourses(student_id).get().getCourse_id() != null) {
            courses = courseDao.getCoursesOfStudent(student_id);
            model.addAttribute("courses", courses);
        }
        return "/student/profile";
    }
    @PostMapping("/profile")
    public String setImage(@RequestParam("image") MultipartFile multipartFile) throws IOException
    {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Student student = ((StudentDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getStudent();

        student.setPhoto(fileName);
        studentDao.updateStudent(student);

        String uploadDir = "student-photos/" + studentDao.getStudentByEmail(student.getEmail()).get().getStudent_id();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/student/profile";
    }
    @GetMapping("/courses/{course_id}")
    public String getCourseForStudent(@PathVariable("course_id")int course_id, Model model)
    {
        int student_id = ((StudentDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getStudent().getStudent_id();
        Optional<Course> course = courseDao.getCourseOfStudent(student_id, course_id);
        if(course.isPresent())
        {
            course.get().setLessons(courseDao.getLessonOfCourse(course_id));
            model.addAttribute("course", course.get());
            model.addAttribute("subject", courseDao.getSubjectByID(course.get().getSubject_id()).get());
            return "/student/course";
        }
        return "redirect:/student/profile";
    }
    @PostMapping("/course/delete/{course_id}")
    public String unsubscribeFromCourse(@PathVariable("course_id") int course_id)
    {
        int student_id = ((StudentDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getStudent().getStudent_id();
        courseDao.deleteCourseFromStudent(student_id, course_id);
        return "redirect:/student/profile";
    }
    @PostMapping("/addCourse/{course_id}")
    public String addCourse(@PathVariable("course_id") int course_id)
    {
        int student_id = ((StudentDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getStudent().getStudent_id();
        if(courseDao.getCourseOfStudent(student_id, course_id).isEmpty())
        {
            studentDao.addCourseToStudent(student_id, course_id);
        }
        return "redirect:/homepage";
    }
}
