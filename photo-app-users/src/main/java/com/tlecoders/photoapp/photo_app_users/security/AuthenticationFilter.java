package com.tlecoders.photoapp.photo_app_users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlecoders.photoapp.photo_app_users.model.LoginRequestModel;
import com.tlecoders.photoapp.photo_app_users.service.UsersService;
import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;
import io.jsonwebtoken.Jwts;


import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UsersService usersService;
    private final Environment environment;
    public AuthenticationFilter(AuthenticationManager authenticationManager,UsersService usersService,Environment environment)
    {

        // adding the authentication manager to Super class
        super(authenticationManager);
        this.usersService=usersService;
        this.environment=environment;
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
        UsersDTO userDetails = usersService.getUserByEmail(username);
        Instant now=Instant.now();
        String tokenSecret=environment.getProperty("token.secret");

        SecretKey key = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        String jwtToken = Jwts.builder()
                .subject(userDetails.getUserId())
                .expiration(Date.from(now.plusMillis(Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time"))))))
                .issuedAt(Date.from(now))
                .signWith(key)
                .compact();
        response.addHeader("token",jwtToken);
        response.addHeader("userId",userDetails.getUserId());


    }

}
