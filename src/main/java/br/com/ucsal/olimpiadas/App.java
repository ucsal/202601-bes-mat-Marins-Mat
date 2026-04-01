package br.com.ucsal.olimpiadas;


import br.com.ucsal.olimpiadas.model.*;
import br.com.ucsal.olimpiadas.repositories.*;
import br.com.ucsal.olimpiadas.service.*;
import br.com.uscal.olimpiadas.menu.Menu;

import java.util.Scanner;

public class App {

    private static final Menu menu = new Menu();

    private static final ParticipanteRepository participanteRepository = new ParticipanteRepository();
    private static final ParticipanteService participanteService = new ParticipanteService(participanteRepository);

    private static final ProvaRepository provaRepository = new ProvaRepository();
    private static final ProvaService provaService = new ProvaService(provaRepository);

    private static final QuestaoRepository questaoRepository = new QuestaoRepository();
    private static final QuestaoService questaoService = new QuestaoService(questaoRepository, provaRepository);

    private static final TentativaRepository tentativaRepository = new TentativaRepository();
    private static final TentativaService tentativaService = new TentativaService(tentativaRepository);


	private static final Scanner in = new Scanner(System.in);

	static void main(String[] args) {
		seed();

		while (true) {
            menu.exibirMenu();

            switch (in.nextLine()) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> {
                    System.out.println("tchau");
                    return;
                }
                default -> System.out.println("opção inválida");
            }
		}
	}

    static void cadastrarParticipante() {

        var nome = lerNome();
        var email = lerEmail();

        try {
            participanteService.cadastrarParticipante(nome, email);
            System.out.println("Participante cadastrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

    static void cadastrarProva() {

        var titulo = lerTitulo();

        try {
            provaService.cadastrarProva(titulo);
            System.out.println("Prova cadastrada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

    static void cadastrarQuestao() {

        if (!verificarExistenciaDeProvas()) {
            return;
        }

        apresentarProvas();

        var provaId = escolherProva();

        if (provaId == null)
            return;

		var enunciado = lerEnunciado();

		var alternativas = lerAlternativas();

        char altCorreta = lerAlternativaCorreta();

        Character correta = validarAlternativa(altCorreta);

        if (correta == null)
            return;

        questaoService.cadastrarQuestao(provaId, enunciado, alternativas, correta);
        System.out.println("Questão cadastrada: " + questaoService.findByProvaId(provaId).getLast().getQuestaoNaProva() + " (na prova " + provaId + ")");
	}

    static void aplicarProva() {

        if (!verificarExistenciaDeParticipantes()) {
            return;
        }
        if (!verificarExistenciaDeProvas()) {
            return;
        }

        apresentarParticipante();

		var participanteId = escolherParticipante();
		if (participanteId == null)
			return;

        apresentarProvas();

		var provaId = escolherProva();
		if (provaId == null)
			return;


        var questoesDaProva = questaoService.findByProvaId(provaId);

        if (questoesDaProva.isEmpty()) {
            System.out.println("Esta prova não possui questões cadastradas");
            return;
        }

        Long tentativaId = tentativaService.addTentativa(participanteId, provaId);

        System.out.println("\n--- Início da Prova ---");

        for (var questao : questoesDaProva){

            exibirQuestao(questao);

            Character marcada = lerResposta();

            marcada = validarResposta(marcada);

            tentativaService.addResposta(tentativaId, questao, marcada);
        }

        int nota = tentativaService.calcularNota(tentativaId);
        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativaService.getRespostas(tentativaId).size());

	}

	static void listarTentativas() {

        if (!verificarExistenciaDeTentativas())
            return;

        System.out.println("\n--- Tentativas ---");

        for (var t : tentativaService.getTentativas()) {
            System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
                    t.getProvaId(), t.calcularNota(), t.getRespostas().size());
        }

	}

	static void imprimirTabuleiroFen(String fen) {

		String parteTabuleiro = fen.split(" ")[0];
		String[] ranks = parteTabuleiro.split("/");

		System.out.println();
		System.out.println("    a b c d e f g h");
		System.out.println("   -----------------");

		for (int r = 0; r < 8; r++) {

			String rank = ranks[r];
			System.out.print((8 - r) + " | ");

			for (char c : rank.toCharArray()) {

				if (Character.isDigit(c)) {
					int vazios = c - '0';
					for (int i = 0; i < vazios; i++) {
						System.out.print(". ");
					}
				} else {
					System.out.print(c + " ");
				}
			}

			System.out.println("| " + (8 - r));
		}

		System.out.println("   -----------------");
		System.out.println("    a b c d e f g h");
		System.out.println();
	}

    static void seed() {

        participanteService.cadastrarParticipante("Mateus", "");

		provaService.cadastrarProva("Olimpíada 2026 • Nível 1 • Prova A");

        provaService.cadastrarProva("Prova teste");

        Prova primeiraProva = provaService.getProva(1L);

		String enunciado = ("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""");

		String FenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

        String[] Alternativas = new String[] { "A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#" };

		char AlternativaCorreta = ('C');

		questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta);

        primeiraProva = provaService.getProva(2L);

        enunciado = ("""
				Questão 1 — A""");

        FenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('a');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta);

        enunciado = ("""
				Questão 2 — B""");

        FenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('b');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta);

        enunciado = ("""
				Questão 2 — B""");

        FenInicial = ("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

        Alternativas = new String[] { "A) a", "B) b", "C) c", "D) d", "E) e" };

        AlternativaCorreta = ('c');

        questaoService.cadastrarQuestao(primeiraProva.getId(), enunciado, Alternativas, AlternativaCorreta);
    }



    static String lerNome() {
        System.out.print("Nome: ");
        return in.nextLine();
    }

    static String lerEmail() {
        System.out.print("Email (opcional): ");
        return in.nextLine();
    }

    static Long escolherParticipante() {

        System.out.print("Escolha o id do participante: ");
        return Long.parseLong(in.nextLine());

    }

    static void apresentarParticipante() {
        System.out.println("\nParticipantes:");
        for (var p : participanteService.getParticipantes()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getNome());
        }
    }

    static boolean verificarExistenciaDeParticipantes() {
        if (participanteService.isEmpty()) {
            System.out.println("Cadastre participantes primeiro");
            return false;
        }
        return true;
    }

    static String lerTitulo() {

        System.out.print("Título da prova: ");
        return in.nextLine();

    }

    static void apresentarProvas() {
        System.out.println("\nProvas:");
        for (var p : provaService.getProvas()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        }
    }

    static Long escolherProva() {

        System.out.println("Escolha o id da prova");
        return Long.parseLong(in.nextLine());

    }

    static boolean verificarExistenciaDeProvas() {
        if (provaService.isEmpty()){
            System.out.println("Cadastre provas primeiro");
            return false;
        }
        return true;
    }

    static String lerEnunciado() {
        System.out.println("Enunciado:");
        return in.nextLine();
    }

    static String[] lerAlternativas() {
        var alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }
        return alternativas;
    }

    static char lerAlternativaCorreta(){
        System.out.print("Alternativa correta (A–E): ");
        return in.nextLine().trim().charAt(0);
    }

    static Character validarAlternativa(char c) {
        try {
            return questaoService.validarAlternativa(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    static void exibirQuestao(Questao q) {
        System.out.println("\nQuestão #" + q.getQuestaoNaProva());
        System.out.println(q.getEnunciado());

        System.out.println("Posição inicial:");
        //imprimirTabuleiroFen(q.getFenInicial());

        for (var alt : q.getAlternativas()) {
            System.out.println(alt);
        }
    }

    static char lerResposta() {
        System.out.print("Sua resposta (A–E): ");
        return in.nextLine().trim().charAt(0);
    }

    static char validarResposta(char c) {
        try {
            return questaoService.validarAlternativa(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("resposta inválida (marcando como errada)");
            return 'X';
        }
    }

    static boolean verificarExistenciaDeTentativas(){
        if (tentativaService.isEmpty()){
            System.out.println("Não existem tentativas");
            return false;
        }
        return true;
    }
}