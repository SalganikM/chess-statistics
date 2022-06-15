package chessLogic.chessCoordinates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChessBoardCoordinates {
    A1(new ChessBoardCoordinateIntegerForm(1, 1)),
    A2(new ChessBoardCoordinateIntegerForm(1, 2)),
    A3(new ChessBoardCoordinateIntegerForm(1, 3)),
    A4(new ChessBoardCoordinateIntegerForm(1, 4)),
    A5(new ChessBoardCoordinateIntegerForm(1, 5)),
    A6(new ChessBoardCoordinateIntegerForm(1, 6)),
    A7(new ChessBoardCoordinateIntegerForm(1, 7)),
    A8(new ChessBoardCoordinateIntegerForm(1, 8)),
    B1(new ChessBoardCoordinateIntegerForm(2, 1)),
    B2(new ChessBoardCoordinateIntegerForm(2, 2)),
    B3(new ChessBoardCoordinateIntegerForm(2, 3)),
    B4(new ChessBoardCoordinateIntegerForm(2, 4)),
    B5(new ChessBoardCoordinateIntegerForm(2, 5)),
    B6(new ChessBoardCoordinateIntegerForm(2, 6)),
    B7(new ChessBoardCoordinateIntegerForm(2, 7)),
    B8(new ChessBoardCoordinateIntegerForm(2, 8)),
    C1(new ChessBoardCoordinateIntegerForm(3, 1)),
    C2(new ChessBoardCoordinateIntegerForm(3, 2)),
    C3(new ChessBoardCoordinateIntegerForm(3, 3)),
    C4(new ChessBoardCoordinateIntegerForm(3, 4)),
    C5(new ChessBoardCoordinateIntegerForm(3, 5)),
    C6(new ChessBoardCoordinateIntegerForm(3, 6)),
    C7(new ChessBoardCoordinateIntegerForm(3, 7)),
    C8(new ChessBoardCoordinateIntegerForm(3, 8)),
    D1(new ChessBoardCoordinateIntegerForm(4, 1)),
    D2(new ChessBoardCoordinateIntegerForm(4, 2)),
    D3(new ChessBoardCoordinateIntegerForm(4, 3)),
    D4(new ChessBoardCoordinateIntegerForm(4, 4)),
    D5(new ChessBoardCoordinateIntegerForm(4, 5)),
    D6(new ChessBoardCoordinateIntegerForm(4, 6)),
    D7(new ChessBoardCoordinateIntegerForm(4, 7)),
    D8(new ChessBoardCoordinateIntegerForm(4, 8)),
    E1(new ChessBoardCoordinateIntegerForm(5, 1)),
    E2(new ChessBoardCoordinateIntegerForm(5, 2)),
    E3(new ChessBoardCoordinateIntegerForm(5, 3)),
    E4(new ChessBoardCoordinateIntegerForm(5, 4)),
    E5(new ChessBoardCoordinateIntegerForm(5, 5)),
    E6(new ChessBoardCoordinateIntegerForm(5, 6)),
    E7(new ChessBoardCoordinateIntegerForm(5, 7)),
    E8(new ChessBoardCoordinateIntegerForm(5, 8)),
    F1(new ChessBoardCoordinateIntegerForm(6, 1)),
    F2(new ChessBoardCoordinateIntegerForm(6, 2)),
    F3(new ChessBoardCoordinateIntegerForm(6, 3)),
    F4(new ChessBoardCoordinateIntegerForm(6, 4)),
    F5(new ChessBoardCoordinateIntegerForm(6, 5)),
    F6(new ChessBoardCoordinateIntegerForm(6, 6)),
    F7(new ChessBoardCoordinateIntegerForm(6, 7)),
    F8(new ChessBoardCoordinateIntegerForm(6, 8)),
    G1(new ChessBoardCoordinateIntegerForm(7, 1)),
    G2(new ChessBoardCoordinateIntegerForm(7, 2)),
    G3(new ChessBoardCoordinateIntegerForm(7, 3)),
    G4(new ChessBoardCoordinateIntegerForm(7, 4)),
    G5(new ChessBoardCoordinateIntegerForm(7, 5)),
    G6(new ChessBoardCoordinateIntegerForm(7, 6)),
    G7(new ChessBoardCoordinateIntegerForm(7, 7)),
    G8(new ChessBoardCoordinateIntegerForm(7, 8)),
    H1(new ChessBoardCoordinateIntegerForm(8, 1)),
    H2(new ChessBoardCoordinateIntegerForm(8, 2)),
    H3(new ChessBoardCoordinateIntegerForm(8, 3)),
    H4(new ChessBoardCoordinateIntegerForm(8, 4)),
    H5(new ChessBoardCoordinateIntegerForm(8, 5)),
    H6(new ChessBoardCoordinateIntegerForm(8, 6)),
    H7(new ChessBoardCoordinateIntegerForm(8, 7)),
    H8(new ChessBoardCoordinateIntegerForm(8, 8));

    @Getter
    private final ChessBoardCoordinateIntegerForm coordinate;

    public static ChessBoardCoordinates getCoordinateByIntegerForm(ChessBoardCoordinateIntegerForm intCoordinate) {
        for (ChessBoardCoordinates coordinate : ChessBoardCoordinates.values()) {
            if (coordinate.getCoordinate().equals(intCoordinate)) {
                return coordinate;
            }
        }
        return null;
    }
}
