package com.tlecoders.photoapp.api_gateway;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment environment;

    // to tell the super class which config class to use in the apply method
    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
            {
                return onError(exchange,"No Authorization header found", HttpStatus.UNAUTHORIZED);

            }
            List<String> strings = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
            String authorizationHeader=strings.get(0);
            String jwt=authorizationHeader.replace("Bearer","").trim();
            if(!isJwtValid(jwt))
                return onError(exchange,"Token invalid", HttpStatus.UNAUTHORIZED);

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();

    }

    public static class Config
    {
        //put config details here
    }

    private boolean isJwtValid(String jwt)
    {
        boolean isValid=true;
        String subject=null;
        String tokenSecret=environment.getProperty("token.secret");
        if(tokenSecret==null)
            return false;
        SecretKey key = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        JwtParser parser= Jwts.parser()
                .verifyWith(key)
                .build();
        try{
            Jws<Claims> claimsJws = parser.parseSignedClaims(jwt);
            subject = claimsJws.getPayload().getSubject();

        }
        catch (Exception ex)
        {
            isValid=false;
        }
        if(subject==null || subject.isEmpty())
            isValid=false;

        return isValid;
    }
}
