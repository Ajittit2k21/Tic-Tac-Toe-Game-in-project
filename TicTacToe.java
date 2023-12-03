import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {

    private JButton[][] buttons;
    private JLabel label;
    private String[] players = {"X", "O"};
    private String currentPlayer;
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Times", Font.PLAIN, 40));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setBackground(new Color(196, 195, 208));
                buttons[row][col].setForeground(Color.BLACK);
                buttons[row][col].setFont(new Font("Times", Font.BOLD, 30));
                buttons[row][col].setPreferredSize(new Dimension(100, 100));
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                buttonPanel.add(buttons[row][col]);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Restart");
        resetButton.setBackground(new Color(196, 195, 208));
        resetButton.setFont(new Font("Times", Font.BOLD, 20));
        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        add(resetButton, BorderLayout.SOUTH);

        newGame();
    }

    private void newGame() {
        currentPlayer = players[(int) (Math.random() * 2)];
        label.setText(currentPlayer + " Turn");

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(new Color(196, 195, 208));
            }
        }
    }

    private void nextTurn(int row, int col) {
        if (buttons[row][col].getText().equals("") && !checkWinner()) {
            buttons[row][col].setText(currentPlayer);

            if (!checkWinner()) {
                currentPlayer = (currentPlayer.equals(players[0])) ? players[1] : players[0];
                label.setText(currentPlayer + " Turn");
            } else {
                label.setText((currentPlayer.equals(players[0])) ? players[0] + " wins" : players[1] + " wins");
            }
        }
    }

    private boolean checkWinner() {
        for (int row = 0; row < ROWS; row++) {
            if (buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                buttons[row][0].getText().equals(buttons[row][2].getText()) &&
                !buttons[row][0].getText().equals("")) {
                highlightWinnerCells(row, 0, row, 2);
                return true;
            }
        }

        for (int col = 0; col < COLUMNS; col++) {
            if (buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                buttons[0][col].getText().equals(buttons[2][col].getText()) &&
                !buttons[0][col].getText().equals("")) {
                highlightWinnerCells(0, col, 2, col);
                return true;
            }
        }

        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            highlightWinnerCells(0, 0, 2, 2);
            return true;
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText()) &&
            !buttons[0][2].getText().equals("")) {
            highlightWinnerCells(0, 2, 2, 0);
            return true;
        }

        if (emptySpaces() == 0) {
            highlightTieCells();
            label.setText("Tie!");
            return true;
        }

        return false;
    }

    private void highlightWinnerCells(int row1, int col1, int row2, int col2) {
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                buttons[i][j].setBackground(Color.GREEN);
            }
        }
    }

    private void highlightTieCells() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                buttons[row][col].setBackground(Color.YELLOW);
            }
        }
    }

    private int emptySpaces() {
        int spaces = ROWS * COLUMNS;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (!buttons[row][col].getText().equals("")) {
                    spaces--;
                }
            }
        }
        return spaces;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            nextTurn(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe ticTacToe = new TicTacToe();
            ticTacToe.setSize(400, 400);
            ticTacToe.setLocationRelativeTo(null);
            ticTacToe.setVisible(true);
        });
    }
}
