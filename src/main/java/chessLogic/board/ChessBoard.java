package chessLogic.board;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.chessCoordinates.ChessBoardCoordinates;
import chessLogic.figure.ChessFigure;
import chessLogic.figure.ChessFigureLabel;
import org.javatuples.Pair;

import java.util.List;

public interface ChessBoard {
    public void move(ChessBoardCoordinateIntegerForm startPos,
                     ChessBoardCoordinateIntegerForm newPos);

    public ChessFigure getFigureByField(ChessBoardCoordinateIntegerForm fieldCoordinate);

    public ChessFigure getFigureByField(ChessBoardCoordinates fieldCoordinate);

    public List<Pair<ChessBoardCoordinateIntegerForm, ChessBoardCoordinateIntegerForm>> getMoves();

    public boolean isFieldUnderAttackByFiguresOfThisLabel(ChessFigureLabel labelOfAttacker, ChessBoardCoordinateIntegerForm field);
}
