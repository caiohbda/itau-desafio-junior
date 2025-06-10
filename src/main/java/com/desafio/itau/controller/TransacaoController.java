package com.desafio.itau.controller;

import com.desafio.itau.dto.EstatisticasDTO;
import com.desafio.itau.dto.TransacaoRequestDTO;

import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Operation(summary = "Cria uma nova transação", description = "Registra uma nova transação financeira.")
    @PostMapping("/transacao")
    public ResponseEntity<Void> createTransacao(@Valid @RequestBody TransacaoRequestDTO dto) {
        transacaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retorna as estatísticas", description = "Calcula e retorna as estatísticas das transações ocorridas nos últimos 60 segundos.")
    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticasDTO> getEstatisticas(@RequestParam(name = "segundos", defaultValue = "60") int segundos) {
        EstatisticasDTO estatisticas = transacaoService.getEstatisticas(segundos);
        return ResponseEntity.ok(estatisticas);
    }

    @DeleteMapping("/transacao")
    @Operation(summary = "Apaga todas as transações", description = "Remove todos os registros de transações. Use com cuidado.")
    public ResponseEntity<Void> deleteTransacoes() {
        transacaoService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
