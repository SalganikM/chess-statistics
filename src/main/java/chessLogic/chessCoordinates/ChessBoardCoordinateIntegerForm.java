package chessLogic.chessCoordinates;

import chessLogic.exceptions.ChessLogicException;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

@Data
public class ChessBoardCoordinateIntegerForm {
    private @NonNull Integer xCoordinate;
    private @NonNull Integer yCoordinate;

    @SneakyThrows(ChessLogicException.class)
    public boolean isLegalChessBoardCoordinate() {
        return (xCoordinate >= 1 && xCoordinate <= 8) && (yCoordinate >= 1 && yCoordinate <= 8);
    }
}
