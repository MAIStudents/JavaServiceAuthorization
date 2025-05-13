package ru.mai.lessons.rpks.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mai.lessons.rpks.utils.TokenUtils;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilterImpl extends OncePerRequestFilter {

  private static final String TOKEN_HEADER = "Authorization";

  private final JwtVerifierService jwtVerifierService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = getToken(request);
    final String jwtToken = TokenUtils.extractToken(authHeader);
    if (jwtToken == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      filterChain.doFilter(request, response);
      return;
    }
    final String username = TokenUtils.getSubject(jwtToken);
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      if (jwtVerifierService.verify(jwtToken)) {
        Authentication authToken = new TokenAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    return request.getHeader(TOKEN_HEADER);
  }
}
