package service;

import chessLogic.board.ChessBoard;
import chessLogic.chessCoordinates.ChessBoardCoordinates;
import chessLogic.enums.ChessFigureLabel;
import chessLogic.enums.ChessWinner;

public interface ChessService {
    void move(ChessBoardCoordinates startPos, ChessBoardCoordinates newPos);

    ChessWinner getResult();

    ChessFigureLabel getTurnToMove();

    ChessBoard getBoard();
}
