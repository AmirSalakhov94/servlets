package tech.itpark.proggerhub.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserPasswordRestoreModel {
    private long userId;
    private String key;
    private boolean isActive;
}
