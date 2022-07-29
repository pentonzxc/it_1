package com.kolya.it_1.security;

import com.kolya.it_1.security.filters.LoginSuccessFilter;
import com.kolya.it_1.services.CustomUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final LoginSuccessFilter loginSuccessFilter;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, LoginSuccessFilter loginSuccessFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.loginSuccessFilter = loginSuccessFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                  .disable()
                .anonymous().authorities("anonymous")
                .and()
                .authorizeRequests()
                  .antMatchers("/register").permitAll()
                  .anyRequest()
                  .authenticated()
                .and()
                .formLogin().permitAll()
                  .loginPage("/login")
                  .loginProcessingUrl("/perform-login")
                  .usernameParameter("email")
                  .passwordParameter("password")
                  .successHandler(loginSuccessFilter)
                .and()
                .logout().permitAll()
                  .deleteCookies("JSESSIONID")
                  .invalidateHttpSession(true)
                  .clearAuthentication(true);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
