package ru.mai.lessons.rpks.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mai.lessons.rpks.services.UserService;
import ru.mai.lessons.rpks.utils.TokenUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilterImpl extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";

    private final JwtVerifierService jwtVerifierService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = TokenUtils.extractToken(getToken(request));

        if (token != null && jwtVerifierService.verify(token)) {
            String username = TokenUtils.getSubject(token);

            try {
                userService.loadUserByUsername(username);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User not found");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token verification failed");
        }
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(TOKEN_HEADER);
    }
}