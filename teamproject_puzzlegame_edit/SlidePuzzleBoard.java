import java.util.ArrayList;
import java.util.Collections;

public class SlidePuzzleBoard {
    private PuzzlePiece[][] board;
    private int size;
    private int empty_row;
    private int empty_col;
    private boolean game_on = false;
    private int counter = 0;
    public SlidePuzzleBoard(int s) {
        size = s;
        assert(size > 1);
        board = new PuzzlePiece[size][size];
        int num = 1;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                board[i][j] = new PuzzlePiece(num);
                num++;
            }
        }
        board[size-1][size-1] = null;
        empty_row = size-1;
        empty_col = size-1;
    }
    public PuzzlePiece getPuzzlePiece(int row, int col) {
        return board[row][col];
    }

    public int find(int r, int c) {
        if (r>=size || c>=size) return 0;
        if (r<0 || c<0) return 0;
        if (board[r][c] == null) return 0;
        return board[r][c].faceValue();
    }

    public boolean found(int w, int r, int c) {
        if (r>=size || c>=size) return false;
        if (r<0 || c<0) return false;
        if (board[r][c] == null) return false;
        return board[r][c].faceValue() == w;
    }
    public boolean move(int w) {
        int r,c;
        if (!game_on) return false;
        if (found(w, empty_row+1, empty_col)) {
            r = empty_row+1;
            c = empty_col;
        }
        else if (found(w, empty_row-1, empty_col)) {
            r = empty_row-1;
            c = empty_col;
        }
        else if (found(w, empty_row, empty_col+1)) {
            r = empty_row;
            c = empty_col+1;
        }
        else if (found(w, empty_row, empty_col-1)) {
            r = empty_row;
            c = empty_col-1;
        }
        else {
            return false;
        }
        board[empty_row][empty_col] = board[r][c];
        board[r][c] = null;
        empty_row = r;
        empty_col = c;
        counter++;
        return true;
    }

    public void createPuzzleBoard() {
        ArrayList<Integer> arr = new ArrayList<>(size*size);
        for (int i=1; i<size*size; i++) arr.add(i);

        Collections.shuffle(arr);

        int inv_count = inversion_count(arr);
        if (inv_count%2 == 1) {
            for (int i=1; i<size*size-1; i++) {
                if (arr.get(i-1)>arr.get(i)) {
                    Collections.swap(arr, i-1, i);
                    break;
                }
            }
        }
        inv_count = inversion_count(arr);
        if (inv_count == 0) {
            Collections.swap(arr, 0, 1);
            Collections.swap(arr, 1, 2);
        }

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i==size-1 && j==size-1) break;
                board[i][j] = new PuzzlePiece(arr.get(i*size+j));
            }
        }
        board[size-1][size-1] = null;
        empty_row = size-1;
        empty_col = size-1;

        game_on = true;
    }

    private int inversion_count(ArrayList<Integer> arr) {
        int inv_count = 0;
        int sz = arr.size();
        for (int i=0; i<sz; i++) {
            for (int j=0; j<i; j++) {
                if (arr.get(j) > arr.get(i)) inv_count++;
            }
        }
        return inv_count;
    }

    public boolean gameOn() {
        return game_on;
    }

    public boolean gameOver() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i==size-1 && j==size-1) break;
                if (board[i][j] == null || board[i][j].faceValue()!=i*size+j+1) return false;
            }
        }
        game_on = false;

        return true;
    }

    public int sz() {return size;}
    public int emptyRow() {return empty_row;}
    public int emptyCol() {return empty_col;}
    public int getCounter() {return counter;}
    public void resetCount() {counter=0;}
}
