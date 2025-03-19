package com.notificacoes.controller;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.usecase.EnviarNotificacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final EnviarNotificacaoUseCase enviarNotificacaoUseCase;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        enviarNotificacaoUseCase.enviar(notificacaoDTO);
        return ResponseEntity.ok("Notificação enviada com sucesso!");
    }
}
