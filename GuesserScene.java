package Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GuesserScene extends SceneBasic {
    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JTextArea hangmanArea;
    private JTextField guessField;
    private JButton guessButton;
    private String currentWord;
    private int currentAttempts;
    private Set<Character> guessedLetters;

    public GuesserScene(SceneManager manager) {
        super(manager);
        setLayout(new BorderLayout());
        
        guessedLetters = new HashSet<>();
        currentAttempts = 6;
        
        // Hangman display area
        hangmanArea = new JTextArea(10, 20);
        hangmanArea.setEditable(false);
        hangmanArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(hangmanArea), BorderLayout.WEST);
        
        // Game info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        infoPanel.add(wordLabel);
        
        attemptsLabel = new JLabel("Attempts left: 6", SwingConstants.CENTER);
        infoPanel.add(attemptsLabel);
        
        // Input panel
        JPanel inputPanel = new JPanel();
        guessField = new JTextField(5);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> makeGuess());
        guessField.addActionListener(e -> makeGuess());
        
        inputPanel.add(new JLabel("Enter letter:"));
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        infoPanel.add(inputPanel);
        
        add(infoPanel, BorderLayout.CENTER);
        
        // Back button
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> manager.setScene(SceneType.START));
        add(backButton, BorderLayout.SOUTH);
        
        drawHangman(6);
    }
    
    private void makeGuess() {
        String input = guessField.getText().trim().toUpperCase();
        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Please enter a single letter");
            return;
        }
        
        char guess = input.charAt(0);
        if (guessedLetters.contains(guess)) {
            JOptionPane.showMessageDialog(this, "You already guessed that letter");
            return;
        }
        
        guessedLetters.add(guess);
        manager.sendMessage("GUESS:" + guess);
        guessField.setText("");
    }
    
    public void updateGameState(String maskedWord, int attemptsLeft) {
        currentWord = maskedWord;
        currentAttempts = attemptsLeft;
        wordLabel.setText(maskedWord);
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        drawHangman(attemptsLeft);
    }
    
    private void drawHangman(int wrongAttempts) {
        String[] hangman = {
            "  ____",
            " |    |",
            " |    " + (wrongAttempts < 6 ? "O" : ""),
            " |   " + (wrongAttempts < 4 ? "/" : "") + 
                   (wrongAttempts < 5 ? "|" : "") + 
                   (wrongAttempts < 3 ? "\\" : ""),
            " |   " + (wrongAttempts < 2 ? "/" : "") + 
                   " " + (wrongAttempts < 1 ? "\\" : ""),
            "_|_"
        };
        
        hangmanArea.setText(String.join("\n", Arrays.copyOfRange(hangman, 0, 6 - wrongAttempts)));
    }
    
    @Override
    public void handleMessage(String message) {
        if (message.startsWith("WORD:")) {
            updateGameState(message.substring(5), currentAttempts);
        } else if (message.startsWith("ATTEMPTS_LEFT:")) {
            currentAttempts = Integer.parseInt(message.substring(14));
            updateGameState(currentWord, currentAttempts);
        } else if (message.startsWith("GAME_OVER:")) {
            String result = message.substring(10);
            if (result.equals("WIN")) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won!");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Game over! The word was: " + message.split(":")[2]);
            }
            manager.setScene(SceneType.START);
        } else if (message.startsWith("MESSAGE:")) {
            JOptionPane.showMessageDialog(this, message.substring(8));
        }
    }
}
