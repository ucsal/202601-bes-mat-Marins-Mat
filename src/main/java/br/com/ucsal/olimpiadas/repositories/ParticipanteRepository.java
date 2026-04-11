package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.Participante;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteRepository {

    private List<Participante> participantes;
    private long proximoId;

    public ParticipanteRepository() {
        participantes = new ArrayList<>();
        proximoId = 1;
    }

    public void add(Participante p){
        p.setId(proximoId++);
        participantes.add(p);
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public Participante findById(Long id) {
        return participantes.stream().filter(p -> p.getId()==id).findFirst().orElse(null);
    }
}
