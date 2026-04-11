package br.com.ucsal.olimpiadas.model;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;

import java.util.List;

public class Prova {

	private long id;
	private String titulo;

	private List<br.com.ucsal.olimpiadas.enums.TipoQuestao> tiposDeQuestao;
	private List<Materia> materias;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<TipoQuestao> getTiposDeQuestao() {
		return tiposDeQuestao;
	}

	public void setTiposDeQuestao(List<TipoQuestao> tiposDeQuestao) {
		this.tiposDeQuestao = tiposDeQuestao;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public List<Materia> getMaterias() {
		return materias;
	}
}
