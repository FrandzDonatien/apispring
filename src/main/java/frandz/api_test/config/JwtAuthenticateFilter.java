package frandz.api_test.config;

import frandz.api_test.constant.SecurityConstant;
import frandz.api_test.model.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //recuperation du header de request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        boolean isTokenExpired;
        //Jwt tokenDansLaBase = null;
        if(authHeader == null ||!authHeader.startsWith(SecurityConstant.TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        //si le header contient le token, on passe à l'extraction
        jwt = authHeader.substring(7);
        //extraction d'user dans le jwt
        userEmail = jwtService.extractUsername(jwt);
        Jwt tokenDansLaBase = this.jwtService.tokenByValue(jwt);
        isTokenExpired = this.jwtService.isTokenExpired(jwt);
        if (!isTokenExpired && Objects.equals(userEmail, tokenDansLaBase.getUser().getEmail()) && !tokenDansLaBase.getExpire() && !tokenDansLaBase.getDesactive()
                && tokenDansLaBase.getUser().getEmail().equals(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
