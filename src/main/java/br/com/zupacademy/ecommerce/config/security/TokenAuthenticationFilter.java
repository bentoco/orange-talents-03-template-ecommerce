package br.com.zupacademy.ecommerce.config.security;

import br.com.zupacademy.ecommerce.user.User;
import br.com.zupacademy.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final TokenManager tokenManager;
    private final UserRepository repository;

    public TokenAuthenticationFilter (
            TokenManager tokenManager ,
            UserRepository repository ) {
        this.tokenManager = tokenManager;
        this.repository = repository;
    }

    @Override protected void doFilterInternal (
            HttpServletRequest request ,
            HttpServletResponse response ,
            FilterChain filterChain ) throws ServletException, IOException {

        Optional<String> token = getTokenFromRequest(request);

        if (token.isPresent() && tokenManager.isValid(token.get())) {
            Long idUsuario = tokenManager.getIdUsuario(token.get());
            Optional<User> possibleUser = repository.findById(idUsuario);
            if (possibleUser.isPresent()) {
                User user = possibleUser.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user , null , user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND , "usuario nao encontrado");
            }
        }
        filterChain.doFilter(request , response);
    }

    private Optional<String> getTokenFromRequest ( HttpServletRequest request ) {
        String authToken = request.getHeader("Authorization");
        return Optional.ofNullable(authToken);
    }
}
