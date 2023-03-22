package com.example.jeu_lgame;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piece {
    private Player player;
    private PieceType type;
    private int row;
    private int col;
    private Rectangle view;

    public Piece(Player player, PieceType type) {
        this.player = player;
        this.type = type;
        this.view = new Rectangle(40, 40);
        if (type == PieceType.L) {
            view.setFill((player == Player.RED) ? Color.RED : Color.BLUE);
        } else {
            view.setFill(Color.BLACK);
        }
        view.setStroke(Color.GRAY);
        view.setStrokeWidth(2);
    }

    public Player getPlayer() {
        return player;
    }

    public PieceType getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Rectangle getView() {
        return view;
    }

}
