package ui;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    /**
     * Instantiates a MazePanel, which is in the MainPanel and will contain every maze case.
     * Will initially be a grid composed by 10 rows and 10 columns.
     *
     * @param mazeHeight The height of the maze
     * @param mazeWidth  The width of the maze
     */
    public MazePanel(int mazeHeight, int mazeWidth) {
        super(new GridLayout(mazeHeight, mazeWidth));
    }

}
