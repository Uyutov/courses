package masha.courses.controllers;

import jakarta.validation.Valid;
import masha.courses.dao.StudentDao;
import masha.courses.models.Student;
import masha.courses.services.RegistrationService;
import masha.courses.utils.FileUploadUtil;
import masha.courses.validator.StudentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final StudentDao studentDao;
    private final StudentValidator studentValidator;
    public AuthController(RegistrationService registrationService, StudentDao studentDao, StudentValidator studentValidator) {
        this.registrationService = registrationService;
        this.studentDao = studentDao;
        this.studentValidator = studentValidator;
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "/auth/login";
    }
    @GetMapping("/registration")
    public String registrationPageForStudent(@ModelAttribute("student")Student student)
    {
        return "/auth/registration";
    }
    @PostMapping("/registration")
    public String StudentRegistration(@ModelAttribute("student") @Valid Student student,
                                      BindingResult bindingResult)
    {
        studentValidator.validate(student, bindingResult);
        if(bindingResult.hasErrors())
        {
            return "/auth/registration";
        }

        registrationService.registerStudent(student);
        return "redirect:/auth/login";
    }
    /*@PostMapping("/registration")
    public String StudentRegistration(@ModelAttribute("student") @Valid Student student,
                                      BindingResult bindingResult,
                                      @RequestParam("image")MultipartFile multipartFile
                                      ) throws IOException
    {
        studentValidator.validate(student, bindingResult);
        if(bindingResult.hasErrors())
        {
            return "/auth/registration";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        student.setPhoto(fileName);

        registrationService.registerStudent(student);

        String uploadDir = "student-photos/" + studentDao.getStudentByEmail(student.getEmail()).get().getStudent_id();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/auth/login";
    }*/
}
