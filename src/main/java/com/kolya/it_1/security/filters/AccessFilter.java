package com.kolya.it_1.security.filters;

import com.kolya.it_1.dao.CustomUserDetails;
import com.kolya.it_1.domain.User;
import com.kolya.it_1.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class AccessFilter implements Filter {

    public final UserService userService;

    public AccessFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !authentication.getName().equals("anonymousUser")
                && authentication.isAuthenticated()) {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            User user = userService.findUserByEmail(principal.getEmail()).orElse(null);
            if(user == null){
                SecurityContextHolder.clearContext();
            }
            else if(!user.getStatus().equals(principal.getStatus())){
                authentication.setAuthenticated(false);
                principal.setStatus("BLOCKED");
            }
        }
        filterChain.doFilter(servletRequest , servletResponse);
    }
}
