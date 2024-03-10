package masha.courses.controllers;

import masha.courses.dao.CourseDao;
import masha.courses.dao.TeacherDao;
import masha.courses.models.Course;
import masha.courses.models.Lesson;
import masha.courses.security.TeacherDetails;
import masha.courses.services.UsersDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller()
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final UsersDetailsService usersDetailsService;
    public TeacherController(TeacherDao teacherDao, CourseDao courseDao, UsersDetailsService usersDetailsService) {
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.usersDetailsService = usersDetailsService;
    }
    @GetMapping("/profile")
    public String getTeacher(Model model)
    {
        int teacher_id = ((TeacherDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getTeacher().getTeacher_id();
        model.addAttribute("teacher",teacherDao.getTeacherById(teacher_id).get());
        return "/teacher/profile";
    }
    @GetMapping("/directions")
    public String getDirections(Model model)
    {
        model.addAttribute("directions", courseDao.getAllDirections());
        return "/teacher/directions";
    }
    @GetMapping("/direction/{direction_id}")
    public String GetDirection(@PathVariable("direction_id")int direction_id, Model model)
    {
        model.addAttribute("subjects", courseDao.getSubjectByDirectionId(direction_id));
        return "/teacher/subjects";
    }
    @GetMapping("/direction/subject/{subject_id}")
    public String getSubject(@PathVariable("subject_id")int subject_id, Model model)
    {
        model.addAttribute("subject_id", subject_id);
        model.addAttribute("course", new Course());
        return "teacher/subject";
    }
    @PostMapping("/direction/subject/{subject_id}")
    public String createCourseOfSubject(@PathVariable("subject_id")int subject_id,
                                        @ModelAttribute("course")Course course, Model model)
    {
        int teacher_id = ((TeacherDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getTeacher().getTeacher_id();
        course.setSubject_id(subject_id);
        courseDao.createCourse(course);
        courseDao.addCourseToTeacher(teacher_id, courseDao.getCourseBySubjectIdAndCourseName(subject_id, course.getName()).get().getCourse_id());
        return "redirect:/teacher/direction/subject/"+subject_id;
    }
    @GetMapping("/courses")
    public String getCoursesOfTeacher(Model model)
    {
        int teacher_id = ((TeacherDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getTeacher().getTeacher_id();
        model.addAttribute("courses", courseDao.getCoursesOfTeacher(teacher_id));
        return "/teacher/courses";
    }
    @GetMapping("/course/{course_id}")
    public String getCourseToUpdate(@PathVariable("course_id")int course_id, Model model)
    {
        int teacher_id = ((TeacherDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getTeacher().getTeacher_id();
        Optional<Course> course = courseDao.getCourseOfTeacher(teacher_id, course_id);
        if(course.isPresent())
        {
            course.get().setLessons(courseDao.getLessonOfCourse(course_id));
            model.addAttribute("course", course.get());
            return "/teacher/course";
        }
        return "redirect:/teacher/courses";
    }
    @GetMapping("/course/lesson/{lesson_id}")
    public String getLessonToUpdate(@PathVariable("lesson_id")int lesson_id, Model model)
    {
        model.addAttribute("lesson", courseDao.getLessonByLessonId(lesson_id).get());
        return "/teacher/lesson";
    }
    @PostMapping("/course/lesson/{lesson_id}/update")
    public String updateCourse(@PathVariable("lesson_id")int lesson_id,@ModelAttribute("lesson") Lesson lesson)
    {
        lesson.setLesson_id(lesson_id);
        courseDao.updateLesson(lesson);
        return "redirect:/teacher/courses";
    }
    @GetMapping("/course/{course_id}/lesson/new")
    public String getLessonCreator(@PathVariable("course_id")int course_id, Model model)
    {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("course_id", course_id);
        return "/teacher/lesson_creator";
    }
    @PostMapping("/course/{course_id}/lesson/new")
    public String createLesson(@PathVariable("course_id")int course_id, @ModelAttribute("lesson")Lesson lesson)
    {
        lesson.setCourse_id(course_id);
        courseDao.createLesson(lesson);
        return "redirect:/teacher/course/" +course_id;
    }
}
