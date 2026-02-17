package impSinkAship;

import java.util.*;

public class GameHelper {
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;
    static final int HORIZONTAL_INCREMENT = 1;
    static final int VERTICAL_INCREMENT = GRID_LENGTH;

    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();
    private int startupCount = 0;

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    } //

    public ArrayList<String> placeStartup(int startupSize) {
        // holds index to grid (0 - 48)
        int[] startupCoords = new int[startupSize];
        int attempts = 0;
        boolean success = false;

        startupCount++;
        int increment = getIncrement(); // alternates vert & horiz

        while (!success & attempts++ < MAX_ATTEMPTS) {
            int location = random.nextInt(GRID_SIZE);

            for (int i = 0; i < startupCoords.length; i++) {
                startupCoords[i] = location;
                location += increment;
            }

            if (startupFits(startupCoords, increment)) {
                success = coordsAvailable(startupCoords);
            }
        }

        savePositionToGrid(startupCoords);
        return convertCoordsToAlpha(startupCoords);
    }

    private int getIncrement() {
        return (startupCount % 2 == 0) ? VERTICAL_INCREMENT : HORIZONTAL_INCREMENT;
    }

    private boolean startupFits(int[] startupCoords, int increment) {
        int finalLocation = startupCoords[startupCoords.length - 1];
        if (finalLocation >= GRID_SIZE) return false;
        if (increment == HORIZONTAL_INCREMENT) {
            return (startupCoords[0] / GRID_LENGTH == finalLocation / GRID_LENGTH);
        }
        return true;
    }

    private boolean coordsAvailable(int[] startupCoords) {
        for (int coord : startupCoords) {
            if (grid[coord] != 0) return false;
        }
        return true;
    }

    private void savePositionToGrid(int[] startupCoords) {
        for (int coord : startupCoords) {
            grid[coord] = 1;
        }
    }

    private ArrayList<String> convertCoordsToAlpha(int[] startupCoords) {
        ArrayList<String> alphaCells = new ArrayList<>();
        for (int coord : startupCoords) {
            int row = coord / GRID_LENGTH;
            int column = coord % GRID_LENGTH;
            String letter = ALPHABET.substring(column, column + 1);
            alphaCells.add(letter + row);
        }
        return alphaCells;
    }
}
