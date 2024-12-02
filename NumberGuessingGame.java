import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    private int numberToGuess;
    private int attemptsLeft;
    private int score;
    private final int maxAttempts = 10;
    private Random random;

    // Swing components
    private JFrame frame;
    private JTextField guessInput;
    private JButton guessButton;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGame() {
        random = new Random();
        initializeGame();
        createGUI();
    }

    private void initializeGame() {
        numberToGuess = random.nextInt(100) + 1; // Generate a number between 1 and 100
        attemptsLeft = maxAttempts;
        if (score == 0) {
            score = 0; // Reset score only when starting the first game
        }
    }

    private void createGUI() {
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(400, 300);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(5, 1));

        // Instruction label
        JLabel instructionLabel = new JLabel("Guess a number between 1 and 100:", SwingConstants.CENTER);
        frame.add(instructionLabel);

        // Input field and button
        JPanel inputPanel = new JPanel();
        guessInput = new JTextField(10);
        guessButton = new JButton("Submit Guess");
        inputPanel.add(guessInput);
        inputPanel.add(guessButton);
        frame.add(inputPanel);

        // Message label
        messageLabel = new JLabel("You have " + maxAttempts + " attempts left.", SwingConstants.CENTER);
        frame.add(messageLabel);

        // Attempts and score label
        attemptsLabel = new JLabel("Attempts Left: " + maxAttempts, SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        frame.add(attemptsLabel);
        frame.add(scoreLabel);

        // Button click event
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        frame.setVisible(true);
    }

    private void handleGuess() {
        String guessText = guessInput.getText();
        try {
            int guess = Integer.parseInt(guessText);
            if (guess < 1 || guess > 100) {
                messageLabel.setText("Please enter a number between 1 and 100!");
                return;
            }

            attemptsLeft--;
            if (guess == numberToGuess) {
                score++;
                JOptionPane.showMessageDialog(frame,
                        "Correct! The number was " + numberToGuess + ". Your score: " + score);
                startNewGame();
                return;
            } else if (guess > numberToGuess) {
                messageLabel.setText("Too high! Try again.");
            } else {
                messageLabel.setText("Too low! Try again.");
            }

            if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(frame,
                        "Game over! The correct number was " + numberToGuess + ". Your score: " + score);
                startNewGame();
                return;
            }

            attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input! Please enter a number.");
        }

        guessInput.setText("");
    }

    private void startNewGame() {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Play Again?",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initializeGame();
            messageLabel.setText("You have " + maxAttempts + " attempts left.");
            attemptsLabel.setText("Attempts Left: " + maxAttempts);
            guessInput.setText("");
        } else {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGame());
    }
}
