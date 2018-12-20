package pieces;

import board.ChessBoard;
import board.Square;

public class Bishop extends Piece {

	int chessLine;

	public Bishop(int i) {

		super(i);

	}

	@Override
	public void showMove(Square sq) {
		for (int j = 1; j <= 4; j++) {
			loop1: for (int i = 1; i < ChessBoard.getSize(); i++) {
				try {
					Square s = null;
					switch (j) {
					case 1:
						s = ChessBoard.getSquare(sq.getCordX() + i, sq.getCordY() - i);
						break;

					case 2:
						s = ChessBoard.getSquare(sq.getCordX() - i, sq.getCordY() + i);
						break;

					case 3:
						s = ChessBoard.getSquare(sq.getCordX() - i, sq.getCordY() - i);
						break;

					case 4:
						s = ChessBoard.getSquare(sq.getCordX() + i, sq.getCordY() + i);
						break;

					default:
						break;
					}

					if (Square.chessCheck() && !thisChess) {
						if (s.isKingHere() && s.OppositeColor(this)) {
							s.itsChessNow();
							chessLine = j;
							thisChess = true;
							Square.tempPiece = this;
							this.showMove(sq);
						}
					}

					if (thisChess && j == chessLine) {
						s.addChessMove();
					}

					if (s.hasPiece()) {
						if ((s.OppositeColor(this) && !Square.isChessNow() || Square.chessCheck())
								|| s.getPiece().pieceMakesChess()) {
							s.addPredict();
						}
						break loop1;
					} else if (!Square.isChessNow()) {
						s.addPredict();
					} else if (Square.isChessNow()) {
						if (s.hasChessMarker() && !s.isKingHere()) {
							s.addPredict();
						}

						if (s.tempSquareListContains()) {
							s.addPredict();
						}
					}

				} catch (Exception e) {
					break loop1;
				}
			}
		}
	}

	@Override
	public void removeMove(Square sq) {
		for (int j = 1; j <= 4; j++) {
			loop1: for (int i = 1; i < ChessBoard.getSize(); i++) {
				try {
					Square s = null;
					switch (j) {
					case 1:
						s = ChessBoard.getSquare(sq.getCordX() + i, sq.getCordY() - i);
						break;

					case 2:
						s = ChessBoard.getSquare(sq.getCordX() - i, sq.getCordY() + i);
						break;

					case 3:
						s = ChessBoard.getSquare(sq.getCordX() - i, sq.getCordY() - i);
						break;

					case 4:
						s = ChessBoard.getSquare(sq.getCordX() + i, sq.getCordY() + i);
						break;

					default:
						break;
					}
					if (s.hasPiece()) {
						s.removePredict();
						if (s.OppositeColor(this)) {
							break loop1;
						}
					}
					s.removePredict();
				} catch (Exception e) {
					break;
				}
			}
		}
	}

	@Override
	public void castleMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean castlePossible() {
		return false;
		// TODO Auto-generated method stub

	}

}
