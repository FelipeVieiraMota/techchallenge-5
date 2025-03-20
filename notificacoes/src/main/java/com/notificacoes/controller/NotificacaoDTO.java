package com.notificacoes.controller;

import com.notificacoes.enums.TipoNotificacao;

public record NotificacaoDTO(String nome, String destinatario, String mensagem, TipoNotificacao tipo) {}

