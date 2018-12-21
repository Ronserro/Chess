package pieces;

import board.ChessBoard;
import board.Square;

public class King extends Piece {

	boolean hasMoved;

	boolean checkChessMate;

	public King(int i) {

		super(i);

		hasMoved = false;
		checkChessMate = true;
	}

	@Override
	public void showMove(Square sq) {
		for (int i = 1; i <= ChessBoard.getSize() + 1; i++) {
			try {
				Square s = null;
				switch (i) {
				case 1:
					s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - 1);
					break;

				case 2:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 1);
					break;

				case 3:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 1);
					break;

				case 4:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY());
					break;

				case 5:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY());
					break;

				case 6:
					s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + 1);
					break;

				case 7:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 1);
					break;

				case 8:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 1);
					break;

				case 9:
					if (!Square.chessCheck()) {
						s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY());
						if (castlePossible()) {
							s.canCastle(this);
						}
						s.addQuitBlock();
						s = null;
					}
					break;

				default:
					break;
				}

				if (s.hasPiece()) {
					if (s.OppositeColor(this)) {
						s.addPredict();
					}

				} else {
					s.addPredict();
				}
			} catch (Exception e) {

			}

		}

	}

	@Override
	public void removeMove(Square sq) {
		for (int i = 1; i <= ChessBoard.getSize() + 3; i++) {
			try {
				Square s = null;
				switch (i) {
				case 1:
					s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() - 1);
					break;

				case 2:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 1);
					break;

				case 3:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 1);
					break;

				case 4:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY());
					break;

				case 5:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY());
					break;

				case 6:
					s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY() + 1);
					break;

				case 7:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 1);
					break;

				case 8:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 1);
					break;

				case 9:
					s = ChessBoard.getSquare(0, sq.getCordY());
					s.removeCastleMarker();
					s = null;
					break;

				case 10:
					s = ChessBoard.getSquare(7, sq.getCordY());
					s.removeCastleMarker();
					s = null;
					break;

				case 11:
					s = ChessBoard.getSquare(sq.getCordX(), sq.getCordY());
					s.removeQuitBlock();
					break;

				default:
					break;
				}

				if (s.hasPredict() && !s.hasChessMarker()) {
					checkChessMate = false;
				}
				s.removePredict();
			} catch (Exception e) {

			}

		}

	}

	@Override
	public void castleMove() {
		hasMoved = true;
	}

	public boolean castlePossible() {
		return !hasMoved;
	}

	public boolean chessMate() {
		return checkChessMate;
	}

	public void changeChess() {
		checkChessMate = true;
	}

}
