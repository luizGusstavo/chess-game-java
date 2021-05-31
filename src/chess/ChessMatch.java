package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); // INICIA UM TABULEIRO
		initialSetup(); // FUNCAO RESPONSAVEL POR INICIAR A PARTIDA DE XADREZ
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
		
		// RETORNA A PECA CAPTURADA EM FORMATO DE PECA DE XADREZ
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // CAPTURA A PECA DO LOCAL DE ORIGEM
		Piece capturedPiece = board.removePiece(target); // CAPTURA A PECA DO LOCAL DE DESTINO (SE HOUVER PECA)
		board.placePiece(p, target); // COLOCA A PECA P NO LOCAL DE DESTINO
		return capturedPiece; // RETORNA A PECA QUE FOI CAPTURADA ( SE HOUVER)
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) { // SE NAO EXISTIR UMA PECA NESSA POSICAO
			throw new ChessException("There is no piece on source position.");
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//RECEBE A POSICAO EM FORMA DE XADREZ
		//DECLARA A PECA NA POSICAO INFORMADA
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	//RESPONSAVEL POR INICIAR A PARTIDA DE XADREZ
	private void initialSetup() {
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE)); // COLOCA NA POSICAO c1 UMA TORRE BRANCA
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
