package marker;

import board.Square;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MovementPredict extends MarkerCircle {

	public MovementPredict() {
		Circle c = new Circle(MarkerCircle.SIZE, Color.ORANGE);
		this.setTranslateX(Square.SIZE / 2);
		this.setTranslateY(Square.SIZE / 2);
		this.getChildren().add(c);
	}

}
