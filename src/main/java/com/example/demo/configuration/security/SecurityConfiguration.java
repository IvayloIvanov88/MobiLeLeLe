package com.example.demo.configuration.security;


import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.error.CustomAccessDeniedHandler;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Data
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    public final Oauth2UserSuccessHandler oauth2UserSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/", "/users/login", "/users/register").permitAll().
                antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()).
                antMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name()).
                anyRequest().
                authenticated().
                and().
                exceptionHandling().accessDeniedHandler(accessDeniedHandler()).
                and().
                formLogin().
                loginPage("/users/login").permitAll().
                usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // where to go in case that the login is successful if login defined by role use it
                        successHandler(myAuthenticationSuccessHandler()).
//                defaultSuccessUrl("/", true).
                failureForwardUrl("/login-error").
//        failureUrl("/login-error").

        and().
                logout().
                logoutUrl("/users/logout").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID")
                //for login with Facebook
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(oauth2UserSuccessHandler);

        return http.build();
    }
}
