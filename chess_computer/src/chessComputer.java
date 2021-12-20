import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class chessComputer {
  public static void main(String[] args) {
    int[][] playerField = createPlayerField();
    String[][] colorField = createColorField();
    boolean isBlack = false;
    boolean isKingAlive = true;
    int man = 0;
    List<List<Integer>> possibleMoves = null;

    
    while (man == 0 && isKingAlive || possibleMoves != null && possibleMoves.size() == 0 && isKingAlive) {
      int x = getRandom(0, 7);
      int y = getRandom(0, 7);

      man = getManOnTurn(x, y, isBlack, playerField, colorField);
      if (man > 0) {
        possibleMoves = removeImpossibleMoves(x, y, isBlack, playerField, colorField, man);
        if (possibleMoves.size() > 0) {
          List<Integer> coordinatesOfEnemy = getCoordinatesOfEnemyOrRandom(isBlack, playerField, colorField, possibleMoves);
          int nextMoveX = coordinatesOfEnemy.get(0);
          int nextMoveY = coordinatesOfEnemy.get(1);

          if (playerField[nextMoveX][nextMoveY] == 2) {
            isKingAlive = false;
            System.out.println("Checkmate, " + (isBlack ? "black" : "white") + " won");
            System.out.println("---------");
          }

          playerField[nextMoveX][nextMoveY] = man;
          colorField[nextMoveX][nextMoveY] = isBlack ? "black" : "white";
          
          playerField[x][y] = 0;
          colorField[x][y] = null;

          man = 0;
          isBlack = !isBlack;

          printIntField(playerField);
          printStrField(colorField);
          System.out.println("---------");
        }
      }
    }
  }

  public static List<Integer> getCoordinatesOfEnemyOrRandom(boolean isBlack, int[][] playerField, String[][] colorField, List<List<Integer>> possibleMoves) {
    for (int i = 0; i < possibleMoves.size(); i++) {
      int nextMoveX = possibleMoves.get(i).get(0);
      int nextMoveY = possibleMoves.get(i).get(1);
      String color = colorField[nextMoveX][nextMoveY];

      if (!isSameColor(color, isBlack) && color != null) {                
        return possibleMoves.get(i);
      }
    }
    int to = possibleMoves.size() - 1;
    int index = getRandom(0, to);                 
    return possibleMoves.get(index);
  }

  // removes fields occupied by men of own color
  public static List<List<Integer>> removeImpossibleMoves(int x, int y, boolean isBlack, int[][] playerField, String[][] colorField, int man) { 
    List<List<Integer>> possibleMoves = getPossibleMoves(x, y, isBlack, man);
    
    for (int i = 0; i < possibleMoves.size(); i++) {
      int moveX = possibleMoves.get(i).get(0);
      int moveY = possibleMoves.get(i).get(1);
      String color = colorField[moveX][moveY];
      
      if (playerField[moveX][moveY] != 0 && isSameColor(color, isBlack)) {
        possibleMoves.remove(i);
      }
    }
    
    return possibleMoves;
  }

  public static boolean isSameColor(String color, boolean isBlack) {
    if (color == "black") {
      return isBlack == true;
    } else if (color == "white") {
      return isBlack == false;
    } else {
      return false;
    }
  }

  public static int getManOnTurn(int x, int y, boolean isBlack, int[][] playerField, String[][] colorField) {
    int man = playerField[x][y];
    String color = colorField[x][y];

    if (isSameColor(color, isBlack)) {
      return man;
    } else {
      return 0;
    }
  }

  public static int getRandom(int min, int max){
    return min + (int)(Math.random() * ((max - min) + 1));
  }

  public static String[][] createColorField() {
    String[][] field = new String[8][8];

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (i == 0 || i == 1) {
          field[j][i] = "black";
        }
        if (i == 6 || i == 7) {
          field[j][i] = "white";
        }
      }
    }

    return field;
  }

  public static int[][] createPlayerField() {
    int[][] field = new int[8][8];

    field[0][0] = 3;
    field[7][0] = 3;
    field[1][0] = 6;
    field[6][0] = 6;
    field[2][0] = 4;
    field[5][0] = 4;
    field[3][0] = 5;
    field[4][0] = 2;
    
    field[0][7] = 3;
    field[7][7] = 3;
    field[1][7] = 6;
    field[6][7] = 6;
    field[2][7] = 4;
    field[5][7] = 4;
    field[3][7] = 5;
    field[4][7] = 2;

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (i == 1 || i == 6) {
          field[j][i] = 1;
        }
      }
    }

    return field;
  }

  public static List<List<Integer>> getPossibleMoves(int x, int y, boolean isBlack, int man) {
    boolean[][] field = {};

    if (man == 1) {
      field = getPossibleMovesPawn(x, y, isBlack);
    } else if (man == 2) {
      field = getPossibleMovesKing(x, y, isBlack);
    } else if (man == 3) {
      field = getPossibleMovesRook(x, y, isBlack);
    } else if (man == 4) {
      field = getPossibleMovesBishop(x, y, isBlack);
    } else if (man == 5) {
      field = getPossibleMovesQueen(x, y, isBlack);
    } else if (man == 6) {
      field = getPossibleMovesKnight(x, y, isBlack);
    }

    List<List<Integer>> list = new ArrayList<List<Integer>>();

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (field[i][j]) {
          list.add(Arrays.asList(i, j));
        }
      }
    }

    return list;
  }

  public static boolean[][] createFieldArr() {
    boolean[][] fieldArr = new boolean[8][8];
    return fieldArr;
  }

  public static boolean[][] getPossibleMovesKnight(int x, int y, boolean isBlack) {
    boolean[][] field = createFieldArr();

    if (x <= 5 && y <= 6) {
      field[x + 2][y + 1] = true;
    }
    if (x <= 6 && y <= 5) {
      field[x + 1][y + 2] = true;
    }
    if (x >= 1 && y <= 5) {
      field[x - 1][y + 2] = true;
    }
    if (x >= 2 && y <= 6) {
      field[x - 2][y + 1] = true;
    }
    if (x >= 2 && y >= 1) {
      field[x - 2][y - 1] = true;
    }
    if (x >= 1 && y >= 2) {
      field[x - 1][y - 2] = true;
    }
    if (x <= 6 && y >= 2) {
      field[x + 1][y - 2] = true;
    }
    if (x <= 5 && y >= 1) {
      field[x + 2][y - 1] = true;
    }
    
    return field;
  }
  
  public static boolean[][] getPossibleMovesQueen(int x, int y, boolean isBlack) {
    boolean[][] fieldArrBishop = getPossibleMovesBishop(x, y, isBlack);
    boolean[][] fieldArrRook = getPossibleMovesRook(x, y, isBlack);

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (fieldArrBishop[i][j] || fieldArrRook[i][j]) {
          fieldArrBishop[i][j] = true;
        }
      }
    }

    return fieldArrBishop;
  }

  public static boolean[][] getPossibleMovesBishop(int x, int y, boolean isBlack) {
    boolean[][] field = createFieldArr();

    int unMutatedX = x;
    int unMutatedY = y;

    while (x > 0 && y > 0) {
      x -= 1;
      y -= 1;
      field[x][y] = true;
    }
    x = unMutatedX;
    y = unMutatedY;

    while (x < 7 && y > 0) {
      x += 1;
      y -= 1;
      field[x][y] = true;
    }
    x = unMutatedX;
    y = unMutatedY;

    while (x < 7 && y < 7) {
      x += 1;
      y += 1;
      field[x][y] = true;
    }
    x = unMutatedX;
    y = unMutatedY;

    while (x > 0 && y < 7) {
      x -= 1;
      y += 1;
      field[x][y] = true;
    }

    return field;
  }
  
  public static boolean[][] getPossibleMovesRook(int x, int y, boolean isBlack) {
    boolean[][] field = createFieldArr();

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (i == x || j == y) {
          field[i][j] = true;
        }
      }
    }
    field[x][y] = false;

    return field;
  }
  
  public static boolean[][] getPossibleMovesKing(int x, int y, boolean isBlack) {
    boolean[][] field = createFieldArr();

    if (x - 1 >= 0 && y + 1 <= 7) {
      field[x - 1][y + 1] = true;
    }
    if (x - 1 >= 0) {
      field[x - 1][y] = true;
    }
    if (x - 1 >= 0 && y - 1 >= 0) {
      field[x - 1][y - 1] = true;
    }
    if (y - 1 >= 0) {
      field[x][y - 1] = true;
    }
    if (y + 1 <= 7) {
      field[x][y + 1] = true;
    }
    if (x + 1 <= 7 && y - 1 >= 0) {
      field[x + 1][y - 1] = true;
    }
    if (x + 1 <= 7) {
      field[x + 1][y] = true;
    }
    if (x + 1 <= 7 && y + 1 <= 7) {
      field[x + 1][y + 1] = true;
    }

    return field;
  }

  public static boolean[][] getPossibleMovesPawn(int x, int y, boolean isBlack) {
    boolean[][] field = createFieldArr();
    if (isBlack) {
      if (y == 7) return field;
      if (y == 1) {
        field[x][y + 2] = true;
      }
      field[x][y + 1] = true;
    } else {
      if (y == 0) return field;
      if (y == 6) {
        field[x][y - 2] = true;
      }
      field[x][y - 1] = true;
    }

    return field;
  }

  public static void printField(boolean[][] field) {
    System.out.print(field[0][0] ? 1 : 0);
    System.out.print(field[1][0] ? 1 : 0);
    System.out.print(field[2][0] ? 1 : 0);
    System.out.print(field[3][0] ? 1 : 0);
    System.out.print(field[4][0] ? 1 : 0);
    System.out.print(field[5][0] ? 1 : 0);
    System.out.print(field[6][0] ? 1 : 0);
    System.out.println(field[7][0] ? 1 : 0);
    
    System.out.print(field[0][1] ? 1 : 0);
    System.out.print(field[1][1] ? 1 : 0);
    System.out.print(field[2][1] ? 1 : 0);
    System.out.print(field[3][1] ? 1 : 0);
    System.out.print(field[4][1] ? 1 : 0);
    System.out.print(field[5][1] ? 1 : 0);
    System.out.print(field[6][1] ? 1 : 0);
    System.out.println(field[7][1] ? 1 : 0);
    
    System.out.print(field[0][2] ? 1 : 0);
    System.out.print(field[1][2] ? 1 : 0);
    System.out.print(field[2][2] ? 1 : 0);
    System.out.print(field[3][2] ? 1 : 0);
    System.out.print(field[4][2] ? 1 : 0);
    System.out.print(field[5][2] ? 1 : 0);
    System.out.print(field[6][2] ? 1 : 0);
    System.out.println(field[7][2] ? 1 : 0);
    
    System.out.print(field[0][3] ? 1 : 0);
    System.out.print(field[1][3] ? 1 : 0);
    System.out.print(field[2][3] ? 1 : 0);
    System.out.print(field[3][3] ? 1 : 0);
    System.out.print(field[4][3] ? 1 : 0);
    System.out.print(field[5][3] ? 1 : 0);
    System.out.print(field[6][3] ? 1 : 0);
    System.out.println(field[7][3] ? 1 : 0);
    
    System.out.print(field[0][4] ? 1 : 0);
    System.out.print(field[1][4] ? 1 : 0);
    System.out.print(field[2][4] ? 1 : 0);
    System.out.print(field[3][4] ? 1 : 0);
    System.out.print(field[4][4] ? 1 : 0);
    System.out.print(field[5][4] ? 1 : 0);
    System.out.print(field[6][4] ? 1 : 0);
    System.out.println(field[7][4] ? 1 : 0);
    
    System.out.print(field[0][5] ? 1 : 0);
    System.out.print(field[1][5] ? 1 : 0);
    System.out.print(field[2][5] ? 1 : 0);
    System.out.print(field[3][5] ? 1 : 0);
    System.out.print(field[4][5] ? 1 : 0);
    System.out.print(field[5][5] ? 1 : 0);
    System.out.print(field[6][5] ? 1 : 0);
    System.out.println(field[7][5] ? 1 : 0);
    
    System.out.print(field[0][6] ? 1 : 0);
    System.out.print(field[1][6] ? 1 : 0);
    System.out.print(field[2][6] ? 1 : 0);
    System.out.print(field[3][6] ? 1 : 0);
    System.out.print(field[4][6] ? 1 : 0);
    System.out.print(field[5][6] ? 1 : 0);
    System.out.print(field[6][6] ? 1 : 0);
    System.out.println(field[7][6] ? 1 : 0);
    
    System.out.print(field[0][7] ? 1 : 0);
    System.out.print(field[1][7] ? 1 : 0);
    System.out.print(field[2][7] ? 1 : 0);
    System.out.print(field[3][7] ? 1 : 0);
    System.out.print(field[4][7] ? 1 : 0);
    System.out.print(field[5][7] ? 1 : 0);
    System.out.print(field[6][7] ? 1 : 0);
    System.out.println(field[7][7] ? 1 : 0);
  }

  public static void printIntField(int[][] field) {
    System.out.print(field[0][0]);
    System.out.print(field[1][0]);
    System.out.print(field[2][0]);
    System.out.print(field[3][0]);
    System.out.print(field[4][0]);
    System.out.print(field[5][0]);
    System.out.print(field[6][0]);
    System.out.println(field[7][0]);
    
    System.out.print(field[0][1]);
    System.out.print(field[1][1]);
    System.out.print(field[2][1]);
    System.out.print(field[3][1]);
    System.out.print(field[4][1]);
    System.out.print(field[5][1]);
    System.out.print(field[6][1]);
    System.out.println(field[7][1]);
    
    System.out.print(field[0][2]);
    System.out.print(field[1][2]);
    System.out.print(field[2][2]);
    System.out.print(field[3][2]);
    System.out.print(field[4][2]);
    System.out.print(field[5][2]);
    System.out.print(field[6][2]);
    System.out.println(field[7][2]);
    
    System.out.print(field[0][3]);
    System.out.print(field[1][3]);
    System.out.print(field[2][3]);
    System.out.print(field[3][3]);
    System.out.print(field[4][3]);
    System.out.print(field[5][3]);
    System.out.print(field[6][3]);
    System.out.println(field[7][3]);
    
    System.out.print(field[0][4]);
    System.out.print(field[1][4]);
    System.out.print(field[2][4]);
    System.out.print(field[3][4]);
    System.out.print(field[4][4]);
    System.out.print(field[5][4]);
    System.out.print(field[6][4]);
    System.out.println(field[7][4]);
    
    System.out.print(field[0][5]);
    System.out.print(field[1][5]);
    System.out.print(field[2][5]);
    System.out.print(field[3][5]);
    System.out.print(field[4][5]);
    System.out.print(field[5][5]);
    System.out.print(field[6][5]);
    System.out.println(field[7][5]);
    
    System.out.print(field[0][6]);
    System.out.print(field[1][6]);
    System.out.print(field[2][6]);
    System.out.print(field[3][6]);
    System.out.print(field[4][6]);
    System.out.print(field[5][6]);
    System.out.print(field[6][6]);
    System.out.println(field[7][6]);
    
    System.out.print(field[0][7]);
    System.out.print(field[1][7]);
    System.out.print(field[2][7]);
    System.out.print(field[3][7]);
    System.out.print(field[4][7]);
    System.out.print(field[5][7]);
    System.out.print(field[6][7]);
    System.out.println(field[7][7]);
  }
  
  public static void printStrField(String[][] field) {
    System.out.print(field[0][0]);
    System.out.print(field[1][0]);
    System.out.print(field[2][0]);
    System.out.print(field[3][0]);
    System.out.print(field[4][0]);
    System.out.print(field[5][0]);
    System.out.print(field[6][0]);
    System.out.println(field[7][0]);
    
    System.out.print(field[0][1]);
    System.out.print(field[1][1]);
    System.out.print(field[2][1]);
    System.out.print(field[3][1]);
    System.out.print(field[4][1]);
    System.out.print(field[5][1]);
    System.out.print(field[6][1]);
    System.out.println(field[7][1]);
    
    System.out.print(field[0][2]);
    System.out.print(field[1][2]);
    System.out.print(field[2][2]);
    System.out.print(field[3][2]);
    System.out.print(field[4][2]);
    System.out.print(field[5][2]);
    System.out.print(field[6][2]);
    System.out.println(field[7][2]);
    
    System.out.print(field[0][3]);
    System.out.print(field[1][3]);
    System.out.print(field[2][3]);
    System.out.print(field[3][3]);
    System.out.print(field[4][3]);
    System.out.print(field[5][3]);
    System.out.print(field[6][3]);
    System.out.println(field[7][3]);
    
    System.out.print(field[0][4]);
    System.out.print(field[1][4]);
    System.out.print(field[2][4]);
    System.out.print(field[3][4]);
    System.out.print(field[4][4]);
    System.out.print(field[5][4]);
    System.out.print(field[6][4]);
    System.out.println(field[7][4]);
    
    System.out.print(field[0][5]);
    System.out.print(field[1][5]);
    System.out.print(field[2][5]);
    System.out.print(field[3][5]);
    System.out.print(field[4][5]);
    System.out.print(field[5][5]);
    System.out.print(field[6][5]);
    System.out.println(field[7][5]);
    
    System.out.print(field[0][6]);
    System.out.print(field[1][6]);
    System.out.print(field[2][6]);
    System.out.print(field[3][6]);
    System.out.print(field[4][6]);
    System.out.print(field[5][6]);
    System.out.print(field[6][6]);
    System.out.println(field[7][6]);
    
    System.out.print(field[0][7]);
    System.out.print(field[1][7]);
    System.out.print(field[2][7]);
    System.out.print(field[3][7]);
    System.out.print(field[4][7]);
    System.out.print(field[5][7]);
    System.out.print(field[6][7]);
    System.out.println(field[7][7]);
  }
}
