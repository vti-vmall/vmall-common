package vn.edu.vti.vmall.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VMallException extends RuntimeException {
  VMallExceptionInfo exceptionInfo;
}
