package ch.bbzsogr.todo.authentication;

import ch.bbzsogr.todo.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An Implementation of a OncePerRequestFilter which is responsible
 * for the Authorization of each request
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final static String HEADER_NAME = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private JWTService jwtService;


    /**
     * Make sure each Request is authorized before it is forwarded to the controller
     *
     * @param request     The request that should be authorized
     * @param response    The Response which could be returned if the request is invalid
     * @param filterChain The Filter chain which contains the next Filter to execute
     * @throws ServletException If the next filter in the filter chain fails
     * @throws IOException      If the next filter in the filter chain fails
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_NAME);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(TOKEN_PREFIX.length());

        if (!jwtService.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String subject = jwtService.extractClaims(jwt, claims -> String.valueOf(claims.get("id")));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                subject,
                null,
                new ArrayList<>()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
