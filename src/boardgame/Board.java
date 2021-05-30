package boardgame;

public class Board {
	private int rows; // QUANTIDADE DE LINHAS
	private int columns; // QUANTIDADE DE COLUNAS
	private Piece[][] pieces; // DECALARA UMA MATRIZ DE PECAS
	
	public Board(int rows, int columns) {	
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
			this.rows = rows;
			this.columns = columns;
			pieces = new Piece[rows][columns]; // DECLARA UMA MATRIZ COM A QUANTIDADE LINHAS E COLUNAS
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board.");
		}
		return pieces[row][column]; // RETORNA PESSAS EM DETERMINADA LINHA E COLUNA
	}
	
	public Piece piece(Position position) {
		// RETORNA UMA PECA EM DETERMINADA POSICAO
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		return pieces[position.getRow()][position.getColumn()]; 
	}
	
	public void placePiece(Piece piece, Position position) {
		// DENTRO DA MINHA MATRIZ DE PECAS, EU ADICIONO UMA PECA NA DETERMINADA POSICAO
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // RETORNO A POSICAO ONDE A PECA FOI ADICIONADA
	}
	
	private boolean positionExists(int row, int column) {
		// METODO AUXILIAR PARA VER SE A POSICAO EXISTE
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		// METODO QUE RETORNA SE A POSICAO QUE FOI PASSADA EXISTE
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		return piece(position) != null;
	}
}
