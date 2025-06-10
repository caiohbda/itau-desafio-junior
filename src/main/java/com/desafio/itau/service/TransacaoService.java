package com.desafio.itau.service;

import com.desafio.itau.dto.EstatisticasDTO;
import com.desafio.itau.dto.TransacaoRequestDTO;

public interface TransacaoService {
    void create(TransacaoRequestDTO transacaoRequestDTO);
    void delete();
    EstatisticasDTO getEstatisticas(int segundos);
}
