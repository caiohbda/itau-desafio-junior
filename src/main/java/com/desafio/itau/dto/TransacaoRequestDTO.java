package com.desafio.itau.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoRequestDTO {
    @NotNull
    BigDecimal valor;
}


