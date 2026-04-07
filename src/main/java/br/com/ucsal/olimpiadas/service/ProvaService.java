package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Materia;
import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.TipoQuestao;
import br.com.ucsal.olimpiadas.repositories.ProvaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProvaService {

    private final ProvaRepository provaRepository;


    public ProvaService(ProvaRepository provaRepository) {
        this.provaRepository = provaRepository;
    }

    public void cadastrarProva(String titulo, List<TipoQuestao> tipos, List<Materia> materias) {

        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Titulo Inválido");

        if (tipos.isEmpty())
            throw new IllegalArgumentException("Nenhum tipo cadastrado");


        if (materias.isEmpty())
            throw new IllegalArgumentException("Nenhuma materia cadastrada");

        var prova = new Prova();
        prova.setTitulo(titulo);
        prova.setTiposDeQuestao(tipos);
        prova.setMaterias(materias);
        provaRepository.add(prova);
    }

    public Prova findProvaById(Long id) {
        return provaRepository.getProvas().stream().filter(p -> id.equals(p.getId())).findFirst().orElse(null);

    }

    public List<Prova> getProvas() {
        return provaRepository.getProvas();
    }

    public boolean isEmpty() {
        return provaRepository.getProvas().isEmpty();

    }

    public boolean anyMatch(Long id){
        return provaRepository.findById(id)!=null;
    }
}


