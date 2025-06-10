package com.desafio.itau.controller;

import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.dto.TransacaoResponseDTO;
import com.desafio.itau.entities.Transacao;
import com.desafio.itau.mapper.TransacaoMapper;
import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;
    @Autowired
    private TransacaoRepository transacaoRepository;

    @PostMapping("/transacoes")
    public ResponseEntity<Void> createTransacao(@Valid @RequestBody TransacaoRequestDTO dto) {
        transacaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
