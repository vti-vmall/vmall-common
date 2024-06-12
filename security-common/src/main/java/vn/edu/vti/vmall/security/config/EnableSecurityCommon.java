package vn.edu.vti.vmall.security.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import vn.edu.vti.vmall.security.filter.JwtTokenFilter;
import vn.edu.vti.vmall.security.util.JwtUtil;

@Import({
    JwtTokenFilter.class,
    JWTTokenProperties.class,
    JwtUtil.class
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSecurityCommon {

}
