package com.desafio.itau.controller;

import com.desafio.itau.dto.EstatisticasDTO;
import com.desafio.itau.dto.TransacaoRequestDTO;

import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
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

    @PostMapping("/transacao")
    public ResponseEntity<Void> createTransacao(@Valid @RequestBody TransacaoRequestDTO dto) {
        transacaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deleteTransacoes() {
        transacaoService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticasDTO> getEstatisticas() {
        EstatisticasDTO estatisticas = transacaoService.getEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}
