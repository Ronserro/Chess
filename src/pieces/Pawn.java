package pieces;

import board.ChessBoard;
import board.Square;
import javafx.scene.paint.Color;

public class Pawn extends Piece {

	boolean enPassant;
	boolean canBeEnPassant;

	public Pawn(int i) {

		super(i);

	}

	@Override
	public void showMove(Square sq) {
		canBeEnPassant = false;
		Square s = null;
		if (getColor() == Color.WHITE) {
			for (int i = 0; i < 4; i++) {
				try {
					if (i == 0) {
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 1);
					} else if (i == 1) {
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 1);
					} else if (i == 2) { // Passant
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY());
						if (s.hasPiece()) {
							if (s.OppositeColor(this) && s.passantPredict() && !Square.isChessNow()) {
								canBeEnPassant = true;
								s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 1);
								s.addPredict();
							}
						}
						s = null;
					} else { // Passant
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY());
						if (s.hasPiece() && !Square.isChessNow()) {
							if (s.OppositeColor(this) && s.passantPredict()) {
								canBeEnPassant = true;
								s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 1);
								s.addPredict();
							}
						}
						s = null;
					}

					if (Square.chessCheck()) {

						s.addPredict();

					} else if (s.hasPiece()) {
						if (s.OppositeColor(this) && !Square.isChessNow() || s.getPiece().pieceMakesChess()) {
							s.addPredict();
						}
					}

				} catch (Exception e) {
				}
			}

			s = null;
			if (sq.getCordY() == 6) {
				for (int i = 1; i <= 2; i++) {
					try {
						s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - i);
						if (!s.hasPiece()) {
							if (!Square.isChessNow() || s.hasChessMarker()) {
								s.addPredict();
							} else if (s.tempSquareListContains()) {
								s.addPredict();
							}
						} else {
							break;
						}
					} catch (Exception e) {
					}

				}
			} else {
				s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - 1);
				if (!s.hasPiece() && !Square.isChessNow() && !Square.chessCheck()) {
					s.addPredict();
				}

			}
		} else

		{ // BLACK
			for (int i = 0; i < 4; i++) {
				try {
					if (i == 0) {
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 1);
					} else if (i == 1) {
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 1);
					} else if (i == 2) { // Passant
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY());
						if (s.hasPiece()) {
							if (s.OppositeColor(this) && s.passantPredict() && !Square.isChessNow()) {
								canBeEnPassant = true;
								s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 1);
								s.addPredict();
							}
						}
						s = null;
					} else { // Passant
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY());
						if (s.hasPiece() && !Square.isChessNow()) {
							if (s.OppositeColor(this) && s.passantPredict()) {
								canBeEnPassant = true;
								s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 1);
								s.addPredict();
							}
						}
						s = null;
					}
					if (Square.chessCheck()) {
						s.addPredict();
					} else if (s.hasPiece()) {
						if (s.OppositeColor(this) && !Square.isChessNow() || s.getPiece().pieceMakesChess()) {
							s.addPredict();
						}
					}
				} catch (Exception e) {
				}
			}

			s = null;
			if (sq.getCordY() == 1) {
				for (int i = 1; i <= 2; i++) {
					try {
						s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + i);
						if (!s.hasPiece()) {
							if (!Square.isChessNow() || s.hasChessMarker()) {
								s.addPredict();
							} else if (s.tempSquareListContains()) {

								s.addPredict();
							}
						} else {
							break;
						}
					} catch (Exception e) {
					}

				}
			} else {
				s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + 1);
				if (!s.hasPiece() && !Square.isChessNow() && !Square.chessCheck()) {
					s.addPredict();
				}

			}
		}

	}

	@Override
	public void removeMove(Square sq) {
		if (this.getColor() == Color.WHITE) {
			Square s = null;
			for (int i = 0; i < 4; i++) {
				try {
					if (i == 0) {
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 1);
					} else if (i == 1) {
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 1);
					}
					s.removePredict();
				} catch (Exception e) {
				}
			}

			s = null;
			if (sq.getCordY() == 6) {
				for (int i = 1; i <= 2; i++) {
					try {
						s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - i);
						s.removePredict();
					} catch (Exception e) {
						break;
					}
				}
			} else {
				s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - 1);
				s.removePredict();
			}
		} else { // BLACK
			Square s = null;
			for (int i = 0; i < 2; i++) {
				try {
					if (i == 0) {
						s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 1);
					} else {
						s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 1);
					}
					s.removePredict();
				} catch (Exception e) {
				}
			}

			s = null;
			if (sq.getCordY() == 1) {
				for (int i = 1; i <= 2; i++) {
					try {
						s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + i);
						s.removePredict();
					} catch (Exception e) {
						break;
					}
				}
			} else {
				s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + 1);
				s.removePredict();
			}
		}
	}

	@Override
	public void castleMove() {

	}

	@Override
	public boolean castlePossible() {
		return false;

	}

	public void isPassant() {
		enPassant = true;

	}

	public void notPassant() {
		enPassant = false;

	}

	public boolean canPassant() {
		return enPassant;
	}

	public boolean movePassant() {
		return canBeEnPassant;
	}
}
