import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Runner {
    public static void main(String[] args) {
        Grid grid = new Grid();
        int result = grid.fillWithValues(325489);
        System.out.println(result);
    }
}

class Grid {
    private static final int INITIAL_NUMBER = 1;
    private final Square initialSquare = new Square(0, 0);
    private Map<Integer, Square> squares;
    private Map<Coordinates, Square> squaresByCoord = new HashMap<>();

    Grid() {
        this.squares = new HashMap<>();
        this.squares.put(INITIAL_NUMBER, initialSquare);
    }

    void fill(int toFind) {
        //R, U, L, L, D, D, R, R, R, U, U, U, L, L, L, L, D, D, D, D

        int i = INITIAL_NUMBER;
        int shift = 0;
        boolean rightAndUp = true;
        Square square = initialSquare;

        while (i < toFind) {
            shift++;

            for (int j = 0; j < shift; j++) {
                square = square.next();
                if (rightAndUp)
                    square.right();
                else
                    square.left();
                i++;
                squares.put(i, square);
            }

            for (int j = 0; j < shift; j++) {
                square = square.next();
                if (rightAndUp)
                    square.up();
                else
                    square.down();
                i++;
                squares.put(i, square);
            }

            rightAndUp = !rightAndUp;
        }
    }

    int fillWithValues(int input) {
        int shift = 0;
        boolean rightAndUp = true;
        Square square = initialSquare;
        square.setValue(1);
        squaresByCoord.put(new Coordinates(0, 0), square);

        while (true) {
            shift++;
            Coordinates coordinates;

            for (int j = 0; j < shift; j++) {
                square = square.next();
                if (rightAndUp)
                    coordinates = square.right();
                else
                    coordinates = square.left();
                squaresByCoord.put(coordinates, square);

                int value = calcValue(square);
                if (value > input) return value;
                square.setValue(value);
            }

            for (int j = 0; j < shift; j++) {
                square = square.next();
                if (rightAndUp)
                    coordinates = square.up();
                else
                    coordinates = square.down();
                squaresByCoord.put(coordinates, square);

                int value = calcValue(square);
                if (value > input) return value;
                square.setValue(value);
            }

            rightAndUp = !rightAndUp;
        }
    }

    private int calcValue(Square square) {
        int x = square.getX();
        int y = square.getY();
        final int[] sum = {0};
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x - 1, y + 1)))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x, y + 1)))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x + 1, y + 1)))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x - 1, y)))
                .ifPresent(o -> sum[0] += o.getValue());

        Optional.ofNullable(squaresByCoord.get(new Coordinates(x +1, y )))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x - 1, y - 1)))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x , y-1 )))
                .ifPresent(o -> sum[0] += o.getValue());
        Optional.ofNullable(squaresByCoord.get(new Coordinates(x +1, y -1)))
                .ifPresent(o -> sum[0] += o.getValue());
        return sum[0];
    }

    int findDistanceFrom(int i) {
        Square square = squares.get(i);
        int x = square.getX();
        int y = square.getY();

        int xAbs = Math.abs(x);
        int yAbs = Math.abs(y);

        return xAbs + yAbs;
    }
}

class Coordinates {
    private int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

class Square {
    private int x, y;
    private int value = 0;

    Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Square next() {
        return new Square(x, y);
    }

    Coordinates right() {
        this.x += 1;
        return new Coordinates(x, y);
    }

    Coordinates left() {
        this.x -= 1;
        return new Coordinates(x, y);
    }

    Coordinates up() {
        this.y += 1;
        return new Coordinates(x, y);
    }

    Coordinates down() {
        this.y -= 1;
        return new Coordinates(x, y);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }


}
