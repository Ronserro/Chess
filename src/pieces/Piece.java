package pieces;

import java.util.ArrayList;

import board.ChessBoard;
import board.Square;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Piece extends Group implements Moveable {

	private static ArrayList<Piece> pieceGraveyard = new ArrayList<Piece>();
	private static King KING_WHITE;
	private static King KING_BLACK;
	protected boolean thisChess;
	private Color color;

	public Piece(int i) {
		thisChess = false;
		String s;
		if (i == 0 || i == 1) {
			color = Color.BLACK;
			s = "black";
		} else {
			color = Color.WHITE;
			s = "white";
		}

		if (this instanceof King) {
			if (s.equals("white")) {
				KING_WHITE = (King) this;
			} else {
				KING_BLACK = (King) this;
			}

		}

		Image image = new Image("/images/" + this.getClass().getSimpleName().toLowerCase() + "_" + s + ".png");

		ImageView iw = new ImageView(image);

		getChildren().add(iw);

	}

	public Color getColor() {
		return this.color;
	}

	public static King getBlackKing() {
		return KING_BLACK;

	}

	public static King getWhiteKing() {
		return KING_WHITE;

	}

	public static void addGraveyard(Piece c) {
		pieceGraveyard.add(c);
	}

	public static void removeGraveyard(Piece c) {
		pieceGraveyard.remove(c);
	}

	public static Piece getGraveyard(int place) {
		return pieceGraveyard.get(place);
	}

	public static int getSizeGraveyard() {
		return pieceGraveyard.size();
	}

	public boolean pieceMakesChess() {
		return thisChess;
	}

	public void notChess() {
		thisChess = false;
	}

	public void chooseGraveyard() {
		for (int i = 0; i < getSizeGraveyard(); i++) {
			if (pieceGraveyard.get(i).getColor() == this.color) {
				ChessBoard.useGraveyard(this);
			}
		}
		Square s = (Square) this.getParent();
		s.removePiece();

	}

}
