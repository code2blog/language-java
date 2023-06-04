package com.wordpress.code2blog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeUI extends JFrame implements ActionListener {

    private JButton[][] buttons = new JButton[3][3];
    private String[] symbols = {"X", "O"};
    private int currentPlayer = 0;
    private boolean gameOver = false;

    public TicTacToeUI() {
        super("Tic Tac Toe");

        // Create the buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
            }
        }

        // Add the buttons to the frame
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                contentPane.add(buttons[i][j]);
            }
        }

        // Set the frame's size and make it visible
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the button that was clicked
        JButton button = (JButton) e.getSource();

        // Check if the game is over
        if (gameOver) {
            return;
        }

        // Set the button's text to the current player's symbol
        button.setText(symbols[currentPlayer]);

        // Check if the current player has won
        if (checkWinner()) {
            // Game over!
            gameOver = true;
            JOptionPane.showMessageDialog(this, currentPlayer + " wins!");
        } else {
            // Switch to the next player
            currentPlayer = (currentPlayer + 1) % 2;
        }
    }

    private boolean checkWinner() {
        // Check for a winner in each row
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                buttons[i][0].getText() != "") {
                return true;
            }
        }

        // Check for a winner in each column
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                buttons[1][j].getText().equals(buttons[2][j].getText()) &&
                buttons[0][j].getText() != "") {
                return true;
            }
        }

        // Check for a winner in the diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText()) &&
            buttons[0][0].getText() != "") {
            return true;
        } else if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                   buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                   buttons[0][2].getText() != "") {
            return true;
        }

        // No winner yet
        return false;
    }

    public static void main(String[] args) {
        // Create and display the frame
        new TicTacToeUI();
    }
}
