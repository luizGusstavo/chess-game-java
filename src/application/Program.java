package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		ChessMatch chessMatch = new ChessMatch(); // INSTANCIA UMA PARTIDA DE XADREZ
		UI.printBoard(chessMatch.getPieces()); // IMPRIME O TABULEIRO
	}

}
