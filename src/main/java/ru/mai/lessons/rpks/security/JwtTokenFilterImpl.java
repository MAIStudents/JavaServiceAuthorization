package ru.mai.lessons.rpks.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mai.lessons.rpks.utils.TokenUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilterImpl extends OncePerRequestFilter {

  private static final String TOKEN_HEADER = "Authorization";

  private static final List<String> PUBLIC_ENDPOINTS = List.of(
          "/register",
          "/swagger-ui/",
          "/v3/api-docs/",
          "/actuator/"
  );

  private final JwtVerifierService jwtVerifierService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    if (isPublicEndpoint(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String authHeader = request.getHeader(TOKEN_HEADER);

    String token = TokenUtils.extractToken(authHeader);
    if (token == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token format");
      return;
    }

    String username;
    try {
      username = TokenUtils.getSubject(token);
    } catch (Exception exception) {
      log.warn("Failed to extract subject from token", exception);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token structure");
      return;
    }

    if (!jwtVerifierService.verify(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
      return;
    }

    setAuthenticationToContext(username);
    filterChain.doFilter(request, response);
  }

  private boolean isPublicEndpoint(HttpServletRequest request) {
    String uri = request.getRequestURI();

    return PUBLIC_ENDPOINTS.stream().anyMatch(uri::startsWith);
  }

  private void setAuthenticationToContext(String username) {
    TokenAuthentication authentication = new TokenAuthentication(username);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("Authentication set to context {}", authentication);
  }

}
