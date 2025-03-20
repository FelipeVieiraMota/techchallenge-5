package br.com.agendafacilsus.notificacoes.controller;

import br.com.agendafacilsus.notificacoes.usecase.EnviarNotificacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final EnviarNotificacaoUseCase enviarNotificacaoUseCase;
    private static final String NOME_FILA = "notificacao.exchange.consumidor-notificacoes";

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        enviarNotificacaoUseCase.enviar(notificacaoDTO);
        return ResponseEntity.ok("Notificação enviada com sucesso!");
    }

    @GetMapping("/QtdNotificacoesEnviadas")
    public ResponseEntity<String> getTotalMensagensEnviadas() {
        int totalMensagensEnviadas = enviarNotificacaoUseCase.getTotalNotificacoesEnviadas(NOME_FILA);
        return ResponseEntity.ok("Total de notificações na enviadas: " + totalMensagensEnviadas);
    }

}
