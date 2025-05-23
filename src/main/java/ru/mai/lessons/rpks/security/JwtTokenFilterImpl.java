package ru.mai.lessons.rpks.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mai.lessons.rpks.exception.ParseTokenException;
import ru.mai.lessons.rpks.models.UserClaims;
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

    if (isUnsecuredEndpoint(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String authHeader = getToken(request);

    String token;
    try{
      token = TokenUtils.extractToken(authHeader);
    } catch(ParseTokenException e) {
      log.warn("Header could not be parsed: {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (token == null || !jwtVerifierService.verify(token)) {
      log.warn("Token is invalid or empty");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    DecodedJWT decodedJWT = JWT.decode(token);
    UserClaims userAllDataFromToken;
    try{
      userAllDataFromToken = new UserClaims(decodedJWT);
    } catch (IllegalArgumentException e) {
      log.warn("Claims is incorrect!");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String username = userAllDataFromToken.subject();

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      TokenAuthentication tokenAuthentication = new TokenAuthentication(username);
      SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
    } else {
      log.warn("Token subject is null");
    }


    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    return request.getHeader(TOKEN_HEADER);
  }

  private boolean isUnsecuredEndpoint(HttpServletRequest request) {
    return "OPTIONS".equalsIgnoreCase(request.getMethod()) ||
            UnsecuredEndpointsProvider.PUBLIC_ENDPOINTS.stream()
                    .anyMatch(pattern -> new AntPathRequestMatcher(pattern).matches(request));
  }
}
