package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.*;
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

    public void cadastrarQuestao(Long provaID, String enunciado, String [] alternativas, char correta, TipoQuestao tipo, Materia materia) {

        Prova prova = provaRepository.findById(provaID);

        var questao = new Questao();
        questao.setQuestaoNaProva(findByProvaId(provaID).size()+1);
        questao.setProvaId(prova.getId());
        questao.setEnunciado(enunciado);
        questao.setAlternativas(alternativas);
        questao.setAlternativaCorreta(correta);

        questaoRepository.add(questao);
    }

    public void cadastrarQuestao(Long provaID, String fenInicial, String enunciado, String [] alternativas, char correta, TipoQuestao tipo, Materia materia) {

        Prova prova = provaRepository.findById(provaID);

        var questao = new QuestaoXadrez();
        questao.setQuestaoNaProva(findByProvaId(provaID).size()+1);
        questao.setFenInicial(fenInicial);
        questao.setProvaId(prova.getId());
        questao.setEnunciado(enunciado);
        questao.setAlternativas(alternativas);
        questao.setAlternativaCorreta(correta);

        questaoRepository.add(questao);
    }

    public Character validarAlternativa(char alt){

        return (Character) Questao.normalizar(alt);

    }

    public List<Questao> findByProvaId(Long provaId) {
        return questaoRepository.getQuestoes().stream().filter(q -> q.getProvaId()==provaId).toList();
    }



}
