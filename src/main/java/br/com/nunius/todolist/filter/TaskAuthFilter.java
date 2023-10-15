package br.com.nunius.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nunius.todolist.user.UserRepositoryInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TaskAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks/")) {
            String encodedAuth = request.getHeader("Authorization")
                    .substring("Basic".length()).trim();
            byte[] decodedAuth = Base64.getDecoder().decode(encodedAuth);
            String[] credentials = new String(decodedAuth).split(":");

            String username = credentials[0];
            String password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "you may log in to add a task");
            } else {
                var passwordCheck = BCrypt.verifyer().verify(password.toCharArray(),
                        user.getPassword());
                if (passwordCheck.verified) {
                    request.setAttribute("userId", user.getId());
                    chain.doFilter(request, response);
                } else {
                    response.sendError(401, "wrong password");
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
