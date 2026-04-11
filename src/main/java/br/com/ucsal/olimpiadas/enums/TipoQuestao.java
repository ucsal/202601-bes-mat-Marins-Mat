package br.com.ucsal.olimpiadas.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoQuestao {

    MULTIPLA_ESCOLHA(1L, "Múltipla Escolha") {
        @Override
        public char normalizar(char c) {
            char up = Character.toUpperCase(c);
            if (up < 'A' || up > 'E') {
                throw new IllegalArgumentException("Alternativa deve estar entre A e E.");
            }
            return up;
        }

    },
    VERDADEIRO_OU_FALSO(2L, "Verdadeiro ou Falso") {
        @Override
        public char normalizar(char c) {
            char up = Character.toUpperCase(c);
            if (up != 'V' && up != 'F') {
                throw new IllegalArgumentException("Alternativa deve ser V ou F.");
            }
            return up;
        }
    };

    private final Long id;
    private final String descricao;

    TipoQuestao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }


    public abstract char normalizar(char c);


    public static TipoQuestao findById(Long id) {
        return Arrays.stream(values()).filter(tipo -> tipo.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<TipoQuestao> getValues() {
        return Arrays.asList(values());
    }


}
