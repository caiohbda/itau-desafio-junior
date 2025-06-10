package com.desafio.itau.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransacaoResponseDTO {
    BigDecimal valor;
    OffsetDateTime dataHora;
}

