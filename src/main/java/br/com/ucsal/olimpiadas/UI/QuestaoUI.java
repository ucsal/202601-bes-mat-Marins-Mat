package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.ChessboardRenderer;
import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;
import br.com.ucsal.olimpiadas.model.questoes.DadosQuestao;
import br.com.ucsal.olimpiadas.model.questoes.Questao;

import br.com.ucsal.olimpiadas.model.questoes.QuestaoXadrez;
import br.com.ucsal.olimpiadas.service.QuestaoService;

import java.util.List;
import java.util.Scanner;

public class QuestaoUI implements Executavel{

    private QuestaoService questaoService;
    private ProvaUI provaUI;
    private Scanner in;

    public QuestaoUI(QuestaoService questaoService, ProvaUI provaUI, Scanner in) {
        this.questaoService = questaoService;
        this.provaUI = provaUI;
        this.in = in;
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

       cadastrarQuestao(provaId, tipoId, materiaId);



        System.out.println("Questão cadastrada: " + questaoService.findByProvaId(provaId).getLast().getQuestaoNaProva() + " (na prova " + provaId + ")");
    }

    private void cadastrarQuestao(Long provaId, Long tipoId, Long materiaId){

        var enunciado = lerEnunciado();

        String fenInicial = null;

        var materia = Materia.findById(materiaId);

        if (materia==Materia.XADREZ) {
            fenInicial = lerFenInicial();
        }

        var tipo = TipoQuestao.findById(tipoId);

        var alternativas = lerAlternativas(tipo);

        char altCorreta;

        Character correta = null;
        boolean alternativaNaoAceita = true;

        while (alternativaNaoAceita) {

            altCorreta = lerAlternativaCorreta(tipo);
            correta = validarAlternativa(tipo, altCorreta);

            if (correta != null)
                alternativaNaoAceita=false;
        }


        DadosQuestao dados = new DadosQuestao(enunciado, alternativas, correta, tipo, materia);

        questaoService.cadastrarQuestao(provaId, dados, fenInicial);
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

        if (tipo==TipoQuestao.MULTIPLA_ESCOLHA) {
            alternativas = new String [5];
            for (int i = 0; i < 5; i++) {
                char letra = (char) ('A' + i);
                System.out.print("Alternativa " + letra + ": ");
                alternativas[i] = letra + ") " + in.nextLine();
            }

        } else if (tipo==TipoQuestao.VERDADEIRO_OU_FALSO) {
            alternativas = new String[2];
            alternativas[0] = "Verdadeiro";
            alternativas[1] = "Falso";
        }
        return alternativas;
    }

    private char lerAlternativaCorreta(TipoQuestao tipo){

        if (tipo==TipoQuestao.MULTIPLA_ESCOLHA) {
            System.out.print("Alternativa correta (A–E): ");
            return in.nextLine().trim().charAt(0);
        }

        if (tipo==TipoQuestao.VERDADEIRO_OU_FALSO) {
            System.out.print("Alternativa correta (V (Verdadeiro) | F (Falso): ");
            return in.nextLine().trim().charAt(0);
        } else {
            System.out.println("Tipo ainda não implementado, implementando funcionalidade padrão de multipla escolha");
            System.out.print("Alternativa correta (A–E): ");
            return in.nextLine().trim().charAt(0);
        }
    }

    private Character validarAlternativa(TipoQuestao tipo, char c) {
        try {
            return tipo.normalizar(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String lerFenInicial() {
        System.out.println("Digite o fen inicial: ");
        return in.nextLine();
    }

    public void exibirQuestao(Questao questao) {
        System.out.println("\nQuestão #" + questao.getQuestaoNaProva());
        System.out.println(questao.getEnunciado());

        if (questao.getMateria()==Materia.XADREZ){
            System.out.println("Posição inicial:");
            questao.exibirMaisDetalhes();
        }

        for (var alt : questao.getAlternativas()) {
            System.out.println(alt);
        }
    }

    public char lerResposta(TipoQuestao tipo) {

        if (tipo==TipoQuestao.MULTIPLA_ESCOLHA) {
            System.out.print("Sua resposta (A–E): ");
            return in.nextLine().trim().charAt(0);
        } else  {
            System.out.print("Sua resposta (V | F): ");
            return in.nextLine().trim().charAt(0);
        }

    }

    public char validarResposta(TipoQuestao tipo, char c) {
        try {
            return tipo.normalizar(c);
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
