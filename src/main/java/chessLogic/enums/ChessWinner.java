package chessLogic.enums;

public enum ChessWinner {
    WHITE,
    BLACK,
    DRAW;

    public static ChessWinner getByLabel(ChessFigureLabel label) {
        if (label == ChessFigureLabel.BLACK) {
            return BLACK;
        } else if (label == ChessFigureLabel.WHITE) {
            return WHITE;
        } else {
            return DRAW;
        }
    }
}
