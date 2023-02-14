import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class AStar {

  static ArrayList<Knoten> openedKnoten = new ArrayList<>();
  static ArrayList<Knoten> closedKnoten = new ArrayList<>();
  static TreeSet<String> openedBoards = new TreeSet<>();

  static boolean foundIt = false;
  static Knoten winningKnoten;

  public static void main(String[] args) {
    Knoten parentKnoten = new Knoten(new int[][]{{0, 6, 3}, {1, 4, 7}, {2, 5, 8}}, 0);
    Knoten knoten = parentKnoten.appendKnoten(parentKnoten.board);
    openedKnoten.add(knoten);
    openedBoards.add(arrayToString(knoten.board));
    play();
  }

  static void play() {
    System.out.println("calculating...");
    while (!foundIt) {
      makeMove();
    }

    if (winningKnoten != null) {
      printKnoten(winningKnoten.board);
      printRoadToGlory();
    }
  }

  static void printRoadToGlory() {
    System.out.println("\n\nRoad To Glory:");
    int count = 0;
    Knoten knoten = winningKnoten;
    while (knoten.parentKnoten != null) {
      count++;
      printKnoten(knoten.board);
      knoten = knoten.parentKnoten;
    }
    System.out.println("\nSteps needed: " + count);
  }

  static void makeMove() {
    if (openedKnoten.size() > 0) {
      Iterator<Knoten> it = openedKnoten.iterator();
      int fplusg = 0;
      boolean first = true;
      Knoten highestKnoten = null;
      while (it.hasNext() && !foundIt) {
        Knoten knoten = it.next();
        if (first) {
          highestKnoten = knoten;
          fplusg = knoten.past + knoten.future;
          first = !first;
        }
        if (fplusg > knoten.past + knoten.future) {
          highestKnoten = knoten;
          fplusg = knoten.past + knoten.future;
        }
      }
      openedKnoten.remove(highestKnoten);
      closedKnoten.add(highestKnoten);
      ArrayList<Knoten> newMoves = proposeMoves(highestKnoten);
      openedKnoten.addAll(newMoves);
      Iterator<Knoten> iterator = newMoves.iterator();
      while (iterator.hasNext()) {
        openedBoards.add(arrayToString(iterator.next().board));
      }
      if (highestKnoten.future == 0) {
        foundIt = true;
        winningKnoten = highestKnoten;
      }
    } else {
      System.out.println("unsolvable");
      foundIt = true;
    }
  }


  static ArrayList<Knoten> proposeMoves(Knoten knoten) {
    ArrayList<Knoten> moves = new ArrayList<>();
    int[] nullPos = getNullPos(knoten.board);
    if (nullPos[0] > 0) {
      int[][] board = exchange(nullPos[0] - 1, nullPos[1], knoten.board);
      //printKnoten(board);
      if (!isInArray(board)) {
        moves.add(knoten.appendKnoten(board));
      }
    }
    if (nullPos[0] < 2) {

      int[][] board = exchange(nullPos[0] + 1, nullPos[1], knoten.board);
      //printKnoten(board);
      if (!isInArray(board)) {
        moves.add(knoten.appendKnoten(board));
      }
    }
    if (nullPos[1] > 0) {
      int[][] board = exchange(nullPos[0], nullPos[1] - 1, knoten.board);
      //printKnoten(board);

      if (!isInArray(board)) {
        moves.add(knoten.appendKnoten(board));
      }
    }
    if (nullPos[1] < 2) {
      int[][] board = exchange(nullPos[0], nullPos[1] + 1, knoten.board);
      //printKnoten(board);

      if (!isInArray(board)) {
        moves.add(knoten.appendKnoten(board));
      }
    }
    return moves;
  }


  static int[] getNullPos(int[][] board) {
    for (int a = 0; a < 3; a++) {
      for (int b = 0; b < 3; b++) {
        if (board[a][b] == 0) {
          return new int[]{a, b};
        }
      }
    }
    return new int[0];
  }

  //optimizable
  static int[][] exchange(int x, int y, int[][] bo) {
    int[][] board = new int[3][3];
    for (int a = 0; a < 3; a++) {
      board[a] = bo[a].clone();
    }
    for (int a = 0; a < 3; a++) {
      for (int b = 0; b < 3; b++) {
        if (board[a][b] == 0) {
          board[a][b] = board[x][y];
          board[x][y] = 0;
        }
      }
    }
    return board;
  }

  static boolean isInArray(int[][] board) {
    return openedBoards.contains(arrayToString(board));
  }

  static boolean arrayHasBeenOpened(int[][] board, Knoten knoten) {

    Iterator<Knoten> iterator = knoten.parentKnoten.children.iterator();
    while (iterator.hasNext()) {
      int[][] boardInArray = iterator.next().board;
      boolean isEqual = true;
      for (int x = 0; x < 3; x++) {
        if (!Arrays.equals(boardInArray[x], board[x])) {
          isEqual = false;
        }
      }
      if (isEqual) {
        //System.out.println("found old knot");
        //
        //         printKnoten(boardInArray);
        //        System.out.println("\n==");
        //        printKnoten(board);
        return true;
      }
    }
    return false;
  }


  static void printKnoten(int[][] board) {
    System.out.print("\n -v-");

    for (int x = 0; x < 3; x++) {
      System.out.println();
      for (int y = 0; y < 3; y++) {
        System.out.print(board[x][y] + "\t");
      }
    }
  }


  static String arrayToString(int[][] arr) {
    String s = "";
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        s += arr[x][y];
      }
    }
    return s;
  }
}
