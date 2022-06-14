package chessLogic.board;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.figure.ChessFigure;
import org.javatuples.Pair;

import java.util.List;

public interface ChessBoard {
    public void move(ChessBoardCoordinateIntegerForm startPos,
                     ChessBoardCoordinateIntegerForm newPos);

    public ChessFigure getFigureByField(ChessBoardCoordinateIntegerForm fieldCoordinate);

    public List<Pair<ChessBoardCoordinateIntegerForm, ChessBoardCoordinateIntegerForm>> getMoves();
}
