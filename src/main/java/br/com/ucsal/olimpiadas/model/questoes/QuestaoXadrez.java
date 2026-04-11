package br.com.ucsal.olimpiadas.model.questoes;

import br.com.ucsal.olimpiadas.ChessboardRenderer;
import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;

public class QuestaoXadrez extends Questao{

    private String fenInicial;

    public QuestaoXadrez(long id, long provaId, String enunciado, String[] alternativas,char alternativaCorreta, int questaoNaProva, TipoQuestao tipoQuestao, Materia materia, String fenInicial) {
        super(id, provaId, enunciado, alternativas, alternativaCorreta, questaoNaProva, tipoQuestao, materia);
        this.fenInicial = fenInicial;
    }


    @Override
    public void exibirMaisDetalhes() {
        ChessboardRenderer.imprimirTabuleiroFen(this.fenInicial);
    }

    public String getFenInicial() {
        return fenInicial;
    }

    public void setFenInicial(String fenInicial) {
        this.fenInicial = fenInicial;
    }
}
