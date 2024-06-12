package vn.edu.vti.vmall.api.common.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import vn.edu.vti.vmall.api.common.exception.handler.VMallExceptionHandler;

@Import({
    VMallExceptionHandler.class
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableExceptionHandler {

}
