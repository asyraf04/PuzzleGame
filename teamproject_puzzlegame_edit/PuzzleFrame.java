import javax.swing.*;
import java.awt.*;

public class PuzzleFrame extends JFrame {
    private SlidePuzzleBoard board;
    private PuzzleButton[][] button_board;
    private StartButton start_button;
    private MyKeyboardListener keyboard;
    private int size;
    private int doneCounter=0;
    private JLabel counter_label = new JLabel("Move: 0");
    private JLabel done_counter = new JLabel("Done: 0");

    public PuzzleFrame(SlidePuzzleBoard b) {
        board = b;
        size = b.sz();
        start_button = new StartButton(board, this);
        keyboard = new MyKeyboardListener(board, this);
        button_board = new PuzzleButton[size][size];

        // Set frame properties
        setTitle("Slide Puzzle");
        setSize(Math.max(80 * size, 400) + 50, Math.max(80 * size, 400) + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Add the menu bar
        setJMenuBar(createMenuBar());

        // Initialize UI
        initUI();

        // Make frame visible
        setVisible(true);

        // Add key listener
        addKeyListener(keyboard);
        setFocusable(true);
        requestFocus();
    }

    private void initUI() {
        Container cp = getContentPane();
        cp.removeAll(); // Clear all existing components
        cp.setLayout(new BorderLayout());

        // Create North Panel (Counters + Start Button)
        JPanel north_panel = new JPanel(new BorderLayout());
        north_panel.setPreferredSize(new Dimension(getWidth(), 55));

        counter_label.setPreferredSize(new Dimension(120, 50));
        done_counter.setPreferredSize(new Dimension(120, 50));
        counter_label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        done_counter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        counter_label.setHorizontalAlignment(SwingConstants.LEFT);
        done_counter.setHorizontalAlignment(SwingConstants.RIGHT);
        north_panel.add(counter_label, BorderLayout.WEST);
        north_panel.add(done_counter, BorderLayout.EAST);

        // Add the new Start Button
        JPanel start_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        start_panel.add(start_button);
        north_panel.add(start_panel, BorderLayout.CENTER);

        cp.add(north_panel, BorderLayout.NORTH);

        // Create Grid Panel
        GridLayout gridL = new GridLayout(size, size);
        JPanel gridP = new JPanel(gridL);
        gridP.setPreferredSize(new Dimension(getWidth(), getHeight()+20));
        cp.add(gridP, BorderLayout.CENTER);

        // Initialize Puzzle Buttons and add to Grid Panel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button_board[i][j] = new PuzzleButton(board, this); // Create new buttons
                gridP.add(button_board[i][j]);
            }
        }

        // Refresh the UI
        revalidate();
        repaint();
        update();
    }

    public void update() {
        PuzzlePiece pp;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pp = board.getPuzzlePiece(i, j);
                if (pp != null) {
                    button_board[i][j].setText(Integer.toString(pp.faceValue()));
                    int add_brightness = (60 / size) * ((pp.faceValue() - 1) / size);
                    button_board[i][j].setBackground(new Color(188 + add_brightness, 192 + add_brightness, 196 + add_brightness));
                    button_board[i][j].setForeground(Color.black);
                } else {
                    if (board.gameOn() && board.gameOver()) {
                        button_board[i][j].setText("Done");
                        doneCounter++;
                        done_counter.setText("Done: " + doneCounter);
                    } else button_board[i][j].setText("");
                    button_board[i][j].setBackground(new Color(54, 57, 62));
                    button_board[i][j].setForeground(Color.white);
                }
            }
        }
        counter_label.setText("Move: " + board.getCounter());

        if (board.gameOn() ) start_button.setText("Restart");
        requestFocus();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Game Menu
        JMenu gameMenu = new JMenu("게임");
        JMenuItem grid3x3 = new JMenuItem("3x3");
        JMenuItem grid4x4 = new JMenuItem("4x4");
        JMenuItem grid5x5 = new JMenuItem("5x5");

        // Add action listeners for grid size changes
        grid3x3.addActionListener(e -> changeGridSize(3));
        grid4x4.addActionListener(e -> changeGridSize(4));
        grid5x5.addActionListener(e -> changeGridSize(5));

        gameMenu.add(grid3x3);
        gameMenu.add(grid4x4);
        gameMenu.add(grid5x5);

        // Help Menu
        JMenu helpMenu = new JMenu("도움말");
        JMenuItem instructions = new JMenuItem("지침들");

        // Add action listener to show instructions
        instructions.addActionListener(e -> showHelpDialog());
        helpMenu.add(instructions);

        // Add menus to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void changeGridSize(int newSize) {
        board = new SlidePuzzleBoard(newSize); // Create a new board
        size = newSize;
        button_board = new PuzzleButton[size][size]; // Reinitialize button board

        // Reinitialize the Start Button
        start_button = new StartButton(board, this); // Create a new Start Button

        // Update the frame size
        setSize(Math.max(80 * size, 400) + 50, Math.max(80 * size, 400) + 100);

        // Reinitialize the UI
        initUI();

        // Reattach the keyboard listener
        removeKeyListener(keyboard);
        keyboard = new MyKeyboardListener(board, this);
        addKeyListener(keyboard);

        // Refresh the frame
        revalidate();
        repaint();
    }

    private void showHelpDialog() {
        String instructions = """
            게임 방법:
            1. 'Start' 버튼을 눌러 게임을 시작합니다.
            2. 빈 칸과 인접한 타일을 클릭하거나,
               키보드의 방향키를 사용하여 타일을 이동시킵니다.
            3. 모든 타일을 올바른 순서로 정렬하면 승리합니다.
            4. 'Restart' 버튼을 눌러 보드를 다시 섞을 수 있습니다.
            """;
        JOptionPane.showMessageDialog(this, instructions, "도움말", JOptionPane.INFORMATION_MESSAGE);
    }
}
