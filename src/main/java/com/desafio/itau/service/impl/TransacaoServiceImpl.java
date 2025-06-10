package com.desafio.itau.service.impl;

import com.desafio.itau.dto.EstatisticasDTO;
import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.entities.Transacao;
import com.desafio.itau.mapper.TransacaoMapper;
import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public void create(TransacaoRequestDTO transacaoRequestDTO) {
        if (transacaoRequestDTO == null) {
            throw new IllegalArgumentException("o corpo da requisição não pode estar vazio");
        }

        if (transacaoRequestDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da transação não pode ser negativo");
        }

        Transacao transacao = TransacaoMapper.toEntity(transacaoRequestDTO);
        Transacao savedTransacao = transacaoRepository.save(transacao);
    }

    @Override
    public void delete() {
        transacaoRepository.deleteAll();
    }

    @Override
    public EstatisticasDTO getEstatisticas() {
        long countResult = count();
        BigDecimal sumResult = sum();
        BigDecimal avgResult = avg();
        BigDecimal minResult = min();
        BigDecimal maxResult = max();

        return new EstatisticasDTO(countResult, sumResult, avgResult, minResult, maxResult);
    }

    private List<Transacao> getTransacoesRecentes() {
        OffsetDateTime limiteDeTempo = OffsetDateTime.now().minusSeconds(60);

        return transacaoRepository.findAll().stream()
                .filter(transacao -> transacao.getDataHora().isAfter(limiteDeTempo))
                .toList();
    }

    private long count() {
        return getTransacoesRecentes().size();
    }

    private BigDecimal sum() {
        List<Transacao> transacoesRecentes = getTransacoesRecentes();
        return transacoesRecentes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal avg() {
        List<Transacao> transacoesRecentes = getTransacoesRecentes();
        if (transacoesRecentes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = sum();
        BigDecimal count = new BigDecimal(transacoesRecentes.size());

        return sum.divide(count, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal min() {
        List<Transacao> transacoesRecentes = getTransacoesRecentes();

        return transacoesRecentes.stream()
                .map(Transacao::getValor)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal max() {
        List<Transacao> transacoesRecentes = getTransacoesRecentes();

        return transacoesRecentes.stream()
                .map(Transacao::getValor)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }
}
