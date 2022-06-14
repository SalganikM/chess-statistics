package chessLogic.exceptions;

public class ChessLogicException extends RuntimeException {
    public ChessLogicException(String message, Throwable e) {
        super(message, e);
    }

    public ChessLogicException(Throwable e) {
        super(e);
    }

    public ChessLogicException(String message) {
        super(message);
    }

    public ChessLogicException() {
        super();
    }
}
