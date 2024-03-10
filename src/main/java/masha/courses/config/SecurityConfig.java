package masha.courses.config;

import masha.courses.services.UsersDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UsersDetailsService usersDetailsService;

    public SecurityConfig(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.userDetailsService(usersDetailsService)
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/teacher/**").hasRole("TEACHER")
                .requestMatchers("/homepage/**", "/auth/**", "/error", "/logout", "/css/**", "/images/**", "/direction-icons/**", "/js/**").permitAll()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/homepage/profile", true)
                .failureUrl("/auth/login?error");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder()
    {
        return new BCryptPasswordEncoder(10);
    }
}
