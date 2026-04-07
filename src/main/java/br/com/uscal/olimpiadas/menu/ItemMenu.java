package br.com.uscal.olimpiadas.menu;

import br.com.ucsal.olimpiadas.UI.Executavel;

public class ItemMenu {

    private Long id;
    private String descricao;
    private Executavel executavel;

    public ItemMenu(Long id, String descricao, Executavel executavel) {
        this.id = id;
        this.descricao = descricao;
        this.executavel = executavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Executavel getExecutavel() {
        return executavel;
    }

    public void setExecutavel(Executavel executavel) {
        this.executavel = executavel;
    }
}
