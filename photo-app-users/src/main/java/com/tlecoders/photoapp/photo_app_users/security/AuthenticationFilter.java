package com.tlecoders.photoapp.photo_app_users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlecoders.photoapp.photo_app_users.model.LoginRequestModel;
import com.tlecoders.photoapp.photo_app_users.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UsersService usersService;
    public AuthenticationFilter(AuthenticationManager authenticationManager,UsersService usersService)
    {

        // adding the authentication manager to Super class
        super(authenticationManager);
        this.usersService=usersService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
                                                throws AuthenticationException
    {
        try{
            LoginRequestModel cred=new ObjectMapper().readValue(request.getInputStream(),LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(cred.getEmail(),cred.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
    {
        User userObject = (User) authResult.getPrincipal();
        String username = userObject.getUsername();

    }

}
