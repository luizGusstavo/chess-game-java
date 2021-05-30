package boardgame;

public class Board {
	private int rows; // QUANTIDADE DE LINHAS
	private int columns; // QUANTIDADE DE COLUNAS
	private Piece[][] pieces; // DECALARA UMA MATRIZ DE PECAS
	
	public Board(int rows, int columns) {	
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // DECLARA UMA MATRIZ COM A QUANTIDADE LINHAS E COLUNAS
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) {
		return pieces[row][column]; // RETORNA UMA MATRIZ DE PESSAS EM DETERMINADA LINHA E COLUNA
	}
	
	public Piece piece(Position position) {
		// RETORNA UMA PECA EM DETERMINADA POSICAO
		return pieces[position.getRow()][position.getColumn()]; 
	}
}
