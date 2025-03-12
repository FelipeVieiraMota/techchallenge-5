package com.rabbitmq.controller;

import com.rabbitmq.gateway.EnviarNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final EnviarNotificacao<Object> notificacaoProducer;

    @PostMapping("/enviar")
    public String enviarNotificacao(@RequestBody Object mensagem) {
        notificacaoProducer.enviar(mensagem);
        return "Mensagem enviada!";
    }
}
