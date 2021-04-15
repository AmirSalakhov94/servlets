package tech.itpark.proggerhub.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@AllArgsConstructor
@Data
public class UserWithRestoreModel {
    private String login;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnRestoreIssue;
}
