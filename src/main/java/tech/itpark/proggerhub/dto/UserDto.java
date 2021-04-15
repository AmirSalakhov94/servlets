package tech.itpark.proggerhub.dto;

import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@Data
public class UserDto {
    private String login;
    private String password;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnRestoreIssue;
}
