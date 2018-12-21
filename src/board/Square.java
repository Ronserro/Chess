package board;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import marker.CastleMarker;
import marker.ChessMove;
import marker.MovementPredict;
import marker.QuitBlock;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Rook;

public class Square extends Group {

	public static final int SIZE = 80;
	private Piece piece;
	private MovementPredict mp;
	private ChessMove ch;
	private QuitBlock qb;
	private CastleMarker cm;
	private Color bgColor;
	private Rectangle sq;
	private int CordX;
	private int CordY;
	private static Square tempSquare;
	private static Color playerTurn;
	private static boolean chessCheck;
	private static boolean isChess;
	private static boolean willSurrender;
	private static Pawn passantPawn;
	public static Piece tempPiece;

	private static ArrayList<MovementPredict> chessPredicts = new ArrayList<MovementPredict>();
	private static ArrayList<Square> tempSquareList = new ArrayList<Square>();

	public Square(Color c, int x, int y) {
		resetVariables();
		CordX = x;
		CordY = y;
		bgColor = c;
		sq = new Rectangle(SIZE, SIZE, c);
		getChildren().add(sq);

		setOnMouseClicked(event -> {
			if (hasPiece() && !willSurrender) {
				if (getParent() instanceof Graveyard) {
					makeGraveyardTurn();
				} else if (!ChessBoard.graveYardExists()) {
					if (hasQuitBlock()) {
						willSurrender = true;
						ChessBoard.checkSurrender();
					}
					if (piece.getColor() == playerTurn) {
						if (hasCastleMarker()) {
							doCastle();
						}
						ChessBoard.removePredictsq(tempSquare);

						if (getBackground().getFill().equals(Color.RED)) {
							getBackground().setFill(bgColor);
						} else {
							if (tempSquare != null) {
								tempSquare.getBackground().setFill(tempSquare.bgColor);
							}

							getBackground().setFill(Color.RED);

							Piece tempPieceForChess = CheckThisPieceChess();

							if (isChess == true && tempPieceForChess != null && tempPiece != null) {
								CalculateThisPieceChess();
							} else {
								ChessBoard.addMovement(this);
								tempSquare = this;
							}

						}

					}
				}
			}

			if (hasPredict() && !willSurrender) {
				try {
					boolean tempChessMarker = false;
					if (hasPiece()) {
						tempChessMarker = getPiece().pieceMakesChess();
						removePiece();
					}
					makeTurn(tempChessMarker);
				} catch (Exception e) {

				}
			}

		});
	}

	private void resetVariables() {
		tempSquare = null;
		chessCheck = false;
		isChess = false;
		willSurrender = false;
		passantPawn = null;
		tempPiece = null;
		chessPredicts.clear();
		tempSquareList.clear();

	}

	private void CalculateThisPieceChess() {
		Square s = (Square) tempPiece.getParent();
		s.getPiece().showMove(s);
		removeAllPredicts(true);
		ChessBoard.addMovement(this);
		isChess = false;
		restoreKingColor();
		tempSquare = this;
		tempSquareListremove();

	}

	private Piece CheckThisPieceChess() {
		Piece tempPieceForChess = null;
		if (isChess == false && chessCheck == false) {
			tempPieceForChess = piece;
			if (!(tempPieceForChess instanceof King)) {
				piece = null;
				turnChessCheck();
				piece = tempPieceForChess;
			} else {
				tempPieceForChess = null;
			}
		}
		return tempPieceForChess;
	}

	private void makeTurn(boolean tempChessMarker) {
		checkIfPassantTurn();
		checkPassant();
		tempSquare.piece.castleMove();
		getChildren().add(tempSquare.piece);
		piece = tempSquare.piece;
		tempSquare.getChildren().remove(tempSquare.piece);
		ChessBoard.removePredictsq(tempSquare);
		tempSquare.getBackground().setFill(tempSquare.bgColor);
		tempSquare.piece = null;
		checkIfGraveyard();
		turnChessCheck();
		checkChessMate();
		checkIfChessMarker(tempChessMarker);
		restoreKingColor();
		madeTurn();
	}

	private void restoreKingColor() {
		if (!isChess) {
			for (int i = 0; i < 2; i++) {
				Square q;
				if (i == 0) {
					q = (Square) Piece.getWhiteKing().getParent();
				} else {
					q = (Square) Piece.getBlackKing().getParent();
				}
				q.getBackground().setFill(q.bgColor);
			}
		}
	}

	private void checkIfChessMarker(boolean tempChessMarker) {
		if (hasChessMarker() || tempChessMarker) {
			isChess = false;
			removeChessMove();
		}

	}

	private void turnChessCheck() {
		for (int i = 0; i < 2; i++) {
			Square q;
			if (i == 0) {
				q = (Square) Piece.getWhiteKing().getParent();
			} else {
				q = (Square) Piece.getBlackKing().getParent();
			}
			addChessCheck(q);
			removeChessCheck();
		}

	}

	public static void ChessMateCheck(Color c) {
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (sq.hasPiece()) {
					if (sq.getPiece().getColor() == c && !(sq.getPiece() instanceof King)) {
						sq.piece.showMove(sq);
					}
				}
			}
		}
	}

	public static boolean ChessMateCheckRemove() {
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (sq.hasPredict() && sq.hasChessMarker()) {
					removeAllPredicts(false);
					return false;
				}
				if (sq.hasPiece()) {
					if (sq.hasPredict()) {
						removeAllPredicts(false);
						return false;
					}
				}
			}
		}
		return true;
	}

	private void checkChessMate() {
		if (isChess) {
			King k = null;
			if (this.getPiece().getColor() == Color.WHITE) {
				k = Piece.getBlackKing();
			} else {
				k = Piece.getWhiteKing();
			}
			Square s = (Square) k.getParent();
			k.showMove(s);
			k.removeMove(s);
			if (k.chessMate()) {
				Color c = s.getPiece().getColor();
				ChessMateCheck(c);
				if (ChessMateCheckRemove()) {
					madeTurn();
					ChessBoard.gameOver();
				}
			}
			k.changeChess();
		}

	}

	private void checkIfPassantTurn() {
		if (tempSquare.hasPiece()) {
			if (tempSquare.piece instanceof Pawn) {
				Pawn p = (Pawn) tempSquare.getPiece();
				if (p.movePassant()) {
					if (!hasPiece()) {
						for (int i = -1; i < 3; i += 2) {
							Square s = ChessBoard.getSquare(CordX, CordY + i);
							if (s.hasPiece()) {
								if (s.passantPredict()) {
									getChildren().add(tempSquare.piece);
									piece = tempSquare.piece;
									s.getChildren().remove(s.piece);
									ChessBoard.removePredictsq(tempSquare);
									tempSquare.getBackground().setFill(tempSquare.bgColor);
									s.piece = null;
									madeTurn();
								}
							}
						}
					}
				}
			}
		}
	}

	private void checkPassant() {
		if (passantPawn != null) {
			passantPawn.notPassant();
			passantPawn = null;
		}
		if (tempSquare.piece instanceof Pawn) {
			if (tempSquare.CordY - CordY == 2 || tempSquare.CordY - CordY == -2) {
				passantPawn = (Pawn) tempSquare.getPiece();
				passantPawn.isPassant();
			}
		}

	}

	public boolean passantPredict() {
		if (hasPiece()) {
			if (getPiece() instanceof Pawn) {
				Pawn temp = (Pawn) getPiece();
				if (temp.canPassant()) {
					return true;
				}
			}
		}
		return false;
	}

	private void checkIfGraveyard() {
		if (getPiece() instanceof Pawn && (CordY == 0 || CordY == 7)) {
			getPiece().chooseGraveyard();
		}
	}

	private void makeGraveyardTurn() {
		tempSquare.getChildren().remove(tempSquare.getPiece());
		tempSquare.getChildren().add(getPiece());
		tempSquare.piece = getPiece();
		getChildren().remove(getPiece());
		piece = null;
		ChessBoard.removeGraveyard();
	}

	private void madeTurn() {
		if (playerTurn == Color.WHITE) {
			playerTurn = Color.BLACK;
		} else {
			playerTurn = Color.WHITE;
		}
	}

	public void addPiece(Piece p) {
		piece = p;
		getChildren().add(p);
		p.setTranslateX(SIZE / 8);
		p.setTranslateY(SIZE / 8);
	}

	public void addPredict() {
		if (!hasPredict() && !ChessBoard.graveYardExists()) {
			mp = new MovementPredict();
			getChildren().add(mp);
			if (chessCheck) {
				chessPredicts.add(mp);
			}

		}
	}

	public void addQuitBlock() {
		if (!hasQuitBlock() && !ChessBoard.graveYardExists()) {
			qb = new QuitBlock();
			getChildren().add(qb);

		}

	}

	public void addChessMove() {
		if (!hasChessMarker() && isChessNow()) {
			ch = new ChessMove();
			getChildren().add(ch);
		}
	}

	public void addCastleMarker() {
		if (!hasCastleMarker() && !isChessNow()) {
			cm = new CastleMarker();
			getChildren().add(cm);
		}
	}

	public static void removeChessMove() {
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (sq.hasChessMarker()) {
					sq.removeChessMarker();
				}
				if (sq.hasPiece()) {
					sq.piece.notChess();
				}
			}
		}
	}

	public void removeChessMarker() {
		getChildren().remove(ch);
		ch = null;
	}

	public void removePredict() {
		getChildren().remove(mp);
		mp = null;
	}

	public void removeQuitBlock() {
		getChildren().remove(qb);
		qb = null;
	}

	public void removeCastleMarker() {
		getChildren().remove(cm);
		cm = null;
	}

	public void removePiece() {
		if (!(getPiece() instanceof Pawn)) {
			Piece.addGraveyard(piece);
		}
		getChildren().remove(piece);
		piece = null;
	}

	public boolean hasPiece() {
		return piece != null;
	}

	public boolean hasPredict() {
		return mp != null;
	}

	public boolean hasChessMarker() {
		return ch != null;
	}

	private boolean hasQuitBlock() {
		return qb != null;
	}

	private boolean hasCastleMarker() {
		return cm != null;
	}

	public static boolean isChessNow() {
		return isChess;
	}

	public static void noSurrender() {
		willSurrender = false;
	}

	public void itsChessNow() {
		isChess = true;
		getBackground().setFill(Color.YELLOW);
	}

	public static boolean chessCheck() {
		return chessCheck;
	}

	public boolean isKingHere() {
		if (piece instanceof King) {
			return true;
		}
		return false;
	}

	public Rectangle getBackground() {
		return sq;
	}

	public int getCordX() {
		return CordX;
	}

	public int getCordY() {
		return CordY;
	}

	public static void startGame() {
		playerTurn = Color.WHITE;
	}

	public Piece getPiece() {
		return piece;
	}

	public static String playerTurn() {
		if (playerTurn == Color.WHITE) {
			return "black";
		} else {
			return "white";
		}

	}

	public boolean OppositeColor(Piece p) {
		if (piece.getColor() != p.getColor()) {
			return true;
		} else {
			return false;
		}
	}

	public void canCastle(King k) {
		Square sq = null;
		boolean beAdded = true;
		for (int j = 1; j < 4; j++) {
			sq = ChessBoard.getSquare(getCordX() + j, getCordY());

			if (sq.hasPiece()) {
				if (sq.getCordX() != 7) {
					beAdded = false;
				} else if (sq.getPiece() instanceof Rook) {
					Rook r = (Rook) sq.getPiece();
					if (!r.castlePossible()) {
						beAdded = false;
					}
				}

			}
		}
		
		if (beAdded) {
			sq.addCastleMarker();
		}

		beAdded = true;
		for (int i = 1; i < 5; i++) {
			sq = ChessBoard.getSquare(getCordX() - i, getCordY());
			if (sq.hasPiece()) {
				if (sq.getCordX() != 0) {
					beAdded = false;
				} else if (sq.getPiece() instanceof Rook) {
					Rook r = (Rook) sq.getPiece();
					if (!r.castlePossible()) {
						beAdded = false;
					}
				}

			}
		}

		if (beAdded) {
			sq.addCastleMarker();
		}

	}

	private void doCastle() {
		Square king;
		Square rook;
		if (getCordX() == 0) {
			king = ChessBoard.getSquare(tempSquare.getCordX() - 2, tempSquare.getCordY());
			rook = ChessBoard.getSquare(getCordX() + 3, getCordY());
		} else {
			king = ChessBoard.getSquare(tempSquare.getCordX() + 2, tempSquare.getCordY());
			rook = ChessBoard.getSquare(getCordX() - 2, getCordY());
		}
		castleMove(king, rook);
	}

	private void castleMove(Square king, Square rook) {
		ChessBoard.removePredictsq(tempSquare);
		getBackground().setFill(Color.RED);
		tempSquare.getBackground().setFill(tempSquare.bgColor);
		king.getChildren().add(tempSquare.piece);
		king.piece = tempSquare.piece;
		tempSquare.getChildren().remove(tempSquare.piece);
		tempSquare.piece = null;
		rook.getChildren().add(piece);
		rook.piece = piece;
		getChildren().remove(piece);
		piece = null;
		madeTurn();
	}

	public static void addGraveyard(Piece p) {
		tempSquare = (Square) p.getParent();
		tempSquare.piece = p;
	}

	public static void addChessCheck(Square s) {
		chessCheck = true;
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (sq.hasPiece()) {
					if (sq.OppositeColor(s.getPiece())) {
						sq.piece.showMove(sq);
					}
				}
				if (s.hasPredict() && s.piece instanceof King) {
					s.getBackground().setFill(Color.YELLOW);
					isChess = true;
				}
			}
		}
		chessCheck = false;
	}

	public static void removeChessCheck() {
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (chessPredicts.contains(sq.mp)) {
					sq.removePredict();
				}
			}
		}
		chessPredicts.clear();
	}

	public static void removeAllPredicts(boolean chessMarkRemove) {
		for (int x = 0; x < ChessBoard.getSize(); x++) {
			for (int y = 0; y < ChessBoard.getSize(); y++) {
				Square sq = ChessBoard.getSquare(x, y);
				if (sq.hasPredict()) {
					tempSquareList.add(sq);
					sq.removePredict();
				}
				if (sq.hasChessMarker() && chessMarkRemove) {
					sq.removeChessMarker();
				}
			}
		}

	}

	public boolean tempSquareListContains() {
		if (tempSquareList.contains(this)) {
			return true;
		} else {
			return false;
		}

	}

	public void tempSquareListremove() {
		tempSquareList.clear();
	}

}
