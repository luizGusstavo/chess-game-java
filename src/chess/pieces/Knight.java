package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{
	
	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "C";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position); // PEGUE A PECA NA SEGUINTE POSICAO DO TABULEIRO
		return p == null || p.getColor() != getColor(); // OU A CASA TA VAZIA OU TEM UMA PECA ADVERSARIA
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//above - acima
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
				
		//below - abaixo
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		
		//left - esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
						
		//left - esquerda
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
				
		//nw - noroeste
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
				
		//ne - nordeste
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
			
		//ne - nordeste
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			}
			
		//se - sudeste
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}	
						
		return mat;
	}

}
