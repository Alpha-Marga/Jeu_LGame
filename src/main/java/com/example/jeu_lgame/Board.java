package com.example.jeu_lgame;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] board;
    private Player currentPlayer;

    public Board() {
        board = new Piece[4][4];
        reset();
    }

    public void reset() {
        currentPlayer = Player.RED;

        board = new Piece[4][4];
        board[0][0] = new Piece(Player.RED, PieceType.L);
        board[3][3] = new Piece(Player.BLUE, PieceType.L);
        board[1][2] = new Piece(null, PieceType.PAWN);
        board[2][1] = new Piece(null, PieceType.PAWN);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void movePiece(Piece piece, int newRow, int newCol) {
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();

        board[oldRow][oldCol] = null;
        board[newRow][newCol] = piece;
        piece.setRow(newRow);
        piece.setCol(newCol);

        currentPlayer = (currentPlayer == Player.RED) ? Player.BLUE : Player.RED;
    }

    public List<int[]> getLegalMoves(Piece piece) {
        List<int[]> legalMoves = new ArrayList<>();
        if (piece.getType() == PieceType.L) {
            addLMoves(legalMoves, piece, -2, -1);
            addLMoves(legalMoves, piece, -2, 1);
            addLMoves(legalMoves, piece, -1, -2);
            addLMoves(legalMoves, piece, -1, 2);
            addLMoves(legalMoves, piece, 1, -2);
            addLMoves(legalMoves, piece, 1, 2);
            addLMoves(legalMoves, piece, 2, -1);
            addLMoves(legalMoves, piece, 2, 1);
        } else {
            addPawnMoves(legalMoves, piece, -1, 0);
            addPawnMoves(legalMoves, piece, 0, -1);
            addPawnMoves(legalMoves, piece, 0, 1);
            addPawnMoves(legalMoves, piece, 1, 0);
        }
        return legalMoves;
    }

    private void addLMoves(List<int[]> legalMoves, Piece piece, int rowOffset, int colOffset) {
        int newRow = piece.getRow() + rowOffset;
        int newCol = piece.getCol() + colOffset;
        if (newRow >= 0 && newRow < 4 && newCol >= 0 && newCol < 4) {
            Piece targetPiece = board[newRow][newCol];
            if (targetPiece == null || targetPiece.getPlayer() != piece.getPlayer()) {
                legalMoves.add(new int[]{newRow, newCol});
            }
        }
    }

    private void addPawnMoves(List<int[]> legalMoves, Piece piece, int rowOffset, int colOffset) {
        int newRow = piece.getRow() + rowOffset;
        int newCol = piece.getCol() + colOffset;
        if (newRow >= 0 && newRow < 4 && newCol >= 0 && newCol < 4) {
            Piece targetPiece = board[newRow][newCol];
            if (targetPiece == null || targetPiece.getPlayer() != piece.getPlayer()) {
                legalMoves.add(new int[]{newRow, newCol});
            }
        }
    }

    public Player getWinner() {
        return (currentPlayer == Player.RED) ? Player.BLUE : Player.RED;
    }
}

