package com.example.uniprogramming.security;

import com.example.uniprogramming.security.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(MyUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and().authorizeRequests()
                .antMatchers("/api/users/{\\d+}/**").authenticated()
                .antMatchers("/api/users/all").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/api/users").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/api/companies/**").authenticated()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/users").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/companies/**").authenticated()
                .antMatchers("/register").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .csrf()
                .disable();

    }

}
