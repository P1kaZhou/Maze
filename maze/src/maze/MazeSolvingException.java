package maze;

public class MazeSolvingException extends Exception {

    /**
     * Throws an exception when the maze is unsolvable due to no path between the departure case and the arrival.
     * To put it more simply, if arrival does NOT have any predecessor starting from departure, then we throw this
     * exception.
     */
    public MazeSolvingException() {
        super("Unsolvable maze. There is no valid path between departure and arrival");
    }
}
