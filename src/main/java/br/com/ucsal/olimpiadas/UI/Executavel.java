package br.com.ucsal.olimpiadas.UI;

public interface Executavel {
    Long id = null;
    void executar();
    default Long getId() {
        return id;
    }
}
