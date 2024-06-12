package vn.edu.vti.vmall.api.common.exception;

import org.springframework.http.HttpStatus;

public interface VMallExceptionInfo {
  String getErrorCode();

  String getErrorDescription();

  HttpStatus getHttpStatus();
}
