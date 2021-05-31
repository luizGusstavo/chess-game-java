package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch(); // INSTANCIA UMA PARTIDA DE XADREZ

		while (true) {
			try {

				UI.clearScreen();
				UI.printMatch(chessMatch); // IMPRIME A PARITDA DE XADREZ
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			} 
			catch (ChessException excecao) {
				System.out.print(excecao.getMessage());
				sc.nextLine();
			} 
			catch (InputMismatchException excecao) {
				System.out.print(excecao.getMessage());
				sc.nextLine();
			}
		}

	}
}
