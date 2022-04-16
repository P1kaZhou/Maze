package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//View and controller somehow
public class MazeBox extends JPanel implements MouseListener {

    private final Color color;
    private final MazeWindow window;
    private final int x;
    private final int y;
    private final PanelBoxes panelBoxes;

    /**
     * Instantiates a new MazeBox, which a component of the window represented by a little square.
     *
     * @param color      the color of the box
     * @param window     the window on which the box will be seen (self-explanatory)
     * @param panelBoxes the array representing the current view
     * @param x          the first parameter which is for labelPanel in the MazeWindow class
     * @param y          the second parameter which is also for the same thing
     */
    public MazeBox(Color color, MazeWindow window, PanelBoxes panelBoxes, int x, int y) {
        //The constructor: every point of the GraphInterface is a MazeBox in the IHM
        super();
        this.x = x;
        this.y = y;
        this.window = window;
        this.setBackground(color);
        this.color = color;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setSize(new Dimension(20, 20));
        this.addMouseListener(this);
        this.panelBoxes = panelBoxes;
    }

    /**
     * Changes the color of a given mazeBox from his current one to color.
     *
     * @param color the new color of mazeBox
     */
    public void setColor(Color color) {
        this.setBackground(color);
    }

    /**
     * Returns the first coordinate of a given mazeBox.
     *
     * @return x
     */
    public int getXCoordinate() {
        return x;
    }

    /**
     * Returns the second coordinate of a given mazeBox.
     *
     * @return y
     */
    public int getYCoordinate() {
        return y;
    }

    /**
     * Returns a String corresponding to the label of a given mazeBox.
     *
     * @return label
     */
    public String getBoxLabel() {
        return convertColorToLabel(this.color);
    } //Each color is assigned to a label type

    /**
     * Converts the given label into a color (from convention) and returns it.
     *
     * @param label the label we want to know which color it's corresponding to
     * @return the color corresponding to the given label
     */
    public static Color convertLabelToColor(String label) { //Exactly what I said beforehand
        if (label.equals("A")) {
            return Color.green;
        } else if (label.equals("D")) {
            return Color.red;
        } else if (label.equals("E")) {
            return Color.white;
        } else if (label.equals("W")) {
            return Color.black;
        } else {
            return null;
        }
    }

    /**
     * Does the reverse action from returnColorFromLabel, ie converts a color to its corresponding String label
     *
     * @param color a given color we want to know which label it's corresponding
     * @return the label corresponding to color
     */
    private static String convertColorToLabel(Color color) { //Same thing
        if (color.equals(Color.green)) {
            return "A";
        } else if (color.equals(Color.red)) {
            return "D";
        } else if (color.equals(Color.white) || color.equals(Color.yellow)) {
            return "E";
        } else if (color.equals(Color.black)) {
            return "W";
        } else {
            return null;
        }
    }

    /**
     * Just a small method that the opposite color of the given label
     *
     * @param label the label we want to convert
     * @return The color black if label = "E", the color white otherwise
     */
    private static Color swapBlackAndWhite(String label) {
        //This method is only called twice and the param label will ONLY be equal to either "E" or "W"
        if (label.equals("E")) {
            return Color.black;
        } else {
            return Color.white;
        }
    }


    public void mouseClicked(MouseEvent e) { //The epic implementation
        //Do nothing
    }

    /**
     * Will do something based on what happened beforehand.
     * If the user does not want to edit departure or arrival, will transform the clicked case from empty
     * to full, and vice-versa.
     * If the user chose to edit the departure case or arrival case, the program will transform the clicked case on
     * respectively departure or arrival, and transform the former one into a regular empty case.
     * If both had been clicked, the program will arbitrary chose the last clicked one.
     *
     * @param e Someone clicked on an instance of MazeBox!
     */
    public void mousePressed(MouseEvent e) {
        MazeBox box = (MazeBox) e.getSource(); //We get the source of the clicked
        //Case 1: both set arrival and set departure are NOT clicked
        if (!window.getArrivalValue() && !window.getDepartureValue()) {
            box.swapEmptyAndWallBox();
        } else { //Case 2: at LEAST one of them had been clicked beforehand
            if (window.getDepartureValue()) { //Let's consider departure first
                box.changeDepartureCase();
            } else { //This time, it's arrival which is true
                //There are only 3 possibles cases anyway: either all 2 are false, either ONE ONLY is true
                box.changeArrivalCase();
            }
        }
        if (window.getAutoSolveValue()) {
            window.solveMaze();
            //We can put that here since only legal changes are allowed.
        }
    }

    public void mouseReleased(MouseEvent e) {
        //Do nothing
    }

    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    public void mouseExited(MouseEvent e) {
        //Do nothing
    }

    /**
     * If both "change departure case" and "change arrival case" are not clicked, will change the case the user clicked
     * on to empty if it's a wall and vice versa, which, in this method, is "this".
     */
    private void swapEmptyAndWallBox() {
        if (!window.getAvoidTiltValue()) {
            JOptionPane.showMessageDialog(window,
                    "I see you want to create a maze from scratch.\n" +
                            "Please understand that you should NOT enable\n auto-computing randomly. Have fun!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            window.setAvoidTiltValue(true);
        }
        if (this.getBoxLabel().equals("E") || this.getBoxLabel().equals("W")) {
            MazeBox otherColorBox = new MazeBox(swapBlackAndWhite(this.getBoxLabel()), this.window,
                    this.panelBoxes, this.getXCoordinate(), this.getYCoordinate());
            panelBoxes.setBox(otherColorBox, this.getXCoordinate(), this.getYCoordinate());
            this.setColor(swapBlackAndWhite(this.getBoxLabel()));
            window.updateMaze();
        } else {
            JOptionPane.showMessageDialog(window,
                    "Please only change empty cases to wall and vice-versa!",
                    "Illegal move detected", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Changes the new departure case to this.
     */
    private void changeDepartureCase() {
        int x, y;
        MazeBox mazeDepartureBox = window.searchForCaseFromLabel("D"); //Also displays the info message
        if (mazeDepartureBox != null) {
            x = mazeDepartureBox.getXCoordinate();
            y = mazeDepartureBox.getYCoordinate();
        } else {
            x = this.getXCoordinate();
            y = this.getYCoordinate();
        }//No new departure on wall or arrival!
        if (this.getBoxLabel().equals("E")) {
            panelBoxes.setBox(new MazeBox(Color.white, this.window, this.panelBoxes, x, y), x, y);
            this.setColor(Color.red);
            MazeBox newDepartureCase = new MazeBox(Color.red, this.window,
                    this.panelBoxes, this.getXCoordinate(), this.getYCoordinate());
            panelBoxes.setBox(newDepartureCase, this.getXCoordinate(), this.getYCoordinate());
            window.updateMaze();
            window.setDepartureValue(false);
            window.updatePathLengthTextBox();
        } else if (!this.getBoxLabel().equals("D") && !this.getBoxLabel().equals("E")) {
            JOptionPane.showMessageDialog(window, "Please select a valid departure case!",
                    "Illegal move detected", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Changes the new arrival case to this.
     */
    private void changeArrivalCase() {
        int x, y;
        MazeBox mazeArrivalBox = window.searchForCaseFromLabel("A"); //Also displays the info message
        if (mazeArrivalBox != null) {
            x = mazeArrivalBox.getXCoordinate();
            y = mazeArrivalBox.getYCoordinate();
        } else {
            x = this.getXCoordinate();
            y = this.getYCoordinate();
        }
        if (this.getBoxLabel().equals("E")) {
            panelBoxes.setBox(new MazeBox(Color.white, this.window, this.panelBoxes, x, y), x, y);
            this.setColor(Color.green);
            MazeBox newArrivalCase = new MazeBox(Color.green, this.window, this.panelBoxes,
                    this.getXCoordinate(), this.getYCoordinate());
            panelBoxes.setBox(newArrivalCase, this.getXCoordinate(), this.getYCoordinate());
            window.updateMaze();
            window.setArrivalValue(false);
            window.updatePathLengthTextBox();
        } else if (!this.getBoxLabel().equals("A") && !this.getBoxLabel().equals("E")) {
            JOptionPane.showMessageDialog(window, "Please select a valid arrival case!",
                    "Illegal move detected", JOptionPane.ERROR_MESSAGE);
        }
    }

}
