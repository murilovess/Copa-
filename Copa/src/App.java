import java.util.Scanner;
 
public class App {
 
    static String[] selecao = new String[5];
    static int[] pontuacao = new int[5];
    static String[][] jogadores = new String[5][11];
    static int[][] gol = new int[5][11];
 
    static String verNomeSeleção(int numInscricao) {
        if (numInscricao < 0 || numInscricao >= 5) {
            return "Invalido";
        }
        return selecao[numInscricao];
    }
 
    static String verNomeJogador(int numInscricaoJogador, int numInscricaoSeleção) {
        if (numInscricaoSeleção < 0 || numInscricaoSeleção >= 5) {
            return "Invalido";
        }
        if (numInscricaoJogador < 0 || numInscricaoJogador >= 11) {
            return "Invalido";
        }
        return jogadores[numInscricaoSeleção][numInscricaoJogador];
    }
 
    static int verNumInscricaoSeleção(String nomeSeleção) {
        for (int i = 0; i < 5; i++) {
            if (selecao[i].equalsIgnoreCase(nomeSeleção)) {
                return i;
            }
        }
        return -1;
    }
 
    static int verNumInscricaoJogador(String nomeJogador, String nomeSeleção) {
        int idxSel = verNumInscricaoSeleção(nomeSeleção);
        if (idxSel == -1) {
            return -1;
        }
        for (int k = 0; k < 11; k++) {
            if (jogadores[idxSel][k].equalsIgnoreCase(nomeJogador)) {
                return k;
            }
        }
        return -1;
    }
 
    static int verGolsSeleção(String nomeSeleção) {
        int idxSel = verNumInscricaoSeleção(nomeSeleção);
        if (idxSel == -1) {
            return -1;
        }
        int total = 0;
        for (int k = 0; k < 11; k++) {
            total += gol[idxSel][k];
        }
        return total;
    }
 
    static int verGolsJogador(String nomeJogador, String nomeSeleção) {
        int idxSel = verNumInscricaoSeleção(nomeSeleção);
        if (idxSel == -1) {
            return -1;
        }
        int idxJog = verNumInscricaoJogador(nomeJogador, nomeSeleção);
        if (idxJog == -1) {
            return -1;
        }
        return gol[idxSel][idxJog];
    }
 
    static void cadastrarSeleções(Scanner ler) {
        for (int i = 0; i < 5; i++) {
            System.out.println("Digite o nome da seleção " + (i + 1) + ": ");
            selecao[i] = ler.nextLine();
            pontuacao[i] = 0;
        }
    }
 
    static boolean cadastrarJogadores(int numInscricaoSeleção, Scanner ler) {
        if (numInscricaoSeleção < 0 || numInscricaoSeleção >= 5) {
            return false;
        }
        System.out.println("\nSeleção: " + selecao[numInscricaoSeleção]);
        for (int j = 0; j < 11; j++) {
            System.out.println("Digite o nome do jogador " + (j + 1) + ": ");
            jogadores[numInscricaoSeleção][j] = ler.nextLine();
            gol[numInscricaoSeleção][j] = 0;
        }
        return true;
    }
 
    static void consultarVencedor() {
        int partidas = 4;
 
        int idxVencedor = 0;
        for (int i = 1; i < 5; i++) {
            boolean troca = false;
 
            if (pontuacao[i] > pontuacao[idxVencedor]) {
                troca = true;
            } else if (pontuacao[i] == pontuacao[idxVencedor]) {
                int golsI = verGolsSeleção(selecao[i]);
                int golsV = verGolsSeleção(selecao[idxVencedor]);
                if (golsI > golsV) {
                    troca = true;
                } else if (golsI == golsV) {
                
                    if (i < idxVencedor) {
                        troca = true;
                    }
                }
            }
 
            if (troca) {
                idxVencedor = i;
            }
        }
 
        System.out.println("\n=== SELEÇÃO VENCEDORA ===");
        System.out.println("Nome      : " + selecao[idxVencedor]);
        System.out.println("Pontuação : " + pontuacao[idxVencedor] + " pontos");
    }
 
    static void consultarArtilheiros() {
        double partidas = 4.0;
 
        int maiorGols = 0;
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 11; k++) {
                if (gol[i][k] > maiorGols) {
                    maiorGols = gol[i][k];
                }
            }
        }
 
        System.out.println("\n=== ARTILHEIRO(S) DO TORNEIO ===");
        if (maiorGols == 0) {
            System.out.println("Nenhum gol registrado.");
            return;
        }
 
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 11; k++) {
                if (gol[i][k] == maiorGols) {
                    double media = gol[i][k] / partidas;
                    System.out.printf("Jogador: %-20s | Seleção: %-15s | Média: %.2f gols/partida%n",
                            jogadores[i][k], selecao[i], media);
                }
            }
        }
    }
 
    static void consultarPercentualMaisDe5Gols() {
        int totalJogadores = 5 * 11;
        int contador = 0;
 
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 11; k++) {
                if (gol[i][k] > 5) {
                    contador++;
                }
            }
        }
 
        double percentual = (contador * 100.0) / totalJogadores;
        System.out.println("\n=== JOGADORES COM MAIS DE 5 GOLS ===");
        System.out.printf("%.2f%% dos jogadores (%d de %d)%n", percentual, contador, totalJogadores);
    }
 
    static void consultarPercentualComGolPorSeleção(Scanner ler) {
        System.out.print("\nDigite o nome da seleção: ");
        String nomeSel = ler.nextLine();
 
        int idxSel = verNumInscricaoSeleção(nomeSel);
        if (idxSel == -1) {
            System.out.println("Seleção não encontrada.");
            return;
        }
 
        int comGol = 0;
        for (int k = 0; k < 11; k++) {
            if (gol[idxSel][k] >= 1) {
                comGol++;
            }
        }
 
        double percentual = (comGol * 100.0) / 11;
        System.out.println("\n=== JOGADORES COM PELO MENOS 1 GOL - " + selecao[idxSel].toUpperCase() + " ===");
        System.out.printf("%.2f%% dos jogadores (%d de 11)%n", percentual, comGol);
    }
 
    static void listarDesempenhoSeleções() {
        int partidas = 4;
 
        System.out.println("\n=== DESEMPENHO DAS SELEÇÕES ===");
        for (int i = 0; i < 5; i++) {
            int totalGols = verGolsSeleção(selecao[i]);
            double media = totalGols / (double) partidas;
            System.out.printf("%-20s | Pontos: %2d | Média de gols/partida: %.2f%n",
                    selecao[i], pontuacao[i], media);
        }
    }
 
    static void listarDesempenhoJogadores() {
        System.out.println("\n=== DESEMPENHO DOS JOGADORES ===");
        for (int i = 0; i < 5; i++) {
            System.out.println("\n-- " + selecao[i] + " --");
            for (int k = 0; k < 11; k++) {
                System.out.printf("%-25s | Seleção: %-15s | Gols: %d%n",
                        jogadores[i][k], selecao[i], gol[i][k]);
            }
        }
    }
 
    static void registrarPartidas(Scanner ler) {
        System.out.println("\nA COPA COMEÇOU!");
 
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
 
                System.out.println("\n" + selecao[i] + " X " + selecao[j]);
 
                int golCasa;
                int golVisitante;
 
                do {
                    System.out.print("Digite a quantidade de gols da " + selecao[i] + ": ");
                    golCasa = ler.nextInt();
                } while (golCasa < 0);
                ler.nextLine();
 
                for (int g = 0; g < golCasa; g++) {
                    System.out.println("Quem marcou o gol " + (g + 1) + " da seleção " + selecao[i] + "?");
                    String nomeJogador = ler.nextLine();
 
                    if (nomeJogador.equalsIgnoreCase("CONTRA")) {
                        continue;
                    }
 
                    boolean encontrado = false;
                    for (int k = 0; k < 11; k++) {
                        if (jogadores[i][k].equalsIgnoreCase(nomeJogador)) {
                            gol[i][k]++;
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Jogador não encontrado. Gol não contabilizado.");
                    }
                }
 
                do {
                    System.out.print("Digite a quantidade de gols da " + selecao[j] + ": ");
                    golVisitante = ler.nextInt();
                } while (golVisitante < 0);
                ler.nextLine();
 
                for (int g = 0; g < golVisitante; g++) {
                    System.out.println("Quem marcou o gol " + (g + 1) + " da seleção " + selecao[j] + "?");
                    String nomeJogador = ler.nextLine();
 
                    if (nomeJogador.equalsIgnoreCase("CONTRA")) {
                        continue;
                    }
 
                    boolean encontrado = false;
                    for (int k = 0; k < 11; k++) {
                        if (jogadores[j][k].equalsIgnoreCase(nomeJogador)) {
                            gol[j][k]++;
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Jogador não encontrado. Gol não contabilizado.");
                    }
                }
 
                if (golCasa > golVisitante) {
                    pontuacao[i] += 3;
                } else if (golCasa < golVisitante) {
                    pontuacao[j] += 3;
                } else {
                    pontuacao[i] += 1;
                    pontuacao[j] += 1;
                }
            }
        }
    }
 
 
    public static void main(String[] args) throws Exception {
 
        Scanner ler = new Scanner(System.in);
 
        // Cadastro
        cadastrarSeleções(ler);
 
        for (int i = 0; i < 5; i++) {
            cadastrarJogadores(i, ler);
        }
 
        // Partidas
        registrarPartidas(ler);
 
        // Placar final
        System.out.println("\n=== PLACAR FINAL ===");
        for (int i = 0; i < 5; i++) {
            System.out.println(selecao[i] + " - " + pontuacao[i] + " pontos");
        }
 
        // Menu de consultas
        int opcao;
        do {
            System.out.println("\n========== MENU DE CONSULTAS ==========");
            System.out.println("1 - Seleção vencedora");
            System.out.println("2 - Artilheiro(s) do torneio");
            System.out.println("3 - Percentual de jogadores com mais de 5 gols");
            System.out.println("4 - Percentual de jogadores com gol de uma seleção");
            System.out.println("5 - Desempenho de todas as seleções");
            System.out.println("6 - Desempenho de todos os jogadores");
            System.out.println("0 - Encerrar");
            System.out.print("Escolha uma opção: ");
            opcao = ler.nextInt();
            ler.nextLine();
 
            switch (opcao) {
                case 1:
                    consultarVencedor();
                    break;
                case 2:
                    consultarArtilheiros();
                    break;
                case 3:
                    consultarPercentualMaisDe5Gols();
                    break;
                case 4:
                    consultarPercentualComGolPorSeleção(ler);
                    break;
                case 5:
                    listarDesempenhoSeleções();
                    break;
                case 6:
                    listarDesempenhoJogadores();
                    break;
                case 0:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
 
        ler.close();
    }
}
