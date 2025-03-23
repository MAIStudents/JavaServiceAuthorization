package ru.mai.lessons.rpks.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mai.lessons.rpks.exception.ParseTokenException;
import ru.mai.lessons.rpks.utils.TokenUtils;

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

    String authHeader = getHeader(request);

    String token;
    try{
      token = TokenUtils.extractToken(authHeader);
    } catch(ParseTokenException e) {
      log.warn("error with extraction token: {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (token == null) {
      log.warn("токен пустой");
      filterChain.doFilter(request, response);
      return;
    }

    if (!jwtVerifierService.verify(token)) {
      log.warn("токен битый");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String username = TokenUtils.getSubject(token);
    log.info("Извлечённый username из токена: {}", username);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      TokenAuthentication tokenAuthentication = new TokenAuthentication(username);
      SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
      log.info("Аутентификация установлена для пользователя: {}", username);
    } else {
      log.warn("Субъект (username) из токена равен null");
    }


    filterChain.doFilter(request, response);
  }

  private String getHeader(HttpServletRequest request) {
    return request.getHeader(TOKEN_HEADER);
  }
}
