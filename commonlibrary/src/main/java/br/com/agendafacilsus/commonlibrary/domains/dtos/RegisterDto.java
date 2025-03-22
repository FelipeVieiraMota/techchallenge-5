package br.com.agendafacilsus.commonlibrary.domains.dtos;


import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) { }
