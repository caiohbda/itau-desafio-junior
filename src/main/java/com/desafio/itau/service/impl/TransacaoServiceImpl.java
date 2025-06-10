package com.desafio.itau.service.impl;

import com.desafio.itau.dto.EstatisticasDTO;
import com.desafio.itau.dto.TransacaoRequestDTO;
import com.desafio.itau.entities.Transacao;
import com.desafio.itau.mapper.TransacaoMapper;
import com.desafio.itau.repository.TransacaoRepository;
import com.desafio.itau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.estatisticas.janela-de-tempo-segundos:60}")
    private int janelaDeTempo;

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
    public EstatisticasDTO getEstatisticas(int segundos) {
        List<Transacao> transacoesRecentes = getTransacoesRecentes(segundos);
        long countResult = count(transacoesRecentes);
        BigDecimal sumResult = sum(transacoesRecentes);
        BigDecimal avgResult = avg(transacoesRecentes);
        BigDecimal minResult = min(transacoesRecentes);
        BigDecimal maxResult = max(transacoesRecentes);

        return new EstatisticasDTO(countResult, sumResult, avgResult, minResult, maxResult);
    }

    private List<Transacao> getTransacoesRecentes(int segundos) {
        OffsetDateTime limiteDeTempo = OffsetDateTime.now().minusSeconds(segundos);

        return transacaoRepository.findAll().stream()
                .filter(transacao -> transacao.getDataHora().isAfter(limiteDeTempo))
                .toList();
    }

    private long count(List<Transacao> transacoes) {
        return transacoes.size();
    }

    private BigDecimal sum(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal avg(List<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal currentSum = sum(transacoes);
        BigDecimal currentCount = new BigDecimal(transacoes.size());
        return currentSum.divide(currentCount, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal min(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(Transacao::getValor)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal max(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(Transacao::getValor)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }
}
