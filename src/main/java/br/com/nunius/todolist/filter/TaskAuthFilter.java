package br.com.nunius.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TaskAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String encodedAuth = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        byte[] decodedAuth = Base64.getDecoder().decode(encodedAuth);
        String[] credentials = new String(decodedAuth).split(":");

        String username = credentials[0];
        String password = credentials[1];

        chain.doFilter(request, response);
    }
}
