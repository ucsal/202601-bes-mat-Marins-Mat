package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.service.TentativaService;

import java.util.List;

public class AplicadorProva implements Executavel{

    private ProvaUI provaUI;
    private ParticipanteUI participanteUI;
    private QuestaoUI questaoUI;
    private TentativaService tentativaService;

    public AplicadorProva(ProvaUI provaUI, ParticipanteUI participanteUI, QuestaoUI questaoUI, TentativaService tentativaService) {
        this.provaUI = provaUI;
        this.participanteUI = participanteUI;
        this.questaoUI = questaoUI;
        this.tentativaService = tentativaService;
    }

    public void executar() {

        if (!participanteUI.verificarExistenciaDeParticipantes()) {
            return;
        }
        if (!provaUI.verificarExistenciaDeProvas()) {
            return;
        }

        participanteUI.apresentarParticipante();

        var participanteId = participanteUI.escolherParticipante();
        if (participanteId == null || !participanteUI.verificarExistenciaEscolhido(participanteId))
            return;

        provaUI.apresentarProvas();

        var provaId = provaUI.escolherProva();
        if (provaId == null || !provaUI.verificarExistenciaEscolhida(provaId))
            return;


        var questoesDaProva = questaoUI.procurarQuestoes(provaId);

        if (!questaoUI.validarExistenciaDeQuestoes(questoesDaProva)) {
            return;
        }

        aplicarProva(participanteId, provaId, questoesDaProva);
    }

    private void aplicarProva(Long participanteId, Long provaId, List<Questao> questoesDaProva) {

        Long tentativaId = tentativaService.addTentativa(participanteId, provaId);

        System.out.println("\n--- Início da Prova ---");

        for (var questao : questoesDaProva){

            questaoUI.exibirQuestao(questao);

            Character marcada = questaoUI.lerResposta();

            marcada = questaoUI.validarResposta(marcada);

            tentativaService.addResposta(tentativaId, questao, marcada);
        }

        int nota = tentativaService.calcularNota(tentativaId);
        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativaService.getRespostas(tentativaId).size());

    }
}
