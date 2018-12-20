package board;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import pieces.Piece;

public class Graveyard extends Group {

	public Graveyard(Piece c) {
		int place = 0;
		for (int row = 3; row < 5; row++) {
			for (int col = 2; col < 6; col++) {
				Square g = new Square(Color.YELLOW, row, col);
				while (Piece.getSizeGraveyard() > place) {
					Piece p = Piece.getGraveyard(place);
					if (p.getColor() == c.getColor()) {
						g.addPiece(p);
						break;
					} else {
						place++;
					}
				}
				g.setTranslateX(col * Square.SIZE);
				g.setTranslateY(row * Square.SIZE);
				place++;
				this.getChildren().add(g);
			}
		}
		Square.addGraveyard(c);
	}

}
