package chessLogic.board;

import chessLogic.chessCoordinates.ChessBoardCoordinateIntegerForm;
import chessLogic.figure.ChessFigure;
import chessLogic.figure.ChessFigureImpl;
import chessLogic.figure.ChessFigureLabel;
import chessLogic.exceptions.ChessLogicException;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static chessLogic.chessCoordinates.ChessBoardCoordinates.*;
import static chessLogic.figure.ChessFigureLabel.*;
import static chessLogic.figure.ChessFigures.*;

@Component
public class ChessBoardImpl implements ChessBoard {
    private final @NonNull Set<ChessFigure> figures = new HashSet<>();

    @Getter
    private final @NonNull List<Pair<ChessBoardCoordinateIntegerForm, ChessBoardCoordinateIntegerForm>> moves = new ArrayList<>();

    public ChessBoardImpl() {
        initFiguresInStartPositions();
    }

    @Override
    @SneakyThrows(ChessLogicException.class)
    public void move(ChessBoardCoordinateIntegerForm startField,
                     ChessBoardCoordinateIntegerForm destField) {
        throwExceptionIfBadCoordinates(startField, destField);
        ChessFigure figure = getFigureByField(startField);
        throwExceptionIfItsNotLegalMove(figure, startField, destField);
        doMove(figure, startField, destField);
    }

    public ChessFigure getFigureByField(ChessBoardCoordinateIntegerForm fieldCoordinate) {
        List<ChessFigure> figuresOnThatField = figures.stream()
                .filter(figure -> fieldCoordinate.equals(figure.getCoordinate()))
                .collect(Collectors.toList());
        if (figuresOnThatField.size() == 0) {
            return null;
        } else if (figuresOnThatField.size() > 1) {
            throw new ChessLogicException("There's more than 1 figure in place which used as start position for move");
        } else {
            return figuresOnThatField.get(0);
        }
    }

    private void doMove(ChessFigure figure,
                        ChessBoardCoordinateIntegerForm startPos,
                        ChessBoardCoordinateIntegerForm newPos) {

    }

    private void initFiguresInStartPositions() {
        figures.clear();

        figures.add(new ChessFigureImpl(C1, WHITE, BISHOP));
        figures.add(new ChessFigureImpl(F1, WHITE, BISHOP));
        figures.add(new ChessFigureImpl(C8, BLACK, BISHOP));
        figures.add(new ChessFigureImpl(F8, BLACK, BISHOP));
        figures.add(new ChessFigureImpl(B1, WHITE, KNIGHT));
        figures.add(new ChessFigureImpl(G1, WHITE, KNIGHT));
        figures.add(new ChessFigureImpl(B8, BLACK, KNIGHT));
        figures.add(new ChessFigureImpl(G8, BLACK, KNIGHT));
        figures.add(new ChessFigureImpl(A1, WHITE, ROCK));
        figures.add(new ChessFigureImpl(H1, WHITE, ROCK));
        figures.add(new ChessFigureImpl(A8, BLACK, ROCK));
        figures.add(new ChessFigureImpl(H8, BLACK, ROCK));
        figures.add(new ChessFigureImpl(D1, WHITE, QUEEN));
        figures.add(new ChessFigureImpl(D8, BLACK, QUEEN));
        figures.add(new ChessFigureImpl(E1, WHITE, KING));
        figures.add(new ChessFigureImpl(E8, BLACK, KING));
        figures.add(new ChessFigureImpl(A2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(B2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(C2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(D2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(E2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(F2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(G2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(H2, WHITE, PAWN));
        figures.add(new ChessFigureImpl(A7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(B7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(C7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(D7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(E7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(F7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(G7, BLACK, PAWN));
        figures.add(new ChessFigureImpl(H7, BLACK, PAWN));
    }

    private void throwExceptionIfBadCoordinates(ChessBoardCoordinateIntegerForm startField,
                                                ChessBoardCoordinateIntegerForm destField) {
        if (!(startField.isLegalChessBoardCoordinate() && destField.isLegalChessBoardCoordinate())) {
            String message = "invocation move method on chess board with non consistent coordinates";
            throw new ChessLogicException(message);
        }
    }

    private void throwExceptionIfItsNotLegalMove(ChessFigure figure,
                                                 ChessBoardCoordinateIntegerForm startField,
                                                 ChessBoardCoordinateIntegerForm destField) {
        throwExceptionIfThereAreFriendlyPeaceInDestinationField(destField, figure.getLabel());
        throwExceptionIfStartAndDestFieldAreSame(startField, destField);
        throwExceptionIfThisMoveIsNotLegalForThatFigure(figure, startField, destField);
        throwExceptionIfThereAreFiguresOnPath(startField, destField);
    }

    private void throwExceptionIfThereAreFriendlyPeaceInDestinationField
            (ChessBoardCoordinateIntegerForm destFieldCoordinate,
             ChessFigureLabel label) {
        figures.stream()
                .filter(figure -> label.equals(figure.getLabel()))
                .forEach(figure -> {
                    if (destFieldCoordinate.equals(figure.getCoordinate())) {
                        String message = "There are friendly peace in destination field";
                        throw new ChessLogicException(message);
                    }
                });
    }

    private void throwExceptionIfStartAndDestFieldAreSame
            (ChessBoardCoordinateIntegerForm startField,
             ChessBoardCoordinateIntegerForm destField) {
        if (startField.equals(destField)) {
            String message = "Start and destination fields are the same";
            throw new ChessLogicException(message);
        }
    }

    private void throwExceptionIfThisMoveIsNotLegalForThatFigure(ChessFigure figure,
                                                                 ChessBoardCoordinateIntegerForm startField,
                                                                 ChessBoardCoordinateIntegerForm destField) {
        if (!figure.getFigureType().getConsistentMoveChecker().isLegalMove
                (figure, startField, destField)) {
            String message = "Invocation move method on chess board with non consistent coordinates";
            throw new ChessLogicException(message);
        }
    }

    private void throwExceptionIfThereAreFiguresOnPath
            (ChessBoardCoordinateIntegerForm startField,
             ChessBoardCoordinateIntegerForm destField) {
        Set<ChessBoardCoordinateIntegerForm> coordinatesSet = figures.stream()
                .map(ChessFigure::getCoordinate)
                .collect(Collectors.toSet());

        int dX = destField.getXCoordinate() - startField.getXCoordinate();
        int dY = destField.getYCoordinate() - startField.getYCoordinate();
        int absDX = Math.abs(startField.getXCoordinate() - destField.getXCoordinate());
        int absDY = Math.abs(startField.getYCoordinate() - destField.getYCoordinate());

        ChessBoardCoordinateIntegerForm[] fields = new ChessBoardCoordinateIntegerForm[absDX - 1];

        if (Math.max(absDX, absDY) > 1 && (absDX == absDY || absDX == 0 || absDY == 0)) {
            for (int i = 1; i < Math.max(absDX, absDY); i++) {
                int x = startField.getXCoordinate() + dX / absDX * i;
                int y = startField.getYCoordinate() + dY / absDY * i;
                fields[i - 1] = new ChessBoardCoordinateIntegerForm(x, y);
            }
            for (ChessBoardCoordinateIntegerForm field : fields) {
                if (coordinatesSet.contains(field)) {
                    String message = "There are figure on path";
                    throw new ChessLogicException(message);
                }
            }
        }
    }
}
