package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.service.ParticipanteService;

import java.util.Scanner;

public class ParticipanteUI implements Executavel{

    private ParticipanteService participanteService;
    private Scanner in;

    public ParticipanteUI(ParticipanteService participanteService, Scanner in) {
        this.participanteService = participanteService;
        this.in = in;
    }

    public void executar() {

        var nome = lerNome();
        var email = lerEmail();

        try {
            participanteService.cadastrarParticipante(nome, email);
            System.out.println("Participante cadastrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Long escolherParticipante() {

        System.out.print("Escolha o id do participante: ");
        try {
            return Long.parseLong(in.nextLine());
        } catch (Exception e) {
            return null;
        }


    }

    public void apresentarParticipante() {
        System.out.println("\nParticipantes:");
        for (var p : participanteService.getParticipantes()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getNome());
        }
    }

    public boolean verificarExistenciaDeParticipantes() {
        if (participanteService.isEmpty()) {
            System.out.println("Cadastre participantes primeiro");
            return false;
        }
        return true;
    }

    public boolean verificarExistenciaEscolhido(Long id) {
        if (participanteService.anyMatch(id)){
            return true;
        } else {
            System.out.println("Participante não encontrado");
            return false;
        }
    }

    private String lerNome() {
        System.out.print("Nome: ");
        return in.nextLine();
    }

    private String lerEmail() {
        System.out.print("Email (opcional): ");
        return in.nextLine();
    }
}
