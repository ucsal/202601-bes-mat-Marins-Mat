package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;
import br.com.ucsal.olimpiadas.model.*;
import br.com.ucsal.olimpiadas.model.factories.QuestaoFactory;
import br.com.ucsal.olimpiadas.model.questoes.DadosQuestao;
import br.com.ucsal.olimpiadas.model.questoes.Questao;
import br.com.ucsal.olimpiadas.repositories.ProvaRepository;
import br.com.ucsal.olimpiadas.repositories.QuestaoRepository;

import java.util.List;

public class QuestaoService {


    QuestaoRepository questaoRepository;
    ProvaRepository provaRepository;

    public QuestaoService(QuestaoRepository questaoRepository, ProvaRepository provaRepository) {
        this.questaoRepository = questaoRepository;
        this.provaRepository = provaRepository;
    }

    public void cadastrarQuestao(Long provaID, DadosQuestao dados, String fenInicial) {

        Prova prova = provaRepository.findById(provaID);

        int questaoNaProva = findByProvaId(provaID).size()+1;

        Questao questao = QuestaoFactory.criar(provaID, dados, questaoNaProva, fenInicial);

        questaoRepository.add(questao);
    }

    public List<Questao> findByProvaId(Long provaId) {
        return questaoRepository.getQuestoes().stream().filter(q -> q.getProvaId()==provaId).toList();
    }



}
