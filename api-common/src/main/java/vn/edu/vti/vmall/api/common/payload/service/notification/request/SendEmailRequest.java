package vn.edu.vti.vmall.api.common.payload.service.notification.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest {
  private String target;
  private String content;
}
