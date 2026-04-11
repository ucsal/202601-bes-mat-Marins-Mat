package br.com.ucsal.olimpiadas;


import br.com.ucsal.olimpiadas.UI.*;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;
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

    private static final ProvaRepository provaRepository = new ProvaRepository();
    private static final ProvaService provaService = new ProvaService(provaRepository);
    private static final ProvaUI provaUI = new ProvaUI(provaService, in);


    private static final QuestaoRepository questaoRepository = new QuestaoRepository();
    private static final QuestaoService questaoService = new QuestaoService(questaoRepository, provaRepository);
    private static final QuestaoUI questaoUI = new QuestaoUI(questaoService, provaUI, in);

    private static final TentativaRepository tentativaRepository = new TentativaRepository();
    private static final TentativaService tentativaService = new TentativaService(tentativaRepository);
    private static final TentativaUI tentativaUI = new TentativaUI(tentativaService, in);


    private static final AplicadorProva aplicadorProva = new AplicadorProva(provaUI, participanteUI, questaoUI, tentativaService);
    private static final Menu menu = new Menu(participanteUI, provaUI, questaoUI, aplicadorProva, tentativaUI);

    static void main(String[] args) {
		DataSeeder.seed(participanteService, provaService, questaoService);

		while (true) {

            menu.exibirMenu();

            List<ItemMenu> itens = menu.getItens();

            Map<Long, Executavel> executavelMap = new HashMap<>();

            for (var i : itens) {
                executavelMap.put(i.getId(), i.getExecutavel());
            }

            var opcaoId = Long.parseLong(in.nextLine());

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
}