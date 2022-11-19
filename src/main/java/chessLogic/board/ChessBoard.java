package chessLogic.board;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.chessCoordinates.ChessBoardCoordinates;
import chessLogic.figure.ChessFigure;
import chessLogic.enums.ChessFigureLabel;
import org.javatuples.Pair;

import java.util.List;
import java.util.Set;

public interface ChessBoard {
    void move(ChessBoardCoordinateIntegerForm startPos,
                     ChessBoardCoordinateIntegerForm newPos);

    ChessFigure getFigureByField(ChessBoardCoordinateIntegerForm fieldCoordinate);

    ChessFigure getFigureByField(ChessBoardCoordinates fieldCoordinate);

    List<Pair<ChessBoardCoordinateIntegerForm, ChessBoardCoordinateIntegerForm>> getMoves();

    boolean isFieldUnderAttackByFiguresOfThisLabel(Set<ChessFigure> figures,
                                                   ChessFigureLabel labelOfAttacker,
                                                   ChessBoardCoordinateIntegerForm field);

    boolean isFieldUnderAttackByFiguresOfThisLabel(ChessFigureLabel labelOfAttacker, ChessBoardCoordinateIntegerForm chessBoardCoordinateIntegerForm);
}
