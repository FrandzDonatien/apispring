package frandz.api_test.responses;

import frandz.api_test.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private Boolean accountNonExpired;
    private Boolean enable;
    private Date createdAt;
    private Date updatedAt;

}
