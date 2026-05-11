package com.example.OnlineCourse.config.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize()  kullanabiliyoruz bu annotation sayesinde
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                               //PERMİTALL HERKES ERİŞEBİLİR

                        .requestMatchers("/instructor/create").permitAll()
                        .requestMatchers("/instructor/login").permitAll()
                        .requestMatchers("/users/create").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers("/admin/register").permitAll()
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/Course-Type").permitAll()
                        .requestMatchers("/Course/getAll").permitAll()
                        .requestMatchers("/Course/getById/*").permitAll()

                               //Course Title
                        .requestMatchers("/Course-Title/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Title/create").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Title/getById/{id}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Title/update/{id}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Title/delete/{id}").hasAuthority("ROLE_ADMIN")

                               //Course Type
                        .requestMatchers("/Course-Type/create").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Type/getById/{id}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Type/update/{id}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/Course-Type/delete/{id}").hasAuthority("ROLE_ADMIN")

                              //Courses
                        .requestMatchers("/Course/create").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/Course").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/Course/update/{id}").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/Course/delete/{id}").hasAuthority("ROLE_ADMIN")

                              //INSTRUCTOR
                        .requestMatchers("/instructor/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/instructor/getById/{id}").hasAnyAuthority("ROLE_INSTRUCTOR","ROLE_ADMIN")
                        .requestMatchers("/instructor/update/{id}").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/instructor/delete/{id}").hasAuthority("ROLE_INSTRUCTOR")

                              //USERS
                        .requestMatchers("/users/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/users/getById/{id}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers("/users/update/{id}").hasAuthority("ROLE_USER")
                        .requestMatchers("/users/delete/{id}").hasAuthority("ROLE_USER")

                             //USERS COURSES
                        .requestMatchers("/users-courses/create").hasAuthority("ROLE_USER")
                        .requestMatchers("/users-courses").hasAuthority("ROLE_USER")
                        .requestMatchers("/users-courses/getAll").hasAnyAuthority("ROLE_ADMIN","ROLE_INSTRUCTOR")
                        .requestMatchers("/users-courses/cancel").hasAuthority("ROLE_USER")

                              //ADMIN
                        .requestMatchers("/admin/update/{id}").hasAuthority("ROLE_ADMIN")
                             //ROLE
                        .requestMatchers("/role/create").hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated() // Diğer tüm isteklere kimlik doğrulama zorunlu
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi ekliyoruz
        //jwtAuthenticationFilter ilk önce bu filtre uygulanır tokenen doğruluğu kontrol edilir ,UsernamePasswordAuthenticationFilter.class kimlik doğrulaması için kullanılır login işlemlerinde

        return http.build(); // Yapılandırmayı tamamla ve SecurityFilterChain nesnesini döndür
    }


@Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


