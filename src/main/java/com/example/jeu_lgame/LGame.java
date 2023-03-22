package com.example.jeu_lgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LGame extends Application {

    private Board board;
    private StackPane[][] squares;
    private Piece selectedPiece;
    private List<StackPane> legalMoves;
    private Label currentPlayerLabel;

    @Override
    public void start(Stage stage) throws Exception {
        board = new Board();
        squares = new StackPane[4][4];
        legalMoves = new ArrayList<>();


        currentPlayerLabel = new Label("Joueur actuel: " + board.getCurrentPlayer());
        currentPlayerLabel.setAlignment(Pos.CENTER);
        currentPlayerLabel.setPadding(new Insets(10));

        Button newGameButton = new Button("Nouvelle partie");
        newGameButton.setOnAction(e -> {
            board.reset();
            resetBoard();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                StackPane square = new StackPane();
                square.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #FFFF00; -fx-border-width: 1px;");
                square.setPrefSize(50, 50);
                int finalRow = row;
                int finalCol = col;
                square.setOnMouseClicked(e -> handleSquareClicked(square, finalRow, finalCol));
                squares[row][col] = square;
                gridPane.add(square, col, row);
            }
        }

        VBox root = new VBox(gridPane, currentPlayerLabel, newGameButton);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        resetBoard();
    }

    private void resetBoard() {
        selectedPiece = null;
        legalMoves.clear();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Piece piece = board.getPiece(row, col);
                StackPane square = squares[row][col];
                square.getChildren().clear();
                if (piece != null) {
                    square.getChildren().add(piece.getView());
                    piece.setRow(row);
                    piece.setCol(col);
                }
                square.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 1px;");
            }
        }
        currentPlayerLabel.setText("Joueur actuel: " + board.getCurrentPlayer());
    }

    private void handleSquareClicked(StackPane square, int row, int col) {
        if (selectedPiece == null) {
            Piece piece = board.getPiece(row, col);
            if (piece != null && piece.getPlayer() == board.getCurrentPlayer()) {
                selectedPiece = piece;
                square.setStyle("-fx-background-color: #FF0000; -fx-border-color: #FFFF00; -fx-border-width: 1px;");
                highlightLegalMoves();
            }
        } else {
            if (legalMoves.contains(square)) {
                board.movePiece(selectedPiece, row, col);
                resetBoard();
                checkForWinner();
            } else {
                selectedPiece = null;
                resetBoard();
            }
        }
    }

    private void highlightLegalMoves() {
        legalMoves.clear();
        List<int[]> moves = board.getLegalMoves(selectedPiece);
        for (int[] move : moves) {
            int row = move[0];
            int col = move[1];
            StackPane square = squares[row][col];
            square.setStyle("-fx-background-color: #08ff00; -fx-border-color: #000000; -fx-border-width: 1px;");
            legalMoves.add(square);
        }
    }

    private void checkForWinner() {
        if (board.getCurrentPlayer() == null) {
            String winner = String.valueOf(board.getWinner());
            showMessage("Le joueur " + winner + " a gagné !");
            resetBoard();
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



