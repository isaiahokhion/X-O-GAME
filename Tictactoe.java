import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Tictactoe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlJPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    JButton restartButton = new JButton("Restart");

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    String player1Name = "Player 1";
    String player2Name = "Player 2";

    boolean gameOver = false;
    int turns = 0;

    public static void main(String[] args) {
        new Tictactoe().showWelcomeScreen(); 
    }

    void showWelcomeScreen() {
        JFrame welcomeFrame = new JFrame("Welcome");
        welcomeFrame.setSize(400, 300);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new BorderLayout());
        welcomeFrame.getContentPane().setBackground(new Color(40, 40, 40));

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(70, 130, 180));
        startButton.setForeground(Color.WHITE);
                startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            welcomeFrame.dispose();
            showNameInputScreen(); 
        });

        welcomeFrame.add(welcomeLabel, BorderLayout.CENTER);
        welcomeFrame.add(startButton, BorderLayout.SOUTH);
        welcomeFrame.setVisible(true);
    }

    void showNameInputScreen() {
        JFrame nameFrame = new JFrame("Enter Player Names");
        nameFrame.setSize(400, 300);
        nameFrame.setLocationRelativeTo(null);
        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameFrame.setLayout(new BorderLayout(10, 10));
        nameFrame.getContentPane().setBackground(new Color(30, 30, 30));

        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        inputPanel.setBackground(new Color(30, 30, 30));
        inputPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JTextField player1Field = new JTextField();
        JTextField player2Field = new JTextField();

        player1Field.setFont(new Font("Arial", Font.PLAIN, 18));
        player2Field.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel label1 = new JLabel("Enter Player 1 Name (X):");
        label1.setForeground(Color.WHITE);
        JLabel label2 = new JLabel("Enter Player 2 Name (O):");
        label2.setForeground(Color.WHITE);

        inputPanel.add(label1);
        inputPanel.add(player1Field);
        inputPanel.add(label2);
        inputPanel.add(player2Field);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 18));
        continueButton.setBackground(new Color(60, 150, 80));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        continueButton.addActionListener(e -> {
            String name1 = player1Field.getText().trim();
            String name2 = player2Field.getText().trim();

                    if (!name1.isEmpty()) player1Name = name1;
            if (!name2.isEmpty()) player2Name = name2;

            nameFrame.dispose();
            setupGameUI();
        });

        nameFrame.add(inputPanel, BorderLayout.CENTER);
        nameFrame.add(continueButton, BorderLayout.SOUTH);
        nameFrame.setVisible(true);
    }

    void setupGameUI() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(new Color(45, 45, 45));
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(player1Name + "'s Turn (X)");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(25, 25, 25));
        boardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(boardPanel, BorderLayout.CENTER);

        controlJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlJPanel.setBackground(new Color(30, 30, 30));

        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setBackground(new Color(200, 50, 50));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setUI(new BasicButtonUI());
        restartButton.addActionListener(e -> resetGame());

        controlJPanel.add(restartButton);
        frame.add(controlJPanel, BorderLayout.SOUTH);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(new Color(60, 60, 60));
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setFocusPainted(false);
                tile.setUI(new BasicButtonUI());

                tile.addActionListener(e -> {
                    if (gameOver) return;
                    JButton source = (JButton) e.getSource();
                    if (source.getText().equals("")) {
                        source.setText(currentPlayer);
                        turns++;
                        checkWinner();
                        if (!gameOver) {
                            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                            String nameTurn = currentPlayer.equals(playerX) ? player1Name : player2Name;
                            textLabel.setText(nameTurn + "'s Turn (" + currentPlayer + ")");
                        }
                    }
                });
            }
        }
    }

    void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;
            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) setWinner(board[r][i]);
                gameOver = true;
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) setWinner(board[i][c]);
                gameOver = true;
                return;
            }
        }

        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) setWinner(board[i][i]);
            gameOver = true;
            return;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++)
                for (int c = 0; c < 3; c++)
                    setTie(board[r][c]);
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.BLACK);
        tile.setBackground(Color.GREEN);
        String winnerName = currentPlayer.equals(playerX) ? player1Name : player2Name;
        textLabel.setText("🏆 " + winnerName + " wins!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.ORANGE);
        tile.setBackground(Color.GRAY);
        textLabel.setText("🤝 It's a Tie!");
    }

    void resetGame() {
        currentPlayer = playerX;
        turns = 0;
        gameOver = false;
        textLabel.setText(player1Name + "'s Turn (X)");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(new Color(60, 60, 60));
                board[r][c].setForeground(Color.WHITE);
            }
        }
    }
}

    
