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
}
