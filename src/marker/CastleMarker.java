package marker;

import board.Square;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CastleMarker extends MarkerCircle {
	
	public CastleMarker() {
		Circle c = new Circle(MarkerCircle.SIZE, Color.BLUE);
		this.setTranslateX(Square.SIZE / 2);
		this.setTranslateY(Square.SIZE / 2);
		this.getChildren().add(c);
	}
}
