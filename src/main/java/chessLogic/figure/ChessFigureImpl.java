package chessLogic.figure;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.chessCoordinates.ChessBoardCoordinates;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChessFigureImpl implements ChessFigure {
    private final @NonNull ChessBoardCoordinateIntegerForm startCoordinate;
    private final @NonNull ChessFigureLabel label;
    private final @NonNull ChessFiguresAndMoveRules figureType;
    private final @NonNull List<ChessBoardCoordinateIntegerForm> positionsHistory = new ArrayList<>();

    @Setter
    private @NonNull ChessBoardCoordinateIntegerForm coordinate;

    public ChessFigureImpl(ChessBoardCoordinates coordinate,
                           ChessFigureLabel label,
                           ChessFiguresAndMoveRules figureType) {
        this.startCoordinate = translateCoordinateIntoIntegersForm(coordinate);
        this.coordinate = startCoordinate;
        positionsHistory.add(startCoordinate);
        this.label = label;
        this.figureType = figureType;
    }

    protected ChessBoardCoordinateIntegerForm translateCoordinateIntoIntegersForm(ChessBoardCoordinates coordinate) {
        return coordinate.getCoordinate();
    }
}
