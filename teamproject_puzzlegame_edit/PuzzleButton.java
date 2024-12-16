import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleButton extends JButton implements ActionListener {
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;

    public PuzzleButton(SlidePuzzleBoard b, PuzzleFrame f) {
        board = b;
        frame = f;
        addActionListener(this);
        setFont(new Font("", Font.BOLD, 15));
    }
    public void actionPerformed(ActionEvent e) {
        String s = getText();
        if (!"".equals(s) && !"Done".equals(s) && board.move(Integer.parseInt(s))) {
            frame.update();
        }
        frame.requestFocus();
    }
}
