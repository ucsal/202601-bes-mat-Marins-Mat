package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;
import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.model.questoes.DadosQuestao;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;

import java.util.List;

public class DataSeeder {

    static void seed(ParticipanteService participanteService, ProvaService provaService, QuestaoService questaoService) {

        participanteService.cadastrarParticipante("Mateus", "");

        List<TipoQuestao> tipos = TipoQuestao.getValues();

        List<Materia> materias = Materia.getValues();

        Prova prova = provaService.cadastrarProva("Olimpíada 2026 • Nível 1 • Prova A", tipos, materias);


        String fenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");


        DadosQuestao dados = new DadosQuestao("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""", new String[] {"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"}, 'C', tipos.getFirst(), materias.getFirst());

        questaoService.cadastrarQuestao(prova.getId(), dados, fenInicial);



        prova = provaService.cadastrarProva("Prova teste", tipos, materias);

        List<DadosQuestao> questoesDaProvaTeste =List.of(
                new DadosQuestao("Questão 1 — A", new String[]{"A) a", "B) b", "C) c", "D) d", "E) e"}, 'A', tipos.getFirst(), materias.getLast()),
                new DadosQuestao("Questão 2 — B", new String[]{"Verdadeiro", "Falso"}, 'V', tipos.getLast(), materias.getLast()),
                new DadosQuestao("Questão 3 — C", new String[]{"Verdadeiro", "Falso"}, 'F', tipos.getLast(), materias.getLast())
        );



        fenInicial = null;
        for (var q : questoesDaProvaTeste) {
            questaoService.cadastrarQuestao(prova.getId(), q, fenInicial);
        }
    }

}
