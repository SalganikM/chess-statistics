package chessLogic.figure;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;

@FunctionalInterface
public interface CheckConsistentMove {
    boolean isLegalMove
            (ChessFigure figure,
             ChessBoardCoordinateIntegerForm startPosition,
             ChessBoardCoordinateIntegerForm endPosition);
}
