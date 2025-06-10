package com.desafio.itau.mapper;

import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.dto.TransacaoResponseDTO;
import com.desafio.itau.entities.Transacao;

public class TransacaoMapper {
    public static Transacao toEntity(TransacaoRequestDTO dto) {
        if(dto == null) return null;

        return new Transacao(dto.getValor());
    }

    public static TransacaoResponseDTO toResponse(Transacao transacao) {
        if(transacao == null) return null;

        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.setValor(transacao.getValor());
        dto.setDataHora(transacao.getDataHora());

        return dto;
    }
}
