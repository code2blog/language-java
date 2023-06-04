package com.wordpress.code2blog;

import java.util.Date;

public class TicTacToe {

    private char[][] board = new char[3][3];
    private char winner = ' ';

    public TicTacToe(){
        setBoardValues(board);
    }

    public void setBoardValues(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void drawBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public char getPlayerInput() {
        System.out.println("Enter your move (1-9): ");
        int move = Integer.parseInt(System.console().readLine());

        char symbol = 'X';
        if (move % 2 == 1) {
            symbol = 'O';
        }

        return symbol;
    }

    public boolean checkWinner() {
        // check x axis of the board for wins
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                winner = board[i][0];
                return true;
            }
        }

        // check y axis of the board for wins
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
                winner = board[0][i];
                return true;
            }
        }

        // check the top-to-bottom diagonal space of the board for wins
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            winner = board[0][0];
            return true;
        }

        // check the bottom-to-top diagonal space of the board for wins
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            winner = board[0][2];
            return true;
        }

        return false;
    }

    public boolean isTie() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }

        return true;
    }
    
    public void playGame() {
        int move = -1;
        while (!checkWinner() && !isTie()) {
            move += 1;
            int row = move / 3;
            int column = move % 3;

            drawBoard();
            char symbol = getPlayerInput();
            board[row][column] = symbol;
        }

        if (checkWinner()) {
            System.out.println("Player " + winner + " wins!");
        } else {
            System.out.println("Tie game!");
        }
    }

    public static void main(String[] args) {
        TicTacToe ttt = new TicTacToe();
        System.out.println(new Date());
        ttt.playGame();
        ttt.drawBoard();
    }
}
