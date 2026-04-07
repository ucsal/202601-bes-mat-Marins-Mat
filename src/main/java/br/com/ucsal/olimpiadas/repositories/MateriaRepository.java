package br.com.ucsal.olimpiadas.repositories;

import br.com.ucsal.olimpiadas.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaRepository {
    private List<Materia> materias = new ArrayList<>();
    private Long proximoId = 1L;
    public MateriaRepository() {
        materias.add(new Materia(proximoId++, "Xadrez"));
        materias.add(new Materia(proximoId++, "Conhecimentos Gerais"));
    }

    public Materia findMateriaById(Long id){
        return materias.stream().filter(m -> m.getId()==id).findFirst().orElse(null);
    }

    public List<Materia> getMaterias() {
        return materias;
    }
}
