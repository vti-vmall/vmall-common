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
  private final List<AntPathRequestMatcher> PERMIT_ALL_MATCHERS;

  public JwtTokenFilter(JwtUtil jwtUtil, PublicURLConfigProperties publicURLConfigProperties) {
    this.jwtUtil = jwtUtil;
    PERMIT_ALL_MATCHERS = publicURLConfigProperties.getUrls()
        .stream().map(
            AntPathRequestMatcher::new
        ).collect(Collectors.toList());
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    if (isPublicPath(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = extractTokenFromHeader(request);
    if (token == null) {
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

  private boolean isPublicPath(HttpServletRequest request) {
    for (AntPathRequestMatcher matcher : PERMIT_ALL_MATCHERS) {
      if (matcher.matches(request)) {
        return true;
      }
    }
    return false;
  }
}
