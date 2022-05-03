package maze;

public class MazeIssueException extends Exception { //In case we have zero/two arrival or departure cases

    /**
     * Throws an exception when the maze is Illegal, mostly when it has two arrival or departure cases.
     * Contrary to MazeReadingException, this exception is thrown when the maze the user is customizing from the
     * ui happens to be an illegal one
     *
     * @param lineError   the line which the exception happened
     * @param charAtError the rank at the line where the issue happened
     * @param label       the label type, here it will either be "A" or "D"
     */
    public MazeIssueException(int lineError, int charAtError, String label) {
        super("Error at reading maze, line " + lineError + ", char at " + charAtError +
                ". Zero, or more than two occurrences of the label " + label + "\n");
    }
}
