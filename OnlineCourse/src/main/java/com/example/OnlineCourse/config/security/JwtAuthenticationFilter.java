package com.example.OnlineCourse.config.security;

import com.example.OnlineCourse.config.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // İzin verilen endpoint'leri atla //istek ilk buraya düşüyor bu pathleri kontrol ediyor eğer geçiş yoksa token kontorlüne geçiyor
        String path = request.getRequestURI();
        if(     path.equals("/instructor/create") ||
                       path.equals("/instructor/login") ||
                       path.equals("/users/create") ||
                       path.equals("/users/login") ||
                       path.equals("/admin/register") ||
                       path.equals("/admin/login") ||
                       path.equals("/Course-Type") ||
                       path.startsWith("/Course/getById/") ||
                       path.equals("/Course/getAll") ||
                       path.equals("/role/create")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Token al ve doğrula
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            if (role == null || role.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Role başına ROLE_ ekle (gerekliyse)
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            // Spring Security'ye kullanıcıyı tanıt
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username, null, AuthorityUtils.createAuthorityList(role));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Filtre zincirine devam et
        filterChain.doFilter(request, response);
    }

    // Bearer token'ı header'dan çek
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}




