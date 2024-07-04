package com.BusBookingManagement.BusTicketBookingManagement.SecurityConfig;


import com.BusBookingManagement.BusTicketBookingManagement.Service.CustomHandlerUsers;
import com.BusBookingManagement.BusTicketBookingManagement.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    CustomHandlerUsers customHandlerUsers;

    //Creating Bean for PasswordEncoder
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Creating bean for SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/admin")
                        .hasAuthority("ADMIN")   //permission to access admin only
                        .requestMatchers("/user")
                        .hasAuthority("USER")    //permission to access users only
                        .requestMatchers("/registration","/css/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customHandlerUsers)
                        .permitAll())

                .logout(form-> form.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    //Method for Authentication Manager
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
}
