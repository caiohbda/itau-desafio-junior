package com.desafio.itau.service.impl;

import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.dto.TransacaoResponseDTO;
import com.desafio.itau.entities.Transacao;
import com.desafio.itau.mapper.TransacaoMapper;
import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public TransacaoResponseDTO create(TransacaoRequestDTO transacaoRequestDTO) {
        if (transacaoRequestDTO == null) {
            throw new IllegalArgumentException("o corpo da requisição não pode estar vazio");
        }

        if (transacaoRequestDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da transação não pode ser negativo");
        }

        Transacao transacao = TransacaoMapper.toEntity(transacaoRequestDTO);
        Transacao savedTransacao = transacaoRepository.save(transacao);
        return TransacaoMapper.toResponse(savedTransacao);
    }
}
