package chessLogic.figure;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;

import java.util.List;

public interface ChessFigure {
    ChessBoardCoordinateIntegerForm getCoordinate();

    void setCoordinate(ChessBoardCoordinateIntegerForm coordinate);

    ChessBoardCoordinateIntegerForm getStartCoordinate();

    List<ChessBoardCoordinateIntegerForm> getPositionsHistory();

    ChessFigureLabel getLabel();

    ChessFiguresAndMoveRules getFigureType();
}
