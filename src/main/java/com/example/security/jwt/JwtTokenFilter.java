package com.example.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.exceptions.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;

public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = tokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(request, response);
        } catch (JWTDecodeException e) {
            writeExceptionToResponse(response, request, e, HttpStatus.BAD_REQUEST);
        } catch (TokenExpiredException e) {
            writeExceptionToResponse(response, request, e, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println(e.getClass());
            writeExceptionToResponse(response, request, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Forum Question: Estou com problemas ao tentar tratar os erros do jwt
    private void writeExceptionToResponse(ServletResponse response, ServletRequest request, Exception e, HttpStatus httpStatus) throws IOException {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), ((HttpServletRequest) request).getRequestURI());
        HttpServletResponse myResponse = (HttpServletResponse) response;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonResponse = ow.writeValueAsString(exceptionResponse);
        myResponse.setStatus(httpStatus.value());
        myResponse.setContentType("application/json");
        myResponse.getWriter().write(jsonResponse);
    }
}
