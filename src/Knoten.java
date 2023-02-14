import java.util.ArrayList;

public class Knoten {

/*
Diese klasse repräsentiert die einzelnen Zustände, die wie in einem Baum miteinander verbunden sind.
Dies ist besonders wichtig um am Schluss die "Road to Glory" nachzuvollziehen

Knoten werden selten direkt durch den Constructor erstellt, sonder durch die appendKnoten methode an andere Knoten dran gehängt.
Die Manhatten-Distanz, welche wir als Schätzer für die Kosten verwenden, wird beim erstellen von Knoten einmal berechnet und dann gespeichert

 */

  Knoten parentKnoten;
  int[][] board = new int[3][3];
  int past = 0;
  int future;
  ArrayList<Knoten> children = new ArrayList<>();

  public Knoten(int[][] board, int past, Knoten parent) {
    this.board = board;
    this.past = past;
    initFuture();
    parentKnoten = parent;
  }

  public Knoten(int[][] board, int past) {
    this.board = board;
    this.past = past;

    initFuture();
  }

  public static int getManhattanDistance(int tile, int y, int x) {
    int[][] endBoard = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    int yDistance = 0;
    int xDistance = 0;

    if (endBoard[y][x] == tile) {
      return 0;
    } else {
      for (int i = 0; i < endBoard.length; i++) {
        for (int j = 0; j < endBoard[0].length; j++) {
          if (endBoard[i][j] == tile) {
            yDistance = i - y;
            xDistance = j - x;
            if (yDistance < 0) {
              yDistance *= (-1);
            } else if (xDistance < 0) {
              xDistance *= (-1);
            }
          }
        }
      }
      return xDistance + yDistance;
    }
  }

  public Knoten appendKnoten(int[][] board) {
    Knoten newKnoten = new Knoten(board, this.past + 1, this);
    this.children.add(newKnoten);
    return newKnoten;
  }

  void initFuture() {
    future = 0;

    for (int a = 0; a < 3; a++) {
      for (int b = 0; b < 3; b++) {
        future += getManhattanDistance(board[a][b], a, b);
      }
    }
  }
}
