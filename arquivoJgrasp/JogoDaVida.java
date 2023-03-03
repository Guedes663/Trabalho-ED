import java.util.Scanner;
import java.util.Random;

public class JogoDaVida {

  private int sizeMatriz;
  private int matriz[][];
  private int matrizNeighbors[][];
  private int alive;
  private int line;
  private int column;
  private String response;

  public void getSize() {

    // Método que pergunta ao usuário o tamanho da matriz.

    Scanner scanner = new Scanner(System.in);
    System.out.println("Digite o tamanho da matriz: ");
    this.sizeMatriz = scanner.nextInt();
    this.matriz = new int[sizeMatriz][sizeMatriz];

  }

  public void fillMatriz() {

    // Método que preenche a matriz com números aleatórios.

    Random random = new Random();
    for (int i = 0; i < this.matriz.length; i++) {
      for (int j = 0; j < this.matriz[i].length; j++) {
        matriz[i][j] = random.nextInt(2);
      }
    }

  }

  public void selectGame() {

    // Método que seleciona o jogo.

    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "Você deseja jogar o jogo da vida ou o jogo da aposta ?\n\nDigite 1 para jogo da vida ou 2 para jogo da aposta");
    this.response = scanner.next().toLowerCase();

    if (response.equals("1")) {
      jogoDaVida();

    } else {
      jogoDaAposta();
    }

  }

  public void jogoDaVida() {

    // Método que executa o jogo da vida.

    Scanner scanner = new Scanner(System.in);
    while (response.equals("1")) {
      System.out.println("\n");

      printMatriz();
      nextGeneration();

      System.out.println("Você deseja continuar ? (Digite 1 para continuar ou 2 para sair)");
      this.response = scanner.next().toLowerCase();
    }
  }

  public void jogoDaAposta() {

    // Método que executa o jogo da aposta.

    Scanner scanner = new Scanner(System.in);
    int counter = 0;
    this.response = "1";

    while (response.equals("1")) {
      System.out.println("Digite a quantidade de células que estarão vivas:");
      int bet = scanner.nextInt();

      nextGeneration();
      printMatriz();

      for (int i = 0; i < this.matriz.length; i++) {
        for (int j = 0; j < this.matriz[i].length; j++) {
          if (matriz[i][j] == 1) {
            counter = counter + 1;
          }
        }
      }

      if (counter == bet) {
        System.out.println("Parabéns, você acertou todos os " + bet + " 1!");
      } else {
        int acertos = Math.min(counter, bet);
        int erros = Math.max(counter, bet) - acertos;
        System.out.println("Você acertou " + acertos + " errou " + erros);
      }

      counter = 0;

      System.out.println("Você deseja continuar ? (Digite 1 para continuar ou 2 para sair)");
      this.response = scanner.next().toLowerCase();
    }
  }
  
  public void printMatriz() {

    // Método que imprime a matriz no console.

    for (int i = 0; i < this.matriz.length; i++) {
      for (int j = 0; j < this.matriz[i].length; j++) {
        System.out.print(matriz[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void nextGeneration() {

    // Método que substitui a matriz para a nova geração.

    int[][] newGeneration = new int[matriz.length][matriz[0].length];

    for (int i = 0; i < this.matriz.length; i++) {
      for (int j = 0; j < this.matriz[i].length; j++) {

        this.line = i;
        this.column = j;

        getNeighbors();
        checkNeighbors();

        if (this.matriz[i][j] == 1) {
          if (this.alive < 2 || this.alive > 3) {
            newGeneration[i][j] = 0;
          } else {
            newGeneration[i][j] = 1;
          }
        } else {
          if (this.alive == 3) {
            newGeneration[i][j] = 1;
          } else {
            newGeneration[i][j] = 0;
          }

        }

        this.alive = 0;
      }
    }
    this.matriz = newGeneration;
  }

  public void getNeighbors() {

    // Método que mostra as coordenadas dos vizinhos.

    int height = this.matriz.length;
    int width = this.matriz[0].length;
    int[][] neighbors = new int[4][2];

    // posição acima
    neighbors[0][0] = (this.line - 1 + height) % height;
    neighbors[0][1] = this.column;

    // posição à direita
    neighbors[1][0] = this.line;
    neighbors[1][1] = (this.column + 1) % width;

    // posição abaixo
    neighbors[2][0] = (this.line + 1) % height;
    neighbors[2][1] = this.column;

    // posição à esquerda
    neighbors[3][0] = this.line;
    neighbors[3][1] = (this.column - 1 + width) % width;

    this.matrizNeighbors = neighbors;
  }

  public void checkNeighbors() {

    // Guarda a quantidade de vizinhos vivos e mortos.

    float up = this.matriz[matrizNeighbors[0][0]][matrizNeighbors[0][1]];
    float right = this.matriz[matrizNeighbors[1][0]][matrizNeighbors[1][1]];
    float down = this.matriz[matrizNeighbors[2][0]][matrizNeighbors[2][1]];
    float left = this.matriz[matrizNeighbors[3][0]][matrizNeighbors[3][1]];

    if (up == 1) {
      this.alive = this.alive + 1;
    }

    if (right == 1) {
      this.alive = this.alive + 1;
    }

    if (down == 1) {
      this.alive = this.alive + 1;
    }

    if (left == 1) {
      this.alive = this.alive + 1;
    }
  }

}
