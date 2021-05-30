package chess;

import boardgame.Board;
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//RECEBE A POSICAO EM FORMA DE XADREZ
		//DECLARA A PECA NA POSICAO INFORMADA
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	//RESPONSAVEL POR INICIAR A PARTIDA DE XADREZ
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE)); // COLOCA NA POSICAO B6 UMA TORRE PRETA
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
