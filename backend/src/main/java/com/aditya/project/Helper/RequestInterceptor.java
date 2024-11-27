package com.aditya.project.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


//to check whenever if a new request comes if it contains JWT and if it's not expired

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {



    private final JWTHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String authorizationHeader = request.getHeader("Authorization");

        //check if header is null, or  it doesn't start with Bearer
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = authorizationHeader.substring(7);
        String username = jwtHelper.extractUsername(token);

        //validate token-->token expired->extract claims-->logout
        if(username == null || !jwtHelper.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
