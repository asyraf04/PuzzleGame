import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyboardListener extends KeyAdapter {

    private SlidePuzzleBoard board;
    private PuzzleFrame frame;

    public MyKeyboardListener(SlidePuzzleBoard b, PuzzleFrame f) {
        board = b;
        frame = f;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int r = board.emptyRow();
        int c = board.emptyCol();
        switch (key) {
            case KeyEvent.VK_ENTER:
                board.resetCount();
                board.createPuzzleBoard();
                board.gameOn();
                frame.update();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                if (board.move(board.find(r, c-1))) frame.update();
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                if (board.move(board.find(r, c+1))) frame.update();
                break;
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                if (board.move(board.find(r-1, c))) frame.update();
                break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                if (board.move(board.find(r+1, c))) frame.update();
                break;
        }
    }
}
