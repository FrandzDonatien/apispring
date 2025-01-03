package frandz.api_test.responses;

import frandz.api_test.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {
    private HttpStatus status;
    private T data;
    private String message;
}
