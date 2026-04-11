package br.com.ucsal.olimpiadas.model.questoes;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;

public record DadosQuestao(String enunciado, String[] alternativas, char correta, TipoQuestao tipo, Materia materia) {
}
