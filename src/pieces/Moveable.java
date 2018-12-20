package pieces;

import board.Square;

public interface Moveable {

	void showMove(Square sq);

	void removeMove(Square sq);

	void castleMove();

	boolean castlePossible();

}
