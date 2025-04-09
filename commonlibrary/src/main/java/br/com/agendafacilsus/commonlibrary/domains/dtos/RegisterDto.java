package br.com.agendafacilsus.commonlibrary.domains.dtos;


import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;

public record RegisterDto(String name, String login, String password, UserRole role) { }
