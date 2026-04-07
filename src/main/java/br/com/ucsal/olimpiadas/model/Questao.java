package br.com.ucsal.olimpiadas.model;

import java.util.Arrays;

public class Questao {

	private long id;
	private long provaId;

	private String enunciado;
	private String[] alternativas = new String[5];
	private char alternativaCorreta;
    private int questaoNaProva;
    private TipoQuestao tipoQuestao;
    private Materia materia;

    public TipoQuestao getTipoQuestao() {
        return tipoQuestao;
    }

    public void setTipoQuestao(TipoQuestao tipoQuestao) {
        this.tipoQuestao = tipoQuestao;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public boolean isRespostaCorreta(char marcada) {
        try {
            return normalizar(marcada) == alternativaCorreta;
        } catch (Exception e) {
            return false;
        }
	}

	public static char normalizar(char c) {
		char up = Character.toUpperCase(c);
		if (up < 'A' || up > 'E') {
			throw new IllegalArgumentException("Alternativa deve estar entre A e E.");
		}
		return up;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProvaId() {
        return provaId;
    }

    public void setProvaId(long provaId) {
        this.provaId = provaId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(String[] alternativas) {
        if (alternativas == null || alternativas.length != 5) {
            throw new IllegalArgumentException("A questão deve possuir exatamente 5 alternativas.");
        }
        this.alternativas = Arrays.copyOf(alternativas, 5);
    }

    public char getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(char alternativaCorreta) {
        this.alternativaCorreta = normalizar(alternativaCorreta);
    }

    public int getQuestaoNaProva() {
        return questaoNaProva;
    }

    public void setQuestaoNaProva(int questaoNaProva) {
        this.questaoNaProva = questaoNaProva;
    }

}
