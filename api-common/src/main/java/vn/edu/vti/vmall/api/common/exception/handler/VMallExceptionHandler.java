package vn.edu.vti.vmall.api.common.exception.handler;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vti.vmall.api.common.exception.VMallException;
import vn.edu.vti.vmall.api.common.exception.VMallExceptionInfo;
import vn.edu.vti.vmall.api.common.response.ApiResponse;

@Configuration
@ControllerAdvice
@Slf4j
public class VMallExceptionHandler {
  public static final String BASE_NAME = "messages";

  @ExceptionHandler(VMallException.class)
  public ResponseEntity<ApiResponse<?>> handleRestException(VMallException e){
    VMallExceptionInfo exceptionInfo = e.getExceptionInfo();
    return ResponseEntity.status(exceptionInfo.getHttpStatus()).body(ApiResponse.error(
        getErrorMessage(Locale.getDefault(), exceptionInfo.getErrorCode(), exceptionInfo.getErrorDescription())
    ));
  }

  private String getErrorMessage(Locale locale, String key, String defaultValue) {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
      String val = resourceBundle.getString(key);
      return new String(val.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("getErrorMessage - Exception with message: [{}]", e.getMessage());
      return defaultValue;
    }
  }
}
