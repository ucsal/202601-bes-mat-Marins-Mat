package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.model.Materia;
import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.model.TipoQuestao;
import br.com.ucsal.olimpiadas.model.questoes.DadosQuestao;
import br.com.ucsal.olimpiadas.repositories.MateriaRepository;
import br.com.ucsal.olimpiadas.repositories.TipoQuestaoRepository;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;

import java.util.Arrays;
import java.util.List;

public class DataSeeder {

    static void seed(ParticipanteService participanteService, ProvaService provaService, QuestaoService questaoService, TipoQuestaoRepository tipoQuestaoRepository, MateriaRepository materiaRepository) {

        participanteService.cadastrarParticipante("Mateus", "");

        List<TipoQuestao> tipos = tipoQuestaoRepository.getTiposDeQuestao();

        List<Materia> materias = materiaRepository.getMaterias();

        Prova prova = provaService.cadastrarProva("Olimpíada 2026 • Nível 1 • Prova A", tipos, Arrays.asList(materias.getFirst()));


        String fenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");


        DadosQuestao dados = new DadosQuestao("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""", new String[] {"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"}, 'C');

        questaoService.cadastrarQuestao(prova.getId(), fenInicial, dados.enunciado(), dados.alternativas(), dados.correta(), tipos.getFirst(), materias.getFirst());


        prova = provaService.cadastrarProva("Prova teste", tipos, Arrays.asList(materias.getLast()));

        List<DadosQuestao> questoesDaProvaTeste =List.of(
                new DadosQuestao("Questão 1 — A", new String[]{"A) a", "B) b", "C) c", "D) d", "E) e"}, 'A'),
                new DadosQuestao("Questão 2 — B", new String[]{"A) a", "B) b", "C) c", "D) d", "E) e"}, 'B'),
                new DadosQuestao("Questão 3 — C", new String[]{"A) a", "B) b", "C) c", "D) d", "E) e"}, 'C')
        );

        for (var q : questoesDaProvaTeste) {
            questaoService.cadastrarQuestao(prova.getId(), q.enunciado(), q.alternativas(), q.correta(), tipos.getLast(), materias.getLast());
        }

    }

}
