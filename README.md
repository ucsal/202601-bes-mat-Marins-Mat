No primeiro commit me foquei em tentar implementar o principio de Single Responsability, retirando os métodos da classe App e criando classes adjacentes, como service e repositories para gerir, respectivamente as regras de negocio e os dados das classes.

No segundo commit continuei tentando aplicar o SRP na aplicação, retirando o método ImprimirTabuleiroFen da classe App e criando uma classe QuestãoXadrez que herda de Questão (Questão ainda é uma classe normal, desrespeitando o Dependency Inversion Principle - DIP, mas que pretendo corrigir nos próximos commits). Além disso foi aplicado o principio de Open-Closed Principle no Menu, fazendo com que para um novo item aparecer no menu é apenas necessário registra-lo no construtor do Menu, o OCP foi implementado fazendo com que as classes UI implementassem uma interface Executavel, o que ficou sem nexo, mas pretendo corrigir isso nos próximos commits.


No terceiro Commit eu criei uma classe DataSeeder que faz a inicianlização dos objetos, para retirar essa responsabilidade do App e a criação da classe ChessboardRenderer, que é responsavel por criar o Tabuleiro

No quarto Commit as principais alterações foram, a criação de uma Factory de Questão, que gerencia a criação e quando necessário cria a QuestãoXadrez, unica questão que tem um comportamento diferente. a alteração de materia e TipoQuestão para Enuns.
