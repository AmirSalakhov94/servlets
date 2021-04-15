package tech.itpark.proggerhub.dto;

import lombok.Data;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;

@Data
public class RestoreDto {
    private String login;
    private TypeRestoreIssue typeRestoreIssue;
    private String valueOnTypeRestoreIssue;
}
