package br.com.ucsal.olimpiadas.model.factories;

import br.com.ucsal.olimpiadas.model.questoes.*;

public class QuestaoFactory {

    private static Long proximoId = 1L;

    public static Questao criar(Long provaId, DadosQuestao dados, int questaoNaprova,String fenInicial) {

        return switch (dados.materia()) {

            case XADREZ ->
                 new QuestaoXadrez(proximoId++, provaId, dados.enunciado(),dados.alternativas() ,dados.correta(), questaoNaprova, dados.tipo(), dados.materia(), fenInicial);

            case PORTUGUES ->
                 new QuestaoPortugues(proximoId++, provaId, dados.enunciado(), dados.alternativas(), dados.correta(), questaoNaprova, dados.tipo(), dados.materia());

            case MATEMATICA ->
                 new QuestaoMatematica(proximoId++, provaId, dados.enunciado(), dados.alternativas(), dados.correta(), questaoNaprova, dados.tipo(), dados.materia());

            case PROGRAMACAO ->
                 new QuestaoProgramacao(proximoId++, provaId, dados.enunciado(), dados.alternativas(), dados.correta(), questaoNaprova, dados.tipo(), dados.materia());

        };

    }


}
