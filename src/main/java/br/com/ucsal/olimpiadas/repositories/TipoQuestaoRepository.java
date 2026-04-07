package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.TipoQuestao;

import java.util.ArrayList;
import java.util.List;

public class TipoQuestaoRepository {

    private List<TipoQuestao> tiposDeQuestao = new ArrayList<>();
    private Long proximoId = 1L;
    public TipoQuestaoRepository() {
        tiposDeQuestao.add(new TipoQuestao(proximoId++, "Multipla Escolha"));
        tiposDeQuestao.add(new TipoQuestao(proximoId++, "Verdadeiro ou Falso"));
    }

    public List<TipoQuestao> getTiposDeQuestao() {
        return tiposDeQuestao;
    }

    public TipoQuestao findById(Long id) {
        return tiposDeQuestao.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    }
}
