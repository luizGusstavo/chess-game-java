package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{
	
	// INFORMA QUEM E O TABULEIRO E QUAL A COR DA PECA
	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		// nw - Noroeste
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
			while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSICAO P EXISTIR E NAO TIVER NENHUMA PECA NELA
				mat[p.getRow()][p.getColumn()] = true; // DECLARA QUE A TORRE PODE SE MOVER NESSA POSICAO
				p.setValues(p.getRow() - 1, p.getColumn() - 1);
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
		// ne - nordeste
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
			while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSICAO P EXISTIR E NAO TIVER NENHUMA PECA NELA
				mat[p.getRow()][p.getColumn()] = true; // DECLARA QUE A TORRE PODE SE MOVER NESSA POSICAO
				p.setValues(p.getRow() - 1, p.getColumn() + 1);
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
		// se - sudeste
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
			while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSICAO P EXISTIR E NAO TIVER NENHUMA PECA NELA
				mat[p.getRow()][p.getColumn()] = true; // DECLARA QUE A TORRE PODE SE MOVER NESSA POSICAO
				p.setValues(p.getRow() + 1, p.getColumn() + 1);
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
		// sw - sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
			while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSICAO P EXISTIR E NAO TIVER NENHUMA PECA NELA
				mat[p.getRow()][p.getColumn()] = true; // DECLARA QUE A TORRE PODE SE MOVER NESSA POSICAO
				p.setValues(p.getRow() + 1, p.getColumn() - 1);
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		
		return mat;
	}
}
