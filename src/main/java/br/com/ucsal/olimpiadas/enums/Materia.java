package br.com.ucsal.olimpiadas.enums;

import java.util.Arrays;
import java.util.List;

public enum Materia {
    XADREZ(1L, "Xadrez"),
    PORTUGUES(2L, "Português"),
    MATEMATICA(3L, "Matemática"),
    PROGRAMACAO(4L, "Programação");

    private final Long id;
    private final String nome;

    Materia(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static Materia findById(Long id) {
        return Arrays.stream(values()).filter(materia -> materia.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Materia> getValues() {
        return Arrays.asList(values());
    }
}
