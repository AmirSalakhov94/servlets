package tech.itpark.proggerhub.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@AllArgsConstructor
@Data
public class RestoreModel {
    private String login;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnTypeRestoreIssue;
}
