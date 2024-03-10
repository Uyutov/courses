package masha.courses.controllers;

import masha.courses.dao.CourseDao;
import masha.courses.models.Course;
import masha.courses.models.Direction;
import masha.courses.models.Subject;
import masha.courses.services.UsersDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/homepage")
public class HomepageController {
    private final CourseDao courseDao;
    private final UsersDetailsService usersDetailsService;
    public HomepageController(CourseDao courseDao, UsersDetailsService usersDetailsService) {
        this.courseDao = courseDao;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping()
    public String getHomepage(Model model)
    {
        List<Direction> directions= courseDao.getAllDirections();
        for(Direction direction:directions)
        {
            direction.setSubjects(courseDao.getSubjectByDirectionId(direction.getDirection_id()));
        }
        model.addAttribute("directions", directions);
        return "/homepage/homepage";
    }
    @GetMapping("/courses/{subject_id}")
    public String getCourses(@PathVariable("subject_id") int subject_id, Model model, String keyword)
    {
        Optional<Subject> subject = courseDao.getSubjectByID(subject_id);
        if(subject.isPresent()) {
            if(keyword == null || keyword.isEmpty()) {
                subject.get().setCourses(courseDao.getCoursesOfSubject(subject_id));
            }else{
                System.out.println("Select keyword courses");
                subject.get().setCourses(courseDao.getCoursesByKeyWords(keyword, subject_id));
            }
            model.addAttribute("subject", subject.get());
            return "/homepage/courses";
        }
        return "redirect:/homepage";
    }
    @GetMapping("/course/{course_id}")
    public String getCourseById(@PathVariable("course_id") int course_id, Model model)
    {
        Optional<Course> course = courseDao.getCourseById(course_id);
        if(course.isPresent())
        {
            System.out.println("Inside if");
            course.get().setLessons(courseDao.getLessonOfCourse(course_id));
            model.addAttribute("course", course.get());
            Optional<Subject> subject = courseDao.getSubjectByID(course.get().getSubject_id());
            model.addAttribute("subject", subject.get());
            return "/homepage/course";
        }
        return "redirect:/homepage";
    }
    @GetMapping("/profile")
    public String getProfile()
    {
        Collection<SimpleGrantedAuthority> authority = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authority.stream().anyMatch(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/admin/profile";
        }
        if (authority.stream().anyMatch(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals("ROLE_TEACHER"))){
            return "redirect:/teacher/profile";
        }
        if (authority.stream().anyMatch(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals("ROLE_STUDENT"))){
            return "redirect:/student/profile";
        }
        return "redirect:/auth/login";
    }
}
