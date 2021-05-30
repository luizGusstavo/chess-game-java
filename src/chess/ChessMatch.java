package chess;

import boardgame.Board;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); // INICIA UM TABULEIRO
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
}
