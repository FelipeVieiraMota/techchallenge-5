package com.notificacoes.domain.entidade;

public record NotificacaoDTO(String nome, String destinatario, String mensagem, TipoNotificacao tipo) {}

