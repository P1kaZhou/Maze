package ui;

import dijkstra.PreviousInterface;
import dijkstra.VertexInterface;
import maze.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static dijkstra.Dijkstra.dijkstraPrevious;
import static java.lang.Integer.parseInt;

//The view
//todo: fix the non-correlation between the model and the view
//todo: add more methods to made the long methods shorter
public class MazeWindow extends JFrame implements ChangeListener {

    private MainPanel mainPanel;
    private MazePanel mazePanel;
    private JMenuItem load;
    private JMenuItem save;
    private JMenuItem setDeparture;
    private JMenuItem setArrival;
    private JMenuItem solve;
    private JMenuItem reset;
    private JCheckBoxMenuItem autoSolve;
    private JMenuItem customDimensions;
    private JButton helpButton;
    private JTextField pathLengthTextBox;

    //Integers changing the size of the maze when the user inputs custom height and/or width
    private int customHeight = 10; //By default
    private int customWidth = 10; //Default as well

    //An Integer variable which is the length of the shortest path available
    private int pathLength = 0;

    //Self-explanatory booleans, respectively to check if the user wants to change the departure case, the arrival case
    // or to enable auto-computing.
    private boolean isDepartureClicked = false;
    private boolean isArrivalClicked = false;
    private boolean isAutoSolveClicked = false;

    /**
     * Just a boolean that insures that the message box "oh you're customizing a maze" is only displayed once.
     */
    private boolean avoidTilt = false;

    /**
     * It's an array of arrays of MazeBox that will follow every update made by the user and update itself, as the Maze
     * class has no clue of what's happening besides receiving things to do.
     */
    //We save the interface thing here, as modifications will be done, which the controller doesn't know
    private PanelBoxes panelBoxes;

    private static final String gitlabReadmeLink =
            "https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/README.md";

    /**
     * Creates a new window with title as a title.
     *
     * @param title the title of the window
     */
    //The constructor
    public MazeWindow(String title) {
        //The title
        super(title);
        setupUI();

        //No resize allowed
        this.setResizable(false);
        //We need this!
        this.setVisible(true);
    }

    /**
     * Sets up every window component in the window, like the dimensions or what happens after closing it.
     */
    private void setupUI() { //In which we set up all the essential parts of the interface

        //Setting the size of the window
        this.setPreferredSize(new Dimension(550, 550)); //Arbitrary preferred size
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setupMenuBar();
        setupMaze(10, 10); //Initial arbitrary values
        this.pack();
    }

    /**
     * Sets up the whole JMenuBar, visible from the window.
     */
    private void setupMenuBar() { //The menu bar is the core part, mother of all modifications of the maze
        load = new JMenuItem("Load maze");
        save = new JMenuItem("Save current maze");
        setDeparture = new JMenuItem("Change departure case");
        setArrival = new JMenuItem("Change arrival case");
        solve = new JMenuItem("Solve maze");
        reset = new JMenuItem("Reset maze");
        autoSolve = new JCheckBoxMenuItem("Enable auto-computing?");
        customDimensions = new JMenuItem("Setup a custom maze");
        ImageIcon icon = new ImageIcon("data/help.png");
        Image image = icon.getImage();
        Image help = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(help);
        helpButton = new JButton(icon); //Custom image for the help button
        pathLengthTextBox = new JTextField("Unknown length, maze has not been solved yet");
        pathLengthTextBox.setMaximumSize(pathLengthTextBox.getPreferredSize());
        pathLengthTextBox.setEditable(false); //Uneditable text
        pathLengthTextBox.setBorder(null); //Non-visible borders
        pathLengthTextBox.setOpaque(false); //Transparent "background"
        pathLengthTextBox.setHighlighter(null); //Unselectable text
        pathLengthTextBox.setHorizontalAlignment(JTextField.RIGHT);
        //Because all modifications are done within MazeWindow
        JMenuBar bar = new MazeMenuBar(this, load, save, setDeparture, setArrival, solve,
                reset, autoSolve, customDimensions, helpButton, pathLengthTextBox);
        this.setJMenuBar(bar);
    }

    /**
     * Updates the view and mainPanel everytime the user does something that (obviously) changes of them
     */
    public void updateMaze() {
        remove(mainPanel);
        this.mainPanel = new MainPanel();
        this.mazePanel = new MazePanel(this.panelBoxes.getPanelBoxesHeight(), this.panelBoxes.getPanelBoxesWidth());
        for (int i = 0; i < this.panelBoxes.getPanelBoxesHeight(); i++) {
            for (int j = 0; j < this.panelBoxes.getPanelBoxesWidth(); j++) {
                MazeBox box = this.panelBoxes.getPanelBoxes(i, j);
                mazePanel.add(box);
            }
        }
        mainPanel.add(mazePanel);
        this.add(mainPanel);
        repaint();
        revalidate();
    }

    /**
     * Initializes the user view with height x width white squares
     *
     * @param height The height of the maze
     * @param width  The width of the maze
     */
    private void setupMaze(int height, int width) { //Displays an empty maze first
        this.mainPanel = new MainPanel();
        this.mazePanel = new MazePanel(height, width);
        this.panelBoxes = new PanelBoxes(height, width); //Thus no "magic numbers"!
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MazeBox box = new MazeBox(Color.white, this, this.panelBoxes, i, j);
                mazePanel.add(box);
                this.panelBoxes.setBox(box, i, j);
            }
        }
        mainPanel.add(mazePanel);
        this.add(mainPanel);
    }

    /**
     * Returns isDepartureClicked value.
     *
     * @return a boolean corresponding to whether the user had clicked "Changed departure case" beforehand or not
     */
    public boolean getDepartureValue() {
        return isDepartureClicked;
    }

    /**
     * Changes isDepartureClicked value to departureValue.
     *
     * @param departureValue the new value of isDepartureValue
     */
    public void setDepartureValue(boolean departureValue) {
        this.isDepartureClicked = departureValue;
    }

    /**
     * Returns isArrivalClicked value.
     *
     * @return a boolean corresponding to whether the user had clicked "Changed arrival case" beforehand or not
     */
    public boolean getArrivalValue() {
        return isArrivalClicked;
    }

    /**
     * Changes isArrivalClicked value to ArrivalValue.
     *
     * @param arrivalValue the new value of isArrivalValue
     */
    public void setArrivalValue(boolean arrivalValue) {
        this.isArrivalClicked = arrivalValue;
    }

    /**
     * Returns a boolean corresponding to whether the auto-computing button is enabled
     *
     * @return the value of isAutoSolveClicked
     */
    public boolean getAutoSolveValue() {
        return isAutoSolveClicked;
    }

    /**
     * Returns the value of avoidTilt
     *
     * @return the current value of avoidTilt
     */
    public boolean getAvoidTiltValue() {
        return avoidTilt;
    }

    /**
     * Set the current value of avoidTilt to its new value
     *
     * @param avoidTilt the new value of the current avoidTilt
     */
    public void setAvoidTiltValue(boolean avoidTilt) {
        this.avoidTilt = avoidTilt;
    }

    /**
     * Searches for a mazeBox with the label corresponding to the one in argument, and returns it
     *
     * @param label the label looked for
     * @return a (unique, hopefully) MazeBox with the corresponding label
     */
    public MazeBox searchForCaseFromLabel(String label) {
        for (int i = 0; i < this.panelBoxes.getPanelBoxesHeight(); i++) {
            for (int j = 0; j < this.panelBoxes.getPanelBoxesWidth(); j++) {
                if (this.panelBoxes.getPanelBoxes(i, j).getBoxLabel().equals(label)) {
                    return this.panelBoxes.getPanelBoxes(i, j);
                }
            }
        }
        if (!avoidTilt) {
            JOptionPane.showMessageDialog(this,
                    "I see you want to create a maze from scratch.\n" +
                            "Please understand that you should NOT enable\n" +
                            "auto-computing randomly. Have fun!", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            avoidTilt = true;
        }
        return null;
    }

    /**
     * Tells the window what to do after something happened to the JBarMenu.
     * If the load maze menu item is clicked, the user will have to open a .txt file to load the corresponding maze,
     * but only if the maze is legal.
     * If the save maze menu item is clicked, the program will ask the user to input a file name and save the maze
     * with the corresponding name, but only if the maze is legal.
     * If the change departure case is clicked, the program will wait for the user to click on a valid case to set the
     * new departure case there.
     * Same goes after clicking on the change arrival case, but this time with the arrival case
     * Solves the current displayed maze after clicking on the solve maze button, and displays a path.
     * Resets the current maze after clicking on the reset maze button, and displays a blank maze afterwards.
     * Asks for new dimensions if user clicked on "set custom dimensions"
     * Enable auto-computing if clicking on autoSolve
     *
     * @param e the event being the origin of the change
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == load) { //We are loading a new maze
            loadMaze();
        } else if (e.getSource() == save) { // Let the user choose the name of the file
            saveMaze();
        } else if (e.getSource() == setDeparture) {
            changeDepartureCaseHasBeenClicked();
        } else if (e.getSource() == setArrival) {
            changeArrivalCaseHasBeenClicked();
        } else if (e.getSource() == solve) { //Solving maze
            solveMaze();
        } else if (e.getSource() == reset) {
            resetMaze();
        } else if (e.getSource() == customDimensions) {
            askDimensions();
        } else if (e.getSource() == autoSolve) {
            this.isAutoSolveClicked = autoSolve.getState();
            if (isAutoSolveClicked) {
                solveMaze();
            }
        } else if (e.getSource() == helpButton) {
            openHelpMenu();
        }
    }

    /**
     * Launch a window asking for the user to open a .txt file and shows it (cf stateChanged Javadoc)
     */
    private void loadMaze() {
        setArrivalValue(false); //Thus, we're avoiding false manipulations
        setDepartureValue(false);
        setAvoidTiltValue(true);
        JFileChooser fc = new JFileChooser("./data"); //Saves a couple of clicks
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
        fc.setFileFilter(filter); //Thus, we will only see files ending with .txt
        fc.setDialogTitle("Load maze");
        int result = fc.showDialog(this, "Load maze");
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            Maze maze = new Maze(1, 1); //Unfortunately we have to read the file first
            maze.setParametersFromTextFile(path);
            int mazeHeight = maze.getMazeHeight();
            int mazeWidth = maze.getMazeWidth();
            maze = new Maze(mazeHeight, mazeWidth);
            try {
                maze.initFromTextFile(path); //May throw exceptions
                remove(this.mainPanel); //New panel
                this.mainPanel = new MainPanel();
                MazePanel mazeGrid = new MazePanel(mazeHeight, mazeWidth);
                this.panelBoxes = new PanelBoxes(mazeHeight, mazeWidth);
                for (int i = 0; i < mazeHeight; i++) {
                    for (int j = 0; j < mazeWidth; j++) {
                        String mazeLabel = maze.getLabelFromCoords(i, j);
                        MazeBox box = new MazeBox(MazeBox.convertLabelToColor(mazeLabel), this,
                                this.panelBoxes, i, j);
                        mazeGrid.add(box);
                        this.panelBoxes.setBox(box, i, j);
                    }
                }
                if (isAutoSolveClicked) {
                    solveMaze(); //Saves two (!) clicks
                } else {
                    this.pathLength = 0;
                    this.updatePathLengthTextBox();
                    mainPanel.add(mazeGrid);
                    this.add(mainPanel);
                    repaint(); //Open maze
                    revalidate();
                }
                this.customHeight = mazeHeight;
                this.customWidth = mazeWidth;
            } catch (MazeReadingException e) {
                JLabel errorMessage = new JLabel("<html>Illegal file detected. Please make sure that " +
                        "the file has one, <br>and only one occurrence of \"D\" and \"A\" , as well as " +
                        "no other illegal characters.</html>");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog(this, errorMessage, "Illegal file detected", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Make sure the file exists", "File error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Launch a window asking for the user to input a file name to save the current maze.
     */
    private void saveMaze() {
        Maze maze = new Maze(this.panelBoxes.getPanelBoxesHeight(), this.panelBoxes.getPanelBoxesWidth());
        try {
            maze.readFromMazeBoxArray(this.panelBoxes);
            JFileChooser fileChooser = new JFileChooser("./data");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
            fileChooser.setFileFilter(filter);
            int option = fileChooser.showDialog(this, "Save current maze");
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile() + ".txt");
                String filePath = file.getPath();
                if (file.isFile()) {
                    int result = JOptionPane.showConfirmDialog(this, "The file " + filePath +
                                    "\n already exists. " + "Would you like to overwrite it?", "Existing file",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    this.pack();
                    if (result == JOptionPane.OK_OPTION) {
                        maze.saveToTextFile(filePath);
                    }
                } else {
                    maze.saveToTextFile(filePath);
                }
            }
        } catch (MazeIssueException ex) {
            JOptionPane.showMessageDialog(this, "Please only save a maze with a departure and a arrival case",
                    "Illegal maze saving attempt detected", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solves the current maze displayed at the screen, assuming it's a legal and solvable one
     */
    public final void solveMaze() {
        this.pathLength = 0;
        Maze maze = new Maze(this.panelBoxes.getPanelBoxesHeight(), this.panelBoxes.getPanelBoxesWidth());
        try {
            maze.readFromMazeBoxArray(this.panelBoxes); //May throw MazeIssueException
            VertexInterface departure = maze.getDeparture();
            VertexInterface arrival = maze.getArrival();
            // The maze has an arrival and departure case, but it's not sufficient
            PreviousInterface parentList = dijkstraPrevious(maze, departure); //We get the main source to find the path
            ArrayList<VertexInterface> reversePath = new ArrayList<>();
            VertexInterface lookingForDeparture = parentList.getPrevious(arrival); //It can be null hence the next thing
            if (lookingForDeparture == null) { //This implies that this vertex has no parent, which means no path...
                throw new MazeSolvingException();
            } else {
                while (!lookingForDeparture.equals(departure)) {
                    //This method will NOT produce a NullPointerException because
                    // if arrival has a predecessor, it means the latter also has one and so on... until departure
                    reversePath.add(lookingForDeparture);
                    lookingForDeparture = parentList.getPrevious(lookingForDeparture);
                    this.pathLength++;
                }
                this.pathLength++; //Because there's one more case to reach!
                this.updatePathLengthTextBox(); //Updating displayed length
                autoSolve.setState(isAutoSolveClicked);
                remove(this.mainPanel);
                this.mainPanel = new MainPanel();
                MazePanel mazeGrid = new MazePanel(this.panelBoxes.getPanelBoxesHeight(),
                        this.panelBoxes.getPanelBoxesWidth());
                for (int i = 0; i < this.panelBoxes.getPanelBoxesHeight(); i++) {
                    for (int j = 0; j < this.panelBoxes.getPanelBoxesWidth(); j++) {
                        String mazeLabel = maze.getLabelFromCoords(i, j);
                        if (reversePath.contains(maze.getVertex(i, j))) {
                            MazeBox box = new MazeBox(Color.yellow, this, this.panelBoxes, i, j);
                            //Yellow is the color of one of the shortest paths.
                            mazeGrid.add(box);
                            this.panelBoxes.setBox(box, i, j);
                        } else { //normal routine
                            MazeBox box = new MazeBox(MazeBox.convertLabelToColor(mazeLabel),
                                    this, this.panelBoxes, i, j);
                            mazeGrid.add(box);
                            this.panelBoxes.setBox(box, i, j);
                        }
                    }
                }
                mainPanel.add(mazeGrid);
                this.add(mainPanel);
                repaint(); //Clean
                revalidate();
            }
        } catch (MazeSolvingException ex) { //When the maze is "valid" but there is no path
            JOptionPane.showMessageDialog(this,
                    "No path found, make sure your maze is valid", "Maze Warning", JOptionPane.ERROR_MESSAGE);
        } catch (MazeIssueException ex) { //When the maze is invalid so there is no need to search further
            JOptionPane.showMessageDialog(this,
                    "No arrival and/or departure case found", "Maze Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the text box and "prepares" for a new departure case.
     */
    private void changeDepartureCaseHasBeenClicked() {
        if (isArrivalClicked) {
            setArrivalValue(false);
        }
        setDepartureValue(true);
        this.setPathLengthTextBoxInfo();
    }

    /**
     * Updates the text box and "prepares" for a new arrival case.
     */
    private void changeArrivalCaseHasBeenClicked() {
        if (isDepartureClicked) {
            setDepartureValue(false);
        }
        setArrivalValue(true);
        this.setPathLengthTextBoxInfo();
    }

    /**
     * Resets the current maze, by making all cases empty, with its dimensions equal to the previous maze.
     */
    private void resetMaze() {
        autoSolve.setState(false);
        this.isAutoSolveClicked = false;
        this.pathLength = 0;
        this.updatePathLengthTextBox();
        remove(this.mainPanel);
        setupMaze(this.customHeight, this.customWidth);
        repaint();
        revalidate();
    }

    /**
     * Ask the user to input a new maze width and height. Won't allow illegal inputs (fortunately).
     * Cf README.md and stateChanged javadoc.
     */
    private void askDimensions() {
        JTextField widthField = new JTextField("10");
        JTextField heightField = new JTextField("10");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter width here"));
        panel.add(widthField);
        panel.add(new JLabel("Enter height here"));
        panel.add(heightField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Set custom dimensions",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String widthString = widthField.getText();
            String heightString = heightField.getText();
            assertParsableAndAboveOne(widthString, heightString);
        }
    }

    /**
     * Verifies that the strings the user inputted are parsable, and at least above 1 is yes.
     * If not, opens an error window.
     *
     * @param widthString  the custom maze width the user input
     * @param heightString the custom maze height the user input
     */
    private void assertParsableAndAboveOne(String widthString, String heightString) {
        try {
            if (parseInt(widthString) < 1 || parseInt(heightString) < 1) {
                JOptionPane.showMessageDialog(this, "Illegal input, please make sure both width and height are above 1",
                        "Input error", JOptionPane.ERROR_MESSAGE); //The custom values are therefore not modified
            } else {
                customWidth = Math.min(parseInt(widthString), 70);
                customHeight = Math.min(parseInt(heightString), 70);
                remove(this.mainPanel);
                this.setupMaze(customHeight, customWidth);
                this.updateMaze();
                autoSolve.setState(false);
                this.isAutoSolveClicked = false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Illegal input, please make sure both width and height are parsable",
                    "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Opens a window asking for whether the user wants to read README.md in their browser and lets them know
     * that their IDE can read it as well.
     */
    private void openHelpMenu() {
        Object[] yesNoList = {"Open browser", "No, I'm fine"};
        int result = JOptionPane.showOptionDialog(this,
                "Please refer to the README.md document for all information. \n" +
                        "Do you want to read it in your browser?\nYou can also read it from your IDE.",
                "Need help?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, yesNoList, yesNoList[0]);
        if (result == JOptionPane.OK_OPTION && Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(gitlabReadmeLink));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Updates the text box
     */
    public void updatePathLengthTextBox() {
        if (this.pathLength > 1) {
            this.pathLengthTextBox.setText("Shortest path length: " + this.pathLength + " cases");
        } else if (this.pathLength == 1) { //When the conjugation is too important
            this.pathLengthTextBox.setText("Shortest path length: 1 case");
        } else {
            this.pathLengthTextBox.setText("Unknown length, maze has not been solved yet");
        }
    }

    /**
     * Let the user know what the next step "should be".
     * If the user has clicked on "set departure case", the text box will display "Waiting for a new departure case...".
     * Same goes for "set arrival case", the text box will display "Waiting for a new arrival case..."
     */
    public void setPathLengthTextBoxInfo() {
        if (isDepartureClicked) {
            this.pathLengthTextBox.setText("Waiting for a new departure case...");
        } else if (isArrivalClicked) {
            this.pathLengthTextBox.setText("Waiting for a new arrival case...");
        }
    }
}
