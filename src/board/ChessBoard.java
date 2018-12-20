package board;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class ChessBoard extends Group {

	private static ArrayList<ArrayList<Square>> square = new ArrayList<ArrayList<Square>>();
	private static ChessBoard chess;
	private static Graveyard grave;

	public ChessBoard() {
		square.clear();
		chess = null;
		grave = null;
		for (int row = 0; row < 8; row++) {
			square.add(new ArrayList<Square>());
			for (int col = 0; col < 8; col++) {

				Square r;

				if ((row + col) % 2 == 0) {
					r = new Square(Color.WHEAT, col, row);
				} else {
					r = new Square(Color.SIENNA, col, row);
				}

				if (row == 1 || row == 6) {
					Pawn p = new Pawn(row);
					r.addPiece(p);
				}

				if ((row == 0 || row == 7) && (col == 0 || col == 7)) {
					Rook p = new Rook(row);
					r.addPiece(p);
				}

				if ((row == 0 || row == 7) && (col == 1 || col == 6)) {
					Knight p = new Knight(row);
					r.addPiece(p);
				}

				if ((row == 0 || row == 7) && (col == 2 || col == 5)) {
					Bishop p = new Bishop(row);
					r.addPiece(p);
				}

				if ((row == 0 || row == 7) && col == 3) {
					Queen p = new Queen(row);
					r.addPiece(p);
				}

				if ((row == 0 || row == 7) && col == 4) {
					King p = new King(row);
					r.addPiece(p);
				}

				square.get(row).add(r);

				r.setTranslateX(col * Square.SIZE);
				r.setTranslateY(row * Square.SIZE);
				getChildren().add(r);
			}

		}
		chess = this;
		startGame();
	}

	private void startGame() {
		Square.startGame();
	}

	public static Square getSquare(int x, int y) {
		return square.get(y).get(x);
	}

	public static int getSize() {
		return square.size();
	}

	public static void addMovement(Square sq) {
		try {
			Piece c = sq.getPiece();
			if (c instanceof King) {
				Square.addChessCheck(sq);
			}
			c.showMove(sq);
			if (c instanceof King) {
				Square.removeChessCheck();
			}
		} catch (Exception e) {
			return;
		}

	}

	public static void removePredictsq(Square sq) {
		try {
			Piece c = sq.getPiece();
			c.removeMove(sq);
		} catch (Exception e) {
			return;
		}
	}

	public static void useGraveyard(Piece c) {
		grave = new Graveyard(c);
		chess.getChildren().add(grave);
	}

	public static void removeGraveyard() {
		chess.getChildren().remove(grave);
	}

	public static boolean graveYardExists() {
		if (chess.getChildren().contains(grave)) {
			return true;
		} else {
			return false;
		}

	}

	public static void checkSurrender() {
		Button SurrenderCheck = new Button("I give up");
		Button NoSurrender = new Button("continue");

		SurrenderCheck.setPrefWidth(120);
		SurrenderCheck.setPrefHeight(50);
		SurrenderCheck.setLayoutX((Square.SIZE * getSize()) / 2 + 10);
		SurrenderCheck.setLayoutY((Square.SIZE * getSize()) / 2 - (SurrenderCheck.getPrefHeight() / 2));
		SurrenderCheck.setFont(Font.font("Verdana", 18));

		NoSurrender.setPrefWidth(120);
		NoSurrender.setPrefHeight(50);
		NoSurrender.setLayoutX((Square.SIZE * getSize()) / 2 - 130);
		NoSurrender.setLayoutY((Square.SIZE * getSize()) / 2 - (NoSurrender.getPrefHeight() / 2));
		NoSurrender.setFont(Font.font("Verdana", 18));
		chess.getChildren().addAll(SurrenderCheck, NoSurrender);

		NoSurrender.setOnAction(event -> {
			removeButtons(SurrenderCheck, NoSurrender);
			Square.noSurrender();
		});

		SurrenderCheck.setOnAction(event -> {
			removeButtons(SurrenderCheck, NoSurrender);
			gameOver();
		});

	}

	public static void gameOver() {
		Text t = new Text("Player " + Square.playerTurn() + " has won");
		t.setFont(Font.font("Verdana", 30));
		t.setFill(Color.RED);
		t.setLayoutX((Square.SIZE * getSize()) / 2 - 160);
		t.setLayoutY((Square.SIZE * getSize()) / 2);
		chess.getChildren().add(t);
		newGameButton();
	}

	private static void newGameButton() {
		Button WantNewGame = new Button("New Game");

		WantNewGame.setPrefWidth(120);
		WantNewGame.setPrefHeight(50);
		WantNewGame.setLayoutX((Square.SIZE * getSize()) / 2 - (WantNewGame.getPrefWidth() / 2));
		WantNewGame.setLayoutY((Square.SIZE * getSize()) / 2 + (WantNewGame.getPrefHeight() / 2));
		WantNewGame.setFont(Font.font("Verdana", 15));
		chess.getChildren().add(WantNewGame);

		WantNewGame.setOnAction(event -> {
			newGame();
		});

	}

	private static void newGame() {
		ChessGame.MakeNewGame();
	}

	private static void removeButtons(Button surrenderCheck, Button noSurrender) {
		chess.getChildren().removeAll(surrenderCheck, noSurrender);

	}

}
