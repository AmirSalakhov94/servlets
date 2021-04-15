package tech.itpark.proggerhub.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@AllArgsConstructor
@Data
public class UserModel {
    private String login;
    private String hash;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnRestoreIssue;
}
