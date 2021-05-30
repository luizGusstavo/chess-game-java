package boardgame;

public class Piece {
	protected Position position;
	private Board board; // DECLARA O TABULEIRO
	
	public Piece(Board board) {
		this.board = board;
		position = null; // AS PECAS AINDA N FORAM COLOCADAS NO TABULEIRO
	}
	
	protected Board getBoard() {
		return board;
	}
}
