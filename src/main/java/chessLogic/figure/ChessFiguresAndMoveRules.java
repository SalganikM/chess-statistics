package chessLogic.figure;

import chessLogic.board.ChessBoard;
import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.chessCoordinates.ChessBoardCoordinates;
import chessLogic.exceptions.ChessLogicException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static chessLogic.chessCoordinates.ChessBoardCoordinates.*;
import static chessLogic.figure.ChessFigureLabel.*;

@RequiredArgsConstructor
public enum ChessFiguresAndMoveRules {
    KING((figure, startPosition, endPosition) ->
            isLegalSimpleKingMove(figure, startPosition, endPosition) ||
                    isLegalCastling(figure, startPosition, endPosition)
    ),
    QUEEN((figure, startPosition, endPosition) -> {
        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return dXdY.getValue0().equals(dXdY.getValue1()) || dXdY.getValue0() == 0 || dXdY.getValue1() == 0;
    }),
    ROCK((figure, startPosition, endPosition) -> {
        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return dXdY.getValue0() == 0 || dXdY.getValue1() == 0;
    }),
    BISHOP((figure, startPosition, endPosition) -> {
        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return dXdY.getValue0().equals(dXdY.getValue1());
    }),
    KNIGHT((figure, startPosition, endPosition) -> {
        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return (dXdY.getValue0() == 2 && dXdY.getValue1() == 1) || (dXdY.getValue0() == 1 && dXdY.getValue1() == 2);
    }),
    PAWN((figure, startPosition, endPosition) ->
            isLegalPawnDiagonalMove(figure, startPosition, endPosition) ||
                    isLegalPawnForwardMove(figure, startPosition, endPosition) ||
                    isLegalTakingOnThePath(figure, startPosition, endPosition)
    );

    @Autowired
    private static ChessBoard chessBoard;
    @Getter
    private final CheckConsistentMove consistentMoveChecker;

    public static boolean isLegalSimpleKingMove(ChessFigure figure,
                                                ChessBoardCoordinateIntegerForm startPosition,
                                                ChessBoardCoordinateIntegerForm endPosition) {
        throwExceptionIfItsNullArgumentsOrNonLegalFigureType(figure, KING, startPosition, endPosition);

        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return (dXdY.getValue0() <= 1 && dXdY.getValue1() <= 1);
    }


    public static boolean isLegalCastling(ChessFigure figure,
                                          ChessBoardCoordinateIntegerForm startPosition,
                                          ChessBoardCoordinateIntegerForm destPosition) {
        throwExceptionIfItsNullArgumentsOrNonLegalFigureType(figure, KING, startPosition, destPosition);

        Pair<ChessBoardCoordinates, ChessBoardCoordinates> startAndDestPositions =
                new Pair<>(getCoordinateByIntegerForm(startPosition), getCoordinateByIntegerForm(destPosition));

        ChessBoardCoordinates expectedRockPosition = null;
        Set<ChessBoardCoordinates> fieldsBetweenRockAndKing = null;
        ChessFigureLabel labelOfAttacker = null;

        if (startAndDestPositions.equals(new Pair<>(E1, G1))) {
            expectedRockPosition = H1;
            fieldsBetweenRockAndKing = new HashSet<>() {{
                add(F1);
                add(G1);
            }};
            labelOfAttacker = BLACK;
        } else if (startAndDestPositions.equals(new Pair<>(E1, C1))) {
            expectedRockPosition = A1;
            fieldsBetweenRockAndKing = new HashSet<>() {{
                add(B1);
                add(C1);
                add(D1);
            }};
            labelOfAttacker = BLACK;
        } else if (startAndDestPositions.equals(new Pair<>(E8, G8))) {
            expectedRockPosition = H8;
            fieldsBetweenRockAndKing = new HashSet<>() {{
                add(F8);
                add(G8);
            }};
            labelOfAttacker = WHITE;
        } else if (startAndDestPositions.equals(new Pair<>(E8, C8))) {
            expectedRockPosition = A8;
            fieldsBetweenRockAndKing = new HashSet<>() {{
                add(B8);
                add(C8);
                add(D8);
            }};
            labelOfAttacker = WHITE;
        } else {
            return false;
        }

        ChessFigure expectedRock = chessBoard.getFigureByField(expectedRockPosition);
        if (expectedRock == null ||
                expectedRock.getPositionsHistory().size() != 1 ||
                figure.getPositionsHistory().size() != 1) {
            return false;
        }
        for (ChessBoardCoordinates field : fieldsBetweenRockAndKing) {
            if (chessBoard.getFigureByField(field) != null) {
                return false;
            }
        }
        fieldsBetweenRockAndKing.add(getCoordinateByIntegerForm(startPosition));
        for (ChessBoardCoordinates field : fieldsBetweenRockAndKing) {
            if (chessBoard.isFieldUnderAttackByFiguresOfThisLabel
                    (labelOfAttacker, new ChessBoardCoordinateIntegerForm(field))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLegalPawnDiagonalMove(ChessFigure figure,
                                                  ChessBoardCoordinateIntegerForm startPosition,
                                                  ChessBoardCoordinateIntegerForm endPosition) {
        throwExceptionIfItsNullArgumentsOrNonLegalFigureType(figure, PAWN, startPosition, endPosition);

        ChessFigureLabel label = figure.getLabel();

        ChessFigure figureOnDestField = chessBoard.getFigureByField(endPosition);
        if (figureOnDestField == null || figureOnDestField.getLabel() == label) {
            return false;
        }

        int absDX = Math.abs(endPosition.getXCoordinate() - startPosition.getXCoordinate());
        int dY = endPosition.getYCoordinate() - startPosition.getYCoordinate();
        return (label.equals(ChessFigureLabel.WHITE) && absDX == 1 && dY == 1) ||
                (label.equals(BLACK) && absDX == 1 && dY == -1);
    }

    public static boolean isLegalPawnForwardMove(ChessFigure figure,
                                                 ChessBoardCoordinateIntegerForm startPosition,
                                                 ChessBoardCoordinateIntegerForm endPosition) {
        throwExceptionIfItsNullArgumentsOrNonLegalFigureType(figure, PAWN, startPosition, endPosition);

        ChessFigure figureOnDestField = chessBoard.getFigureByField(endPosition);
        if (figureOnDestField != null) {
            return false;
        }

        ChessFigureLabel label = figure.getLabel();
        int absDX = Math.abs(endPosition.getXCoordinate() - startPosition.getXCoordinate());
        int dY = endPosition.getYCoordinate() - startPosition.getYCoordinate();

        if (figure.getPositionsHistory().size() == 1) {
            return absDX == 0 && ((label.equals(ChessFigureLabel.WHITE) && (dY == 1 || dY == 2)) ||
                    (label.equals(BLACK) && (dY == -1 || dY == -2)));
        } else {
            return absDX == 0 && ((label.equals(ChessFigureLabel.WHITE) && dY == 1) ||
                    (label.equals(BLACK) && dY == -1));
        }
    }

    public static boolean isLegalTakingOnThePath(ChessFigure figure,
                                                 ChessBoardCoordinateIntegerForm startPosition,
                                                 ChessBoardCoordinateIntegerForm endPosition) {
        throwExceptionIfItsNullArgumentsOrNonLegalFigureType(figure, PAWN, startPosition, endPosition);

        ChessFigureLabel label = figure.getLabel();
        int absDX = Math.abs(endPosition.getXCoordinate() - startPosition.getXCoordinate());
        int startPosY = 0;
        int endPosY = 0;
        int defaultPawnPosY = 0;

        if (label.equals(ChessFigureLabel.WHITE)) {
            startPosY = 5;
            endPosY = 6;
            defaultPawnPosY = 7;
        } else {
            startPosY = 4;
            endPosY = 3;
            defaultPawnPosY = 2;
        }

        if (startPosition.getYCoordinate() != startPosY || endPosition.getYCoordinate() != endPosY || absDX != 1) {
            return false;
        }
        ChessFigure figureOnDestField = chessBoard.getFigureByField(new ChessBoardCoordinateIntegerForm
                (endPosition.getXCoordinate(), startPosY));

        return figureOnDestField.getFigureType() == PAWN &&
                figureOnDestField.getPositionsHistory().size() == 2 &&
                chessBoard.getMoves().get(chessBoard.getMoves().size() - 1).equals(new Pair<>
                        (new ChessBoardCoordinateIntegerForm(endPosition.getXCoordinate(), defaultPawnPosY),
                                new ChessBoardCoordinateIntegerForm(endPosition.getXCoordinate(), startPosY)));
    }

    private static Pair<Integer, Integer> getRangesBetweenFields
            (ChessBoardCoordinateIntegerForm startPosition,
             ChessBoardCoordinateIntegerForm endPosition) {
        int dX = Math.abs(startPosition.getXCoordinate() - endPosition.getXCoordinate());
        int dY = Math.abs(startPosition.getYCoordinate() - endPosition.getYCoordinate());
        return new Pair<Integer, Integer>(dX, dY);
    }

    private static void throwExceptionIfItsNullArgumentsOrNonLegalFigureType
            (ChessFigure figure,
             ChessFiguresAndMoveRules figureType,
             ChessBoardCoordinateIntegerForm startPosition,
             ChessBoardCoordinateIntegerForm endPosition) {
        if (figure == null || figure.getFigureType() != figureType || startPosition == null || endPosition == null) {
            String message = "Invocation of move consistency check with non consistent arguments";
            throw new ChessLogicException(message);
        }
    }
}
