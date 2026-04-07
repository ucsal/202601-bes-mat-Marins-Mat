package br.com.ucsal.olimpiadas;


import br.com.ucsal.olimpiadas.UI.*;
import br.com.ucsal.olimpiadas.model.*;
import br.com.ucsal.olimpiadas.repositories.*;
import br.com.ucsal.olimpiadas.service.*;
import br.com.uscal.olimpiadas.menu.ItemMenu;
import br.com.uscal.olimpiadas.menu.Menu;

import java.util.*;

public class App {

    private static final Scanner in = new Scanner(System.in);

    private static final ParticipanteRepository participanteRepository = new ParticipanteRepository();
    private static final ParticipanteService participanteService = new ParticipanteService(participanteRepository);
    private static final ParticipanteUI participanteUI = new ParticipanteUI(participanteService, in);

    private static final TipoQuestaoRepository tipoQuestaoRepository = new TipoQuestaoRepository();
    private static final MateriaRepository materiaRepository = new MateriaRepository();

    private static final ProvaRepository provaRepository = new ProvaRepository();
    private static final ProvaService provaService = new ProvaService(provaRepository);
    private static final ProvaUI provaUI = new ProvaUI(provaService, tipoQuestaoRepository, materiaRepository, in);


    private static final QuestaoRepository questaoRepository = new QuestaoRepository();
    private static final QuestaoService questaoService = new QuestaoService(questaoRepository, provaRepository);
    private static final QuestaoUI questaoUI = new QuestaoUI(questaoService, provaUI, in,tipoQuestaoRepository, materiaRepository);

    private static final TentativaRepository tentativaRepository = new TentativaRepository();
    private static final TentativaService tentativaService = new TentativaService(tentativaRepository);
    private static final TentativaUI tentativaUI = new TentativaUI(tentativaService, in);


    private static final AplicadorProva aplicadorProva = new AplicadorProva(provaUI, participanteUI, questaoUI, tentativaService);
    private static final Menu menu = new Menu(participanteUI, provaUI, questaoUI, aplicadorProva, tentativaUI);

    void main(String[] args) {
		seed();

		while (true) {

            menu.exibirMenu();

            List<ItemMenu> itens = menu.getItens();

            Map<Long, Executavel> executavelMap = new HashMap<>();

            for (var i : itens) {
                executavelMap.put(i.getId(), i.getExecutavel());
            }

            var opcaoId = Long.parseLong(in.nextLine());

            participanteUI.verificarExistenciaDeParticipantes();

            if (opcaoId == 0) {
                System.out.println("tchau");
                return;
            } else if (opcaoId>itens.size() || opcaoId<0) {
                System.out.println("opção inválida");
            } else {
                executavelMap.get(opcaoId).executar();
            }
		}
	}

    void seed() {

        participanteService.cadastrarParticipante("Mateus", "");

        List<TipoQuestao> tipos = tipoQuestaoRepository.getTiposDeQuestao();

        List<Materia> materias = materiaRepository.getMaterias();

		provaService.cadastrarProva("Olimpíada 2026 • Nível 1 • Prova A", tipos, Arrays.asList(materias.getFirst()));

        provaService.cadastrarProva("Prova teste", tipos, Arrays.asList(materias.getLast()));

        Prova primeiraProva = provaService.findProvaById(1L);

		String enunciado = ("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""");

		String FenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

        String[] Alternativas = new String[] { "A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#" };

		char AlternativaCorreta = ('C');

		questaoService.cadastrarQuestao(primeiraProva.getId(), FenInicial, enunciado, Alternativas, AlternativaCorreta, tipos.getFirst(), materias.getFirst());

        primeiraProva = provaService.findProvaById(2L);

        enunciado = ("""
				Questão 1 — A""");



        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('a');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta, tipos.getLast(), materias.getLast());

        enunciado = ("""
				Questão 2 — B""");



        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('b');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta, tipos.getLast(), materias.getLast());
        enunciado = ("""
				Questão 2 — B""");



        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('c');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta, tipos.getLast(), materias.getLast());
    }


}