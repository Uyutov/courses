package masha.courses.config;

import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String studentPhoto = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/static/student-photos/";
        String directionIcon = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/static/direction-icons/";
        String stylesPath = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/finalpages/createx/css/";
        String imagesPath = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/finalpages/createx/images/";
        String fontsPath = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/finalpages/createx/fonts/";
        String jsPath = "file:///D:/JavaProjects/Курсач/courses/src/main/resources/finalpages/createx/js/";

        registry.addResourceHandler("/student/student-photos/**").addResourceLocations(studentPhoto);
        registry.addResourceHandler("/direction-icons/**").addResourceLocations(directionIcon);
        registry.addResourceHandler("/css/**").addResourceLocations(stylesPath);
        registry.addResourceHandler("/images/**").addResourceLocations(imagesPath);
        registry.addResourceHandler("/fonts/**").addResourceLocations(fontsPath);
        registry.addResourceHandler("/js/**").addResourceLocations(jsPath);

    }
}
