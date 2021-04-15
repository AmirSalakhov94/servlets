package tech.itpark.proggerhub.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@AllArgsConstructor
@Data
public class UserModel {
    private String login;
    private String password;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnRestoreIssue;
}
