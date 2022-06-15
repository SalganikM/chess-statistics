package chessLogic.chessCoordinates;

import chessLogic.exceptions.ChessLogicException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

@Data
@AllArgsConstructor
public class ChessBoardCoordinateIntegerForm {
    private @NonNull Integer xCoordinate;
    private @NonNull Integer yCoordinate;

    @SneakyThrows(ChessLogicException.class)
    public boolean isLegalChessBoardCoordinate() {
        return (xCoordinate >= 1 && xCoordinate <= 8) && (yCoordinate >= 1 && yCoordinate <= 8);
    }

    public ChessBoardCoordinateIntegerForm(ChessBoardCoordinates coordinate) {
        ChessBoardCoordinateIntegerForm chessBoardCoordinateIntegerForm = coordinate.getCoordinate();
        this.xCoordinate = chessBoardCoordinateIntegerForm.getXCoordinate();
        this.yCoordinate = chessBoardCoordinateIntegerForm.getYCoordinate();
    }
}
