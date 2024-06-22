package vn.edu.vti.vmall.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.vti.vmall.security.VMallAuthentication;
import vn.edu.vti.vmall.security.config.PublicURLConfigProperties;
import vn.edu.vti.vmall.security.util.JwtUtil;

@Slf4j
@Component
@Configuration
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtTokenFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String token = extractTokenFromHeader(request);
    if (token == null) {
      log.warn("(doFilterInternal)Token is null");
      filterChain.doFilter(request, response);
      return;
    }
    var claims = jwtUtil.validate(token);
    if (claims == null) {
      filterChain.doFilter(request, response);
      return;
    }
    var authentication = VMallAuthentication.builder()
        .username(claims.get("username").toString())
        .authenticated(true)
        .build();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  private String extractTokenFromHeader(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getHeader("Authorization");
  }
}
