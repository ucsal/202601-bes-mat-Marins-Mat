package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Participante;
import br.com.ucsal.olimpiadas.repositories.ParticipanteRepository;

import java.util.List;

public class ParticipanteService {

    ParticipanteRepository participanteRepository;


    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;

    }

    public void cadastrarParticipante(String nome, String email) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        var p = new Participante();
        p.setNome(nome);
        p.setEmail(email);

        participanteRepository.add(p);
    }

    public Long validarParticipante(Long id) {

        try {
            boolean existe = participanteRepository.getParticipantes().stream().anyMatch(p -> p.getId() == id);
            if (!existe) {
                throw new IllegalArgumentException("ID inválido");
            }
            return id;
        } catch (Exception e) {
            throw new IllegalArgumentException("Entrada inválida");
        }
    }

    public List<Participante> getParticipantes() {
        return participanteRepository.getParticipantes();
    }

    public boolean isEmpty(){
        return participanteRepository.getParticipantes().isEmpty();
    }


}
