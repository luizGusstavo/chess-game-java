package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8); // INICIA UM TABULEIRO
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // FUNCAO RESPONSAVEL POR INICIAR A PARTIDA DE XADREZ
	}
	
	public int getTurn() {
		return turn;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	// ELE PEGA PECAS DE CHADREZ (CHESSPIECES) E N UMA PECA QUALQUER
	// POR ISSO ELE E DO TIPO CHESSPIECES
	public ChessPiece[][] getPieces(){
		//INSTANCIA UMA MATRIZ DE PECAS DE XADREZ
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++) { // LE A LINHA DA MATRIZ
			for(int j = 0; j < board.getColumns(); j++) { // LE A COLUNA DA MATRIZ
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;	
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	// SOURCE - LOCAL DE ORIGEM DA PECA
	// TARGET - LUGAR DE DESTINO DA PECA
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // converte uma posicao em xadrez para matriz, com o metodo toPosition
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); // VERIFICA SE EXISTE UMA PECA NO LOGAL DE ORIGEM
		validateTargetPosition(source, target);
		
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check.");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target); // PEGA A PECA QUE SE MOVEU PARA A POSICAO
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		// # SpecialMove En Passant
		if (movedPiece instanceof Pawn && target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		// RETORNA A PECA CAPTURADA EM FORMATO DE PECA DE XADREZ
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source); // CAPTURA A PECA DO LOCAL DE ORIGEM
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); // CAPTURA A PECA DO LOCAL DE DESTINO (SE HOUVER PECA)
		board.placePiece(p, target); // COLOCA A PECA P NO LOCAL DE DESTINO
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// #specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) { // Se a posicao de destino for igual a posicao de origem + 2
			Position sourceT =  new Position(source.getRow(), source.getColumn() + 3);
			Position targetT =  new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// #specialmove castling queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// #specialmove en passant
		if(p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
					if(p.getColor() == Color.WHITE) {
						pawnPosition = new Position(target.getRow() + 1, target.getColumn());
					}
					else {
						pawnPosition = new Position(target.getRow() - 1, target.getColumn());
					}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece; // RETORNA A PECA QUE FOI CAPTURADA ( SE HOUVER)
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target); // REMOVA A PESA DA POSICAO DE DESTINO
		p.decreaseMoveCount();
		board.placePiece(p, source); // COLOQUE A POSICAO DE VOTA A SUA POSICAO DE ORIGEM
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// #specialmove castling kingside rook
				if(p instanceof King && target.getColumn() == source.getColumn() + 2) { // Se a posicao de destino for igual a posicao de origem + 2
					Position sourceT =  new Position(source.getRow(), source.getColumn() + 3);
					Position targetT =  new Position(source.getRow(), source.getColumn() + 1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}
				
				// #specialmove castling queenside rook
				if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
					Position targetT = new Position(source.getRow(), source.getColumn() - 1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}
				
				// #specialmove en passant
				if(p instanceof Pawn) {
					if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
							if(p.getColor() == Color.WHITE) {
								pawnPosition = new Position(3, target.getColumn());
							}
							else {
								pawnPosition = new Position(4, target.getColumn());
							}
							board.placePiece(pawn, pawnPosition);
					}
				}
		
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) { // SE NAO EXISTIR UMA PECA NESSA POSICAO
			throw new ChessException("There is no piece on source position.");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // SE A PECA N FOR DA COR COLETADA
			throw new ChessException("The chosen piece is not yours.");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) { // SE PARA A PECA NA POSICAO DE ORIGEM, NAO EXISTIR MOVIMENTO POSSIVEL
			throw new ChessException("The chosen piece can't move to target position.");
		}
	}
	
	private void nextTurn() {
		turn++;
		//SE MEU JOGADOR FOR A PECA BRANCA, TROCA PRA PECA PRETA, SE N FOR, TROCA PRA BRANCA
		currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//PROCURA O REI NO TABULEIRO
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); 
			for(Piece p : list) {
				if (p instanceof King) {
					return (ChessPiece)p;
				}
			}
		throw new IllegalStateException("There is no " + color + " king on the board.");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // PEGA A POSICAO DO REI DA COR INFORMADA
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
			for(Piece p : opponentPieces) {
				boolean[][] mat = p.possibleMoves();
					if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
						return true;
					}
			}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
			for(Piece p : list) {
				boolean[][] mat = p.possibleMoves(); // INSTANCIA UMA MATRIZ COM OS MOVIMENTOS POSSIVEIS
					for(int i = 0; i < board.getRows(); i++) { // PERCORRE AS LINHAS DA MATRIZ
						for(int j = 0; j < board.getColumns(); j++) { // PERCORRE AS COLUNAS DA MATRIZ
							if(mat[i][j]) { 
								Position source = ((ChessPiece)p).getChessPosition().toPosition(); // CAPTURA A PECA DA POSICAO DE ORIGEM, PARA SIMULAR O MOVIMENTO
								Position target = new Position(i, j); // DEFINI A POSICAO DE ORIGEM DA PECA CAPTURADA
								Piece capturedPiece = makeMove(source, target); // REALIZA O MOVIMENTO
								boolean testCheck = testCheck(color); // TESTA SE CONTINOU EM CHECK
								undoMove(source, target, capturedPiece); // DESFAZ O MOVIMENTO
									if(!testCheck) {
										return false;
									}
							}
						}
					}
			}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//RECEBE A POSICAO EM FORMA DE XADREZ
		//DECLARA A PECA NA POSICAO INFORMADA
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}	
	
	//RESPONSAVEL POR INICIAR A PARTIDA DE XADREZ
	private void initialSetup() {
		
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
