package br.com.ucsal.olimpiadas.UI;

import br.com.ucsal.olimpiadas.enums.Materia;
import br.com.ucsal.olimpiadas.enums.TipoQuestao;
import br.com.ucsal.olimpiadas.service.ProvaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProvaUI implements Executavel{

    private ProvaService provaService;
    private Scanner in;

    public ProvaUI(ProvaService provaService, Scanner in) {
        this.provaService = provaService;
        this.in = in;
    }

    public void executar() {

        var titulo = lerTitulo();

        var tiposDeQuestao = lerTipos();

        var materiasDaProva = lerMaterias();
        try {
            provaService.cadastrarProva(titulo, tiposDeQuestao, materiasDaProva);
            System.out.println("Prova cadastrada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    String lerTitulo() {

        System.out.print("Título da prova: ");
        return in.nextLine();

    }

    List<TipoQuestao> lerTipos() {
        List<TipoQuestao> tiposEscolhidos = new ArrayList<>();

        while (true){
            System.out.println("Escolha os tipos de questao da prova: ");


            for (var tipo : TipoQuestao.values()){
                System.out.printf("%d) %s%n", tipo.getId(), tipo.getDescricao());
            }
            System.out.println("0) Finalizar");

            Long opcaoEscolhida;
            try {
                opcaoEscolhida = Long.parseLong(in.nextLine());
            } catch (Exception e) {
                opcaoEscolhida=-1L;
            }

            if (opcaoEscolhida==0) {
                if (tiposEscolhidos.isEmpty()) {
                    System.out.println("Escolha ao menos um tipo");
                    continue;
                }
                return tiposEscolhidos;
            }

            TipoQuestao escolhido = TipoQuestao.findById(opcaoEscolhida);
            if (escolhido == null) {
                System.out.println("Opção inválida");
            } else if (tiposEscolhidos.contains(escolhido)) {
                System.out.println("Opção já selecionada");
            }else {
                tiposEscolhidos.add(escolhido);
            }
        }
    }

    List<Materia> lerMaterias() {
        List<Materia> materiasEscolhidas = new ArrayList<>();

        while (true){
            System.out.println("Escolha as materias da prova: ");

            for (var materia : Materia.values()){
                System.out.printf("%d) %s%n", materia.getId(), materia.getNome());
            }
            System.out.println("0) Finalizar");

            Long opcaoEscolhida;
            try {
                opcaoEscolhida = Long.parseLong(in.nextLine());
            } catch (Exception e) {
                opcaoEscolhida=-1L;
            }

            if (opcaoEscolhida==0) {
                return materiasEscolhidas;
            }

            Materia escolhido = Materia.findById(opcaoEscolhida);
            if (escolhido == null ) {
                System.out.println("Opção inválida");
            } else if (materiasEscolhidas.contains(escolhido)) {
                System.out.println("Opção já selecionada");
            } else {
                materiasEscolhidas.add(escolhido);
            }
        }
    }



    void apresentarTiposDaProva(Long id) {

        List<TipoQuestao> tipos = provaService.findProvaById(id).getTiposDeQuestao();

        for (var t : tipos) {
            System.out.printf("%d) %s%n", t.getId(), t.getDescricao());
        }
    }

    void apresentarMateriasDaProva(Long id) {

        List<Materia> materias = provaService.findProvaById(id).getMaterias();

        for (var m : materias) {
            System.out.printf("%d) %s%n", m.getId(), m.getNome());
        }
    }


    void apresentarProvas() {
        System.out.println("\nProvas:");
        for (var p : provaService.getProvas()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        }
    }

    Long escolherProva() {

        System.out.println("Escolha o id da prova");
        try {
            return Long.parseLong(in.nextLine());
        } catch (Exception e) {
            return null;
        }


    }

    public boolean verificarExistenciaDeProvas() {
        if (provaService.isEmpty()){
            System.out.println("Cadastre provas primeiro");
            return false;
        }
        return true;
    }

    public boolean verificarExistenciaEscolhida(Long id) {
            if (provaService.anyMatch(id)) {
                return true;
            } else {
                System.out.println("Prova não encontrada");
                return false;
            }
    }
}
