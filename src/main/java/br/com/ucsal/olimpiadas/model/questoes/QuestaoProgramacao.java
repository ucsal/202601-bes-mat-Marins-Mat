package br.com.ucsal.olimpiadas.model.questoes;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;

public class QuestaoProgramacao extends Questao{

    public QuestaoProgramacao(long id, long provaId, String enunciado, String[] alternativas, char alternativaCorreta, int questaoNaProva, TipoQuestao tipoQuestao, Materia materia) {
        super(id, provaId, enunciado, alternativas, alternativaCorreta, questaoNaProva, tipoQuestao, materia);
    }

    @Override
    public void exibirMaisDetalhes() {

    }
}
