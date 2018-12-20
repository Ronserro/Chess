package marker;

import board.Square;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class QuitBlock extends MarkerCircle {

	public QuitBlock() {
		Circle c = new Circle(MarkerCircle.SIZE, Color.RED);
		this.setTranslateX(Square.SIZE / 2);
		this.setTranslateY(Square.SIZE / 2);
		this.getChildren().add(c);
	}

}
