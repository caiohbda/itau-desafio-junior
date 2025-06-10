package com.desafio.itau.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Transacao {
    private BigDecimal valor;
    private OffsetDateTime dataHora;

    public Transacao(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da transação não pode ser nulo ou negativo.");
        }
        this.valor = valor;
        this.dataHora = OffsetDateTime.now();
    }
}
