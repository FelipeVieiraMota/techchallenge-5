package br.com.agendafacilsus.notificacoes.infrastructure.dto;

import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;

public record NotificacaoDTO(String nome, String destinatario, String mensagem, TipoNotificacao tipo) {}

