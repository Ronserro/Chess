package pieces;

import board.ChessBoard;
import board.Square;

public class Knight extends Piece {

	public Knight(int i) {

		super(i);

	}

	@Override
	public void showMove(Square sq) {
		for (int i = 1; i <= 8; i++) {
			try {
				Square s = null;
				switch (i) {
				case 1:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 2);
					break;

				case 2:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 2);
					break;

				case 3:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 2);
					break;

				case 4:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 2);
					break;

				case 5:
					s = ChessBoard.getSquare(sq.getCordX() - 2, sq.getCordY() - 1);
					break;

				case 6:
					s = ChessBoard.getSquare(sq.getCordX() - 2, sq.getCordY() + 1);
					break;

				case 7:
					s = ChessBoard.getSquare(sq.getCordX() + 2, sq.getCordY() - 1);
					break;

				case 8:
					s = ChessBoard.getSquare(sq.getCordX() + 2, sq.getCordY() + 1);
					break;

				default:
					break;
				}

				if (thisChess && s.isKingHere()) {
					s.addChessMove();
				}

				if (Square.chessCheck() && !thisChess) {
					if (s.isKingHere() && s.OppositeColor(this)) {
						s.itsChessNow();
						thisChess = true;
						Square.tempPiece = this;
						this.showMove(sq);
					}
				}

				if (s.hasPiece()) {
					if ((s.OppositeColor(this) && !Square.isChessNow() || Square.chessCheck())
							|| s.getPiece().pieceMakesChess()) {
						s.addPredict();
					}
				} else if (!Square.isChessNow()) {
					s.addPredict();
				} else if (Square.isChessNow()) {
					
					if ((s.hasChessMarker() && !s.isKingHere())) {
						s.addPredict();
					}
					
					if (s.tempSquareListContains()) {
						s.addPredict();
					}
				}

			} catch (Exception e) {

			}
		}
	}

	@Override
	public void removeMove(Square sq) {
		for (int i = 1; i <= 8; i++) {
			try {
				Square s = null;
				switch (i) {
				case 1:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() - 2);
					break;

				case 2:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() - 2);
					break;

				case 3:
					s = ChessBoard.getSquare(sq.getCordX() + 1, sq.getCordY() + 2);
					break;

				case 4:
					s = ChessBoard.getSquare(sq.getCordX() - 1, sq.getCordY() + 2);
					break;

				case 5:
					s = ChessBoard.getSquare(sq.getCordX() - 2, sq.getCordY() - 1);
					break;

				case 6:
					s = ChessBoard.getSquare(sq.getCordX() - 2, sq.getCordY() + 1);
					break;

				case 7:
					s = ChessBoard.getSquare(sq.getCordX() + 2, sq.getCordY() - 1);
					break;

				case 8:
					s = ChessBoard.getSquare(sq.getCordX() + 2, sq.getCordY() + 1);
					break;

				default:
					break;
				}
				s.removePredict();
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void castleMove() {

	}


}
