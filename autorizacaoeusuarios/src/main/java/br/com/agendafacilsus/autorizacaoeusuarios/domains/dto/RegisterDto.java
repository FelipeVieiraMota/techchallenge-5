package br.com.agendafacilsus.autorizacaoeusuarios.domains.dto;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) { }
