package com.kolya.it_1.security;

import com.kolya.it_1.security.filters.AccessFilter;
import com.kolya.it_1.security.handlers.LoginSuccessHandler;
import com.kolya.it_1.services.CustomUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final LoginSuccessHandler loginSuccessHandler;

    private  final AccessFilter accessFilter;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, LoginSuccessHandler loginSuccessHandler, AccessFilter accessFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.loginSuccessHandler = loginSuccessHandler;
        this.accessFilter = accessFilter;
    }



    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                  .disable()
                .addFilterAfter(accessFilter , BasicAuthenticationFilter.class)
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
                  .successHandler(loginSuccessHandler)
                .and()
                .logout().permitAll()
                  .deleteCookies("JSESSIONID")
                  .invalidateHttpSession(true)
                  .clearAuthentication(true);
    }



    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
