package ru.mai.lessons.rpks.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

    if (request.getRequestURI().startsWith("/swagger-ui/**")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = getToken(request);

    if (token == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    if (!jwtVerifierService.verify(token)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    return request.getHeader(TOKEN_HEADER);
  }
}
