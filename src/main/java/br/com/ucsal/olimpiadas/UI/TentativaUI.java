package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.service.TentativaService;

import java.util.Scanner;

public class TentativaUI implements Executavel{

    private TentativaService tentativaService;
    private Scanner in;
    public TentativaUI(TentativaService tentativaService, Scanner in) {
        this.tentativaService = tentativaService;
        this.in = in;
    }

    public void executar() {

        if (!verificarExistenciaDeTentativas())
            return;

        System.out.println("\n--- Tentativas ---");

        for (var t : tentativaService.getTentativas()) {
            System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
                    t.getProvaId(), t.calcularNota(), t.getRespostas().size());
        }

    }

    public boolean verificarExistenciaDeTentativas(){
        if (tentativaService.isEmpty()){
            System.out.println("Não existem tentativas");
            return false;
        }
        return true;
    }

}
