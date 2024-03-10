package masha.courses.controllers;

import masha.courses.dao.AdminDao;
import masha.courses.dao.CourseDao;
import masha.courses.dao.StudentDao;
import masha.courses.dao.TeacherDao;
import masha.courses.models.Admin;
import masha.courses.models.Direction;
import masha.courses.models.Subject;
import masha.courses.models.Teacher;
import masha.courses.security.AdminDetails;
import masha.courses.services.RegistrationService;
import masha.courses.services.UsersDetailsService;
import masha.courses.utils.FileUploadUtil;
import masha.courses.utils.Parser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller()
@RequestMapping("/admin")
public class AdminController {
    private final CourseDao courseDao;
    private final AdminDao adminDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final RegistrationService registrationService;
    private final UsersDetailsService usersDetailsService;

    public AdminController(CourseDao courseDao, AdminDao adminDao, TeacherDao teacherDao, StudentDao studentDao, RegistrationService registrationService, UsersDetailsService usersDetailsService) {
        this.courseDao = courseDao;
        this.adminDao = adminDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
        this.registrationService = registrationService;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping("/profile")
    public String getAdminHomePage(Model model)
    {
        int admin_id = ((AdminDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getAdmin().getAdmin_id();
        Optional<Admin> admin = adminDao.getAdminById(admin_id);
        if(admin.isPresent()) {
            model.addAttribute("admin", admin.get());
            model.addAttribute("top_courses", courseDao.getTopCourses());
        }
        return "/admin/profile";
    }
    @PostMapping("/create_json")
    public String createJsonFile()
    {
        Parser.parseJson(studentDao.getAllStudents());
        return "redirect:/admin/profile";
    }
    @PostMapping("/create_pdf")
    public String createPdfFile()
    {
        int admin_id = ((AdminDetails) usersDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())).getAdmin().getAdmin_id();
        Optional<Admin> admin = adminDao.getAdminById(admin_id);
        if(admin.isPresent()) {
            Parser.parsePdf(admin.get());
        }
        return "redirect:/admin/profile";
    }
    @GetMapping("/directions")
    public String getDirections(Model model)
    {
        model.addAttribute("directions", courseDao.getAllDirections());
        return "/admin/directions";
    }
    @GetMapping("/direction/{direction_id}")
    public String getDirection(@PathVariable("direction_id") int direction_id, Model model)
    {
        Optional<Direction> direction = courseDao.getDirectionById(direction_id);
        if(direction.isPresent())
        {
            direction.get().setSubjects(courseDao.getSubjectByDirectionId(direction_id));
            model.addAttribute("direction", direction.get());
            return "/admin/direction";
        }
        return "redirect:/admin/directions";
    }
    @PostMapping("/direction/delete/{direction_id}")
    public String deleteDirection(@PathVariable("direction_id")int direction_id)
    {
        courseDao.deleteDirectionById(direction_id);
        return "redirect:/admin/directions";
    }
    @GetMapping("/direction/add")
    public String getCreationPage(Model model)
    {
        model.addAttribute("direction", new Direction());
        return "/admin/createDirection";
    }
    @PostMapping("/direction/add")
    public String createDirection(@ModelAttribute("direction")Direction direction,
                                  @RequestParam("image") MultipartFile multipartFile) throws IOException
    {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        direction.setIcon(fileName);

        courseDao.createDirection(direction);

        String uploadDir = "direction-icons/" + direction.getName();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/directions";
    }
    @GetMapping("/teachers")
    public String getAllTeachers(Model model)
    {
        model.addAttribute("teachers", teacherDao.getAllTeachers());
        return "/admin/teachers";
    }
    @GetMapping("/teacher/{teacher_id}")
    public String getTeacherProfile(@PathVariable("teacher_id") int teacher_id, Model model)
    {
        model.addAttribute("teacher", teacherDao.getTeacherById(teacher_id).get());
        return "/admin/teacher";
    }
    @GetMapping("/teachers/create")
    public String getTeacherCreator(Model model)
    {
        model.addAttribute("teacher", new Teacher());
        return "/admin/create_teacher";
    }

    @PostMapping("/teachers/create")
    public  String createTeacher(@ModelAttribute("teacher") Teacher teacher)
    {
        registrationService.registerTeacher(teacher);
        return "redirect:/admin/teachers";
    }
    @PostMapping("/teacher/delete/{teacher_id}")
    public String deleteTeacher(@PathVariable("teacher_id")int teacher_id)
    {
        teacherDao.deleteTeacher(teacher_id);
        return "redirect:/admin/teachers";
    }
    @GetMapping("/direction/subject/{subject_id}")
    public String getSubject(@PathVariable("subject_id")int subject_id, Model model)
    {
        model.addAttribute("subject", courseDao.getSubjectByID(subject_id).get());
        return "/admin/subject";
    }
    @GetMapping("/direction/{direction_id}/subject/add")
    public String getSubjectCreator(@PathVariable("direction_id")int direction_id, Model model)
    {
        model.addAttribute("subject", new Subject());
        model.addAttribute("direction_id", direction_id);
        return "/admin/subject_creator";
    }
    @PostMapping("/direction/{direction_id}/subject/add")
    public String createSubject(@PathVariable("direction_id")int direction_id, @ModelAttribute("subject")Subject subject)
    {
        courseDao.createSubject(subject);
        return "redirect:/admin/direction/" + direction_id;
    }
    @PostMapping("/direction/{direction_id}/subject/{subject_id}/delete")
    public String deleteSubject(@PathVariable("direction_id")int direction_id,
            @PathVariable("subject_id")int subject_id)
    {
        courseDao.deleteSubject(subject_id);
        return "redirect:/admin/direction/" + direction_id;
    }
}
