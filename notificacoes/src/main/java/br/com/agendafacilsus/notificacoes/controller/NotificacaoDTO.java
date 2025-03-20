package br.com.agendafacilsus.notificacoes.controller;

import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;

public record NotificacaoDTO(String nome, String destinatario, String mensagem, TipoNotificacao tipo) {}

