package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.repositories.ProvaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProvaService {

    private final ProvaRepository provaRepository;


    public ProvaService(ProvaRepository provaRepository) {
        this.provaRepository = provaRepository;
    }

    public void cadastrarProva(String titulo) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo Inválido");
        }

        var prova = new Prova();
        prova.setTitulo(titulo);

        provaRepository.add(prova);
    }

    public Prova escolherProva(Long id) {
        return provaRepository.getProvas().get(id.intValue());
    }

    public Prova getProva(Long id) {
        boolean existe = getProvas().stream().anyMatch(p -> p.getId() == id);
        if (existe) {
            return provaRepository.getProvas().stream().filter(p -> id.equals(p.getId())).findFirst().orElse(null);
        }
        return null;
    }

    public List<Prova> getProvas() {
        return provaRepository.getProvas();
    }

    public boolean isEmpty() {
        return provaRepository.getProvas().isEmpty();

    }
}


