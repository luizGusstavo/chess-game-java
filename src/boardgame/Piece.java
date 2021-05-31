package boardgame;

public abstract class Piece {
	protected Position position;
	private Board board; // DECLARA O TABULEIRO
	
	public Piece(Board board) {
		this.board = board;
		position = null; // AS PECAS AINDA N FORAM COLOCADAS NO TABULEIRO
	}
	
	protected Board getBoard() {
		return board;
	}
	
	// RETORNA UMA MATRIZ DE VALORES BOOLEANOS
	// RETORNA TRUE EM TODOS OS CAMPOS EM QUE A PECA POSSA SE MOVER
	public abstract boolean[][] possibleMoves();
	
	//METODO CONCRETO USANDO UM METODO ABSTRATO
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean [][] mat = possibleMoves(); // INSTANCIA UMA MATRIZ DE VALORES BOOLEANOS
			for(int i = 0; i < mat.length; i++) {
				for(int j = 0; j < mat.length; j++) {
					if(mat[i][j]) { // SE VERDADEIRO
						return true;
					}
				}
			}
		return false;
	}
}
