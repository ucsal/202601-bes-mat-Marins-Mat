package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.Resposta;
import br.com.ucsal.olimpiadas.model.Tentativa;
import br.com.ucsal.olimpiadas.repositories.TentativaRepository;

import java.util.List;

public class TentativaService {

    private final TentativaRepository tentativaRepository;

    public TentativaService(TentativaRepository tentativaRepository) {
        this.tentativaRepository = tentativaRepository;

    }

    public Long addTentativa(Long participanteId, Long provaId){

        var tentativa = new Tentativa();
        tentativa.setParticipanteId(participanteId);
        tentativa.setProvaId(provaId);

        tentativaRepository.add(tentativa);

        return tentativa.getId();
    }

    public void addResposta(Long id, Questao q, char m) {
        Tentativa tentativa = tentativaRepository.findById(id);
        tentativa.getRespostas().add(new Resposta(q.getId(), m, q.isRespostaCorreta(m)));
    }

    public int calcularNota(Long id) {
        Tentativa tentativa = findById(id);
        return tentativa.calcularNota();
    }

    public List<Resposta> getRespostas(Long id) {
        Tentativa tentativa = findById(id);
        return tentativa.getRespostas();
    }

    public boolean isEmpty() {
        return tentativaRepository.getTentativas().isEmpty();
    }

    public Tentativa findById(Long id) {
        return tentativaRepository.getTentativas().stream().filter(t -> t.getId()==id).toList().getFirst();
    }

    public List<Tentativa> getTentativas() {
        return tentativaRepository.getTentativas();
    }
}
