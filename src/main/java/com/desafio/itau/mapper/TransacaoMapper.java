package com.desafio.itau.mapper;

import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.entities.Transacao;

public class TransacaoMapper {
    public static Transacao toEntity(TransacaoRequestDTO dto) {
        if(dto == null) return null;

        return new Transacao(dto.getValor());
    }

}
