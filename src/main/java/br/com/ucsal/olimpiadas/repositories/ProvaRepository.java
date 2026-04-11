package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.Prova;

import java.util.List;
import java.util.ArrayList;


public class ProvaRepository {

    private List<Prova> provas;
    private long proximoId;

    public ProvaRepository() {
        provas = new ArrayList<>();
        proximoId = 1;
    }

    public void add(Prova prova){
            prova.setId(proximoId++);
            provas.add(prova);
    }

    public List<Prova> getProvas() {
        return provas;
    }

    public Prova findById(Long id) {
        return provas.stream().filter(p -> p.getId()==id).findFirst().orElse(null);
    }
}
