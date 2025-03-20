package br.com.notificacoes.controller;

import br.com.notificacoes.enums.TipoNotificacao;

public record NotificacaoDTO(String nome, String destinatario, String mensagem, TipoNotificacao tipo) {}

