package tech.itpark.proggerhub.dto.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TypeRestoreIssue {

    NAME_FIRST_PET("Имя первого питомца?"),
    MOTHER_MAIDEN_NAME("Девичья фамилия матери?"),
    BORN_CITY("Город в котором родились?");

    private final String description;
}
