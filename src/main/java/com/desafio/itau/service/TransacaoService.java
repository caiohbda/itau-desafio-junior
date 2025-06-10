package com.desafio.itau.service;

import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.dto.TransacaoResponseDTO;

public interface TransacaoService {
    TransacaoResponseDTO create(TransacaoRequestDTO transacaoRequestDTO);
    void delete();
}
