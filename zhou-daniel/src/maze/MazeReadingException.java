package maze;

public class MazeReadingException extends Exception {

    /**
     * Throws an exception when the file is not compatible with the given parameters, or when one of his lines
     * has an issue.
     *
     * @param filename     the file currently being read
     * @param lineError    the line at which the exception is thrown
     * @param errormessage the message explaining what exactly happened
     */
    public MazeReadingException(String filename, int lineError, String errormessage) {
        super("Error in the file " + filename + ", at line " + lineError + ". " + errormessage + ".\n");
    }
}
