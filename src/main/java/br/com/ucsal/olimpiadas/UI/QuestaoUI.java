package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.model.questoes.Questao;
import br.com.ucsal.olimpiadas.model.TipoQuestao;
import br.com.ucsal.olimpiadas.repositories.MateriaRepository;
import br.com.ucsal.olimpiadas.repositories.TipoQuestaoRepository;
import br.com.ucsal.olimpiadas.service.QuestaoService;

import java.util.List;
import java.util.Scanner;

public class QuestaoUI implements Executavel{

    private QuestaoService questaoService;
    private ProvaUI provaUI;
    private Scanner in;
    private TipoQuestaoRepository tipoQuestaoRepository;
    private MateriaRepository materiaRepository;

    public QuestaoUI(QuestaoService questaoService, ProvaUI provaUI, Scanner in, TipoQuestaoRepository tipoQuestaoRepository, MateriaRepository materiaRepository) {
        this.questaoService = questaoService;
        this.provaUI = provaUI;
        this.in = in;
        this.tipoQuestaoRepository = tipoQuestaoRepository;
        this.materiaRepository = materiaRepository;
    }

    public void executar() {

        provaUI.apresentarProvas();

        var provaId = provaUI.escolherProva();

        if (provaId == null || !provaUI.verificarExistenciaEscolhida(provaId))
            return;


        var tipoId = lerTipo(provaId);

        if (tipoId==null)
            return;

       var materiaId = lerMateria(provaId);

       if (materiaId == null)
                return;

       if (materiaRepository.findMateriaById(materiaId).getId()==1) {
            cadastrarQuestaoXadrez(provaId, tipoId, materiaId);
       } else {
           cadastrarQuestao(provaId, tipoId, materiaId);
       }


        System.out.println("Questão cadastrada: " + questaoService.findByProvaId(provaId).getLast().getQuestaoNaProva() + " (na prova " + provaId + ")");
    }

    private void cadastrarQuestao(Long provaId, Long tipoId, Long materiaId){

        var enunciado = lerEnunciado();

        var materia = materiaRepository.findMateriaById(materiaId);

        var tipo = tipoQuestaoRepository.findById(tipoId);

        var alternativas = lerAlternativas(tipo);

        char altCorreta = lerAlternativaCorreta(tipo);

        Character correta = validarAlternativa(altCorreta);

        if (correta == null)
            return;

        questaoService.cadastrarQuestao(provaId, enunciado, alternativas, correta, tipo, materia);
    }

    private void cadastrarQuestaoXadrez(Long provaId, Long tipoId, Long materiaId) {

        var fenInicial = lerFenInicial();

        var enunciado = lerEnunciado();

        var materia = materiaRepository.findMateriaById(materiaId);

        var tipo = tipoQuestaoRepository.findById(tipoId);

        var alternativas = lerAlternativas(tipo);

        char altCorreta = lerAlternativaCorreta(tipo);

        Character correta = validarAlternativa(altCorreta);

        if (correta == null)
            return;

        questaoService.cadastrarQuestao(provaId, fenInicial, enunciado, alternativas, correta, tipo, materia);

    }

    private Long lerTipo(Long provaId) {

        System.out.println("Selecione o tipo da questão: ");
        provaUI.apresentarTiposDaProva(provaId);
        try {
            return Long.parseLong(in.nextLine());
        } catch (Exception e) {
            return null;
        }

    }

    private Long lerMateria(Long provaId) {
        System.out.println("Selecione a materia da questão: ");
        provaUI.apresentarMateriasDaProva(provaId);
        try {
            return Long.parseLong(in.nextLine());
        } catch (Exception e) {
            return null;
        }
    }

    private String lerEnunciado() {
        System.out.println("Enunciado:");
        return in.nextLine();
    }

    private String[] lerAlternativas(TipoQuestao tipo) {
        String [] alternativas = null;

        if (tipo.getId()==1) {
            alternativas = new String [5];
            for (int i = 0; i < 5; i++) {
                char letra = (char) ('A' + i);
                System.out.print("Alternativa " + letra + ": ");
                alternativas[i] = letra + ") " + in.nextLine();
            }

        } else if (tipo.getId()==2) {
            alternativas = new String[2];
            alternativas[0] = "Verdadeiro";
            alternativas[1] = "Falso";
        }
        return alternativas;
    }



    private char lerAlternativaCorreta(TipoQuestao tipo){

        if (tipo.getId()==1) {
            System.out.print("Alternativa correta (A–E): ");
            return in.nextLine().trim().charAt(0);
        } else if (tipo.getId()==2) {
            System.out.print("Alternativa correta (A (Verdadeiro) | B (Falso): ");
            return in.nextLine().trim().charAt(0);
        } else {
            System.out.println("Tipo ainda não implementado, implementando funcionalidade padrão de multipla escolha");
            System.out.print("Alternativa correta (A–E): ");
            return in.nextLine().trim().charAt(0);
        }
    }

    private Character validarAlternativa(char c) {
        try {
            return questaoService.validarAlternativa(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String lerFenInicial() {
        System.out.println("Digite o fen inicial: ");
        return in.nextLine();
    }

    public void exibirQuestao(Questao q) {
        System.out.println("\nQuestão #" + q.getQuestaoNaProva());
        System.out.println(q.getEnunciado());

        System.out.println("Posição inicial:");
        //imprimirTabuleiroFen(q.getFenInicial());

        for (var alt : q.getAlternativas()) {
            System.out.println(alt);
        }
    }

    public char lerResposta() {
        System.out.print("Sua resposta (A–E): ");
        return in.nextLine().trim().charAt(0);
    }

    public char validarResposta(char c) {
        try {
            return questaoService.validarAlternativa(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("resposta inválida (marcando como errada)");
            return 'X';
        }
    }


    public List<Questao> procurarQuestoes(Long provaId) {
        return questaoService.findByProvaId(provaId);
    }

    public boolean validarExistenciaDeQuestoes(List<Questao> questoes) {
        if (questoes.isEmpty()){
            System.out.println("Esta prova não possui questões cadastradas");
            return false;
        }
        return true;
    }
}
