package com.desafio.itau.repository;

import com.desafio.itau.entities.Transacao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransacaoRepository {
    private static final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public Transacao save(Transacao transacao) {
        transacoes.add(transacao);
        return transacao;
    }

    public List<Transacao> findAll() {
        return transacoes;
    }

    public void deleteAll() {
        transacoes.clear();
    }
}
