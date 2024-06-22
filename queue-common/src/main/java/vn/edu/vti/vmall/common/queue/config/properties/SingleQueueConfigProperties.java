package vn.edu.vti.vmall.common.queue.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleQueueConfigProperties {
  private String queueName;
  private String exchange;
  private String routingKey;
}
