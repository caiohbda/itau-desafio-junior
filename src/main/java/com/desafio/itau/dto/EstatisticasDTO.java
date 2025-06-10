package com.desafio.itau.dto;

import java.math.BigDecimal;

public record EstatisticasDTO(
        long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {}