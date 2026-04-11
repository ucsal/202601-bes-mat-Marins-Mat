package br.com.uscal.olimpiadas.menu;

import br.com.ucsal.olimpiadas.UI.Executavel;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<ItemMenu> itensMenu = new ArrayList<>();
    private Long proximoId = 1L;

    public Menu(Executavel cadastrarParticipante, Executavel cadastrarProva, Executavel cadastrarQuestao, Executavel aplicarProva, Executavel listarTentativas) {
        itensMenu.add(new ItemMenu(proximoId++, "Cadastrar participante", cadastrarParticipante));
        itensMenu.add(new ItemMenu(proximoId++, "Cadastrar prova", cadastrarProva));
        itensMenu.add(new ItemMenu(proximoId++, "Cadastrar questão (A–E) em uma prova", cadastrarQuestao));
        itensMenu.add(new ItemMenu(proximoId++, "Aplicar prova (selecionar participante + prova)", aplicarProva));
        itensMenu.add(new ItemMenu(proximoId++, "Listar tentativas (resumo)", listarTentativas));
    }

    public void exibirMenu(){
        System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
        for (var i : itensMenu) {
            System.out.printf("%d) %s%n", i.getId(), i.getDescricao());
        }
        System.out.println("0) Sair");
        System.out.print("> ");
    }

    public List<ItemMenu> getItens(){
        return itensMenu;
    }
}
