package marker;

import board.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessMove extends Group {

	public ChessMove() {

		Rectangle r = new Rectangle(20, 20, Color.ORANGE);
		this.setTranslateX(Square.SIZE / 2.6);
		this.setTranslateY(Square.SIZE / 2.6);
		this.getChildren().add(r);
	}

}
