package vn.edu.vti.vmall.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private ApiResponseStatus status;
  private T data;
  private String error;
  private String traceId;

  public static <T> ApiResponse<T> success(T data){
    var response = new ApiResponse<T>();
    response.setData(data);
    response.setStatus(ApiResponseStatus.SUCCESS);
    return response;
  }

  public static <T> ApiResponse<T> error(T data, String error){
    var response = new ApiResponse<T>();
    response.setError(error);
    response.setData(data);
    response.setStatus(ApiResponseStatus.ERROR);
    return response;
  }

  public static ApiResponse<?> error(String error){
    var response = new ApiResponse<>();
    response.setError(error);
    response.setStatus(ApiResponseStatus.ERROR);
    return response;
  }
}
