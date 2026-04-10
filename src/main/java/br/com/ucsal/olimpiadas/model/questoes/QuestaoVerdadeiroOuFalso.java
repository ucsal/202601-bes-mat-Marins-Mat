package br.com.ucsal.olimpiadas.model.questoes;

public class QuestaoVerdadeiroOuFalso extends Questao{

    public QuestaoVerdadeiroOuFalso() {
        setAlternativas(new String[2]);
    }

    @Override
    public char normalizar(char c) {
        char up = Character.toUpperCase(c);
        if (up != 'V' && up != 'F') {
            throw new IllegalArgumentException("Alternativa deve ser V ou F.");
        }
        return up;
    }
}
