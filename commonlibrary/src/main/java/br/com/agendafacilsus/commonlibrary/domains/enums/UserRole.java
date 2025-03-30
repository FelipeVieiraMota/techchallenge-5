package br.com.agendafacilsus.commonlibrary.domains.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    PACIENTE("paciente"),
    MEDICO("medico");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

}
