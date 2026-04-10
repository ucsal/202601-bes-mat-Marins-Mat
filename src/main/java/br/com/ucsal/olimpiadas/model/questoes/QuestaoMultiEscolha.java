package br.com.ucsal.olimpiadas.model.questoes;

public class QuestaoMultiEscolha extends Questao{

    public QuestaoMultiEscolha() {
        setAlternativas(new String [5]);
    }

    @Override
    public char normalizar(char c) {
        char up = Character.toUpperCase(c);
        if (up < 'A' || up > 'E') {
            throw new IllegalArgumentException("Alternativa deve estar entre A e E.");
        }
        return up;
    }





}
