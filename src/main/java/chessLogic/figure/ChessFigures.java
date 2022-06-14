package chessLogic.figure;

import chessLogic.board.ChessBoard;
import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public enum ChessFigures {
    KING((figure, startPosition, endPosition) -> {
        Pair<Integer, Integer> dXdY = getRangesBetweenFields(startPosition, endPosition);
        return (dXdY.getValue0() <= 1 && dXdY.getValue1() <= 1);
    }),
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
    PAWN(ChessFigures::isLegalPawnMove);

    @Autowired
    private static ChessBoard chessBoard;
    @Getter
    private final CheckConsistentMove consistentMoveChecker;

    private static Pair<Integer, Integer> getRangesBetweenFields
            (ChessBoardCoordinateIntegerForm startPosition,
             ChessBoardCoordinateIntegerForm endPosition) {
        int dX = Math.abs(startPosition.getXCoordinate() - endPosition.getXCoordinate());
        int dY = Math.abs(startPosition.getYCoordinate() - endPosition.getYCoordinate());
        return new Pair<Integer, Integer>(dX, dY);
    }

    private static boolean isLegalPawnMove(ChessFigure figure,
                                           ChessBoardCoordinateIntegerForm startPosition,
                                           ChessBoardCoordinateIntegerForm endPosition) {
        ChessFigureLabel label = figure.getLabel();
        return isLegalPawnDiagonalMove(figure, startPosition, endPosition) ||
                isLegalPawnForwardMove(figure, startPosition, endPosition) ||
                isLegalTakingOnThePath(figure, startPosition, endPosition);
    }

    private static boolean isLegalPawnDiagonalMove(ChessFigure figure,
                                                   ChessBoardCoordinateIntegerForm startPosition,
                                                   ChessBoardCoordinateIntegerForm endPosition) {
        ChessFigureLabel label = figure.getLabel();

        ChessFigure figureOnDestField = chessBoard.getFigureByField(endPosition);
        if (figureOnDestField == null || figureOnDestField.getLabel() == label) {
            return false;
        }

        int absDX = Math.abs(endPosition.getXCoordinate() - startPosition.getXCoordinate());
        int dY = endPosition.getYCoordinate() - startPosition.getYCoordinate();
        return (label.equals(ChessFigureLabel.WHITE) && absDX == 1 && dY == 1) ||
                (label.equals(ChessFigureLabel.BLACK) && absDX == 1 && dY == -1);
    }

    private static boolean isLegalPawnForwardMove(ChessFigure figure,
                                                  ChessBoardCoordinateIntegerForm startPosition,
                                                  ChessBoardCoordinateIntegerForm endPosition) {
        ChessFigure figureOnDestField = chessBoard.getFigureByField(endPosition);
        if (figureOnDestField != null) {
            return false;
        }

        ChessFigureLabel label = figure.getLabel();
        int absDX = Math.abs(endPosition.getXCoordinate() - startPosition.getXCoordinate());
        int dY = endPosition.getYCoordinate() - startPosition.getYCoordinate();

        if (figure.getPositionsHistory().size() == 1) {
            return absDX == 0 && ((label.equals(ChessFigureLabel.WHITE) && (dY == 1 || dY == 2)) ||
                    (label.equals(ChessFigureLabel.BLACK) && (dY == -1 || dY == -2)));
        } else {
            return absDX == 0 && ((label.equals(ChessFigureLabel.WHITE) && dY == 1) ||
                    (label.equals(ChessFigureLabel.BLACK) && dY == -1));
        }
    }

    private static boolean isLegalTakingOnThePath(ChessFigure figure,
                                                  ChessBoardCoordinateIntegerForm startPosition,
                                                  ChessBoardCoordinateIntegerForm endPosition) {
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
}
