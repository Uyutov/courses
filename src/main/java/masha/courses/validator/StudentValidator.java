package masha.courses.validator;

import masha.courses.models.Student;
import masha.courses.services.UsersDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator {
    private final UsersDetailsService usersDetailsService;

    public StudentValidator(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student =(Student) target;
        try{
            usersDetailsService.loadUserByUsername(student.getEmail());
        }catch (UsernameNotFoundException ignore)
        {
            return;
        }
        errors.rejectValue("email","", "Email already exists");
    }
}
