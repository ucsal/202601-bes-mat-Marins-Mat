package br.com.ucsal.olimpiadas.model.questoes;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;

import java.util.Arrays;

public abstract class Questao {

	private long id;
	private long provaId;


    private String enunciado;
	private String[] alternativas;
	private char alternativaCorreta;
    private int questaoNaProva;
    private TipoQuestao tipoQuestao;
    private Materia materia;

    public Questao(long id, long provaId, String enunciado, String[] alternativas,char alternativaCorreta, int questaoNaProva, TipoQuestao tipoQuestao, Materia materia) {
        this.id = id;
        this.provaId = provaId;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.alternativaCorreta = alternativaCorreta;
        this.questaoNaProva = questaoNaProva;
        this.tipoQuestao = tipoQuestao;
        this.materia = materia;
    }

    public boolean isRespostaCorreta(char marcada) {
        try {
            return tipoQuestao.normalizar(marcada) == alternativaCorreta;
        } catch (Exception e) {
            return false;
        }
	}


    public abstract void exibirMaisDetalhes();

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
        if (this.tipoQuestao==TipoQuestao.MULTIPLA_ESCOLHA) {
            if (alternativas == null || alternativas.length != 5) {
                throw new IllegalArgumentException("A questão deve possuir exatamente 5 alternativas.");
            }
            this.alternativas = Arrays.copyOf(alternativas, 5);
        } if (this.tipoQuestao==TipoQuestao.VERDADEIRO_OU_FALSO) {
            if (alternativas == null || alternativas.length != 2) {
                throw new IllegalArgumentException("A questão deve possuir exatamente 2 alternativas.");
            }
            this.alternativas = Arrays.copyOf(alternativas, 2);
        }
    }

    public void setAlternativaCorreta(char alternativaCorreta) {
        this.alternativaCorreta = this.tipoQuestao.normalizar(alternativaCorreta);
    }

    public int getQuestaoNaProva() {
        return questaoNaProva;
    }

    public void setQuestaoNaProva(int questaoNaProva) {
        this.questaoNaProva = questaoNaProva;
    }

    public char getAlternativaCorreta() {
        return alternativaCorreta;
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

    public TipoQuestao getTipoQuestao() {
        return tipoQuestao;
    }
}
