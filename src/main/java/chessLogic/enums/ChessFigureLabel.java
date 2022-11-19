package chessLogic.enums;

public enum ChessFigureLabel {
    WHITE,
    BLACK;

    public static ChessFigureLabel getOtherLabel(ChessFigureLabel label) {
        if (label == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}
