package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.Tentativa;

import java.util.ArrayList;
import java.util.List;

public class TentativaRepository {

    private final List<Tentativa> tentativas;
    private long proximoId;

    public TentativaRepository() {
        tentativas = new ArrayList<>();

    }

    public void add(Tentativa tentativa){
        tentativa.setId(proximoId++);
        tentativas.add(tentativa);
    }

    public Tentativa findById(Long id) {
        return tentativas.stream().filter(tentativa -> tentativa.getId()==id).toList().getFirst();
    }

    public List<Tentativa> getTentativas() {
        return tentativas;
    }
}
