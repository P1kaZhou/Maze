package ui;

import javax.swing.*;

public class MainPanel extends JPanel {

    /**
     * Instantiates a MainPanel which contains the Maze and all its components.
     */
    public MainPanel() { //The maze grid is included in the Main Panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

}