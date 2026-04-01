package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.Questao;

import java.util.ArrayList;
import java.util.List;

public class QuestaoRepository {

    private final List<Questao> questoes;
    private long proximoId;

    public QuestaoRepository() {
        this.questoes = new ArrayList<>();
    }

    public void add(Questao questao) {

        questao.setId(proximoId++);
        questoes.add(questao);
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }
}
