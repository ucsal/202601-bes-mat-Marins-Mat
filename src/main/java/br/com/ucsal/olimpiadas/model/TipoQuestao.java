package br.com.ucsal.olimpiadas.model;

public class TipoQuestao {

    private Long id;
    private String descricao;

    public TipoQuestao(Long id, String descricao) {
        this.id=id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
