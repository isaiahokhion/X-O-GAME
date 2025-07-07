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
            showNameInputScreen(); // ✅ Go to name input next
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

