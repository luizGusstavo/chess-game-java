package application;

import chess.ChessPiece;

public class UI {
	public static void printBoard(ChessPiece[][] pieces) {
		for(int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // IMPRIME AS LINHAS 
				for(int j = 0; j < pieces.length; j++) {
					printPiece(pieces[i][j]); // IMPRIME A PECA EM DETERMINADA POSICAO
				}
			System.out.println();
		}
	System.out.println("  a b c d e f g h");
	}
	
	public static void printPiece(ChessPiece piece) {
		if ( piece == null) { // SE A POSICAO FOR NULL, ADICIONA UMA PECA DETERMINADA
			System.out.print("-");
		}
		else {
			System.out.print(piece); // SE JA HOUVER UMA PEXCA NA POSICAO, RETORNA A PECA
		}
		System.out.print(" ");
	}
}
