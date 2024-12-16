import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends JButton implements ActionListener {
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;

    StartButton(SlidePuzzleBoard b, PuzzleFrame f) {
        super("Start");
        setPreferredSize(new Dimension(100, 45));
        setBackground(new Color(240, 245, 250));
        board = b;
        frame = f;
        addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        board.resetCount();
        board.createPuzzleBoard();
        board.gameOn();
        frame.update();
        frame.requestFocus();
    }
}
