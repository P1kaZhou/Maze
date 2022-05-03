package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

//One of the controllers
public class MazeMenuBar extends JMenuBar {

    private final MazeWindow window;

    /**
     * Instantiates MazeMenuBar which can be seen at the top of the window
     *
     * @param window            the window the user sees
     * @param load              the load button
     * @param save              the save button
     * @param setDeparture      the change departure case button
     * @param setArrival        the change arrival case button
     * @param solve             the JMenuItem used to solve maze
     * @param reset             the reset maze button
     * @param autoSolve         a JCheckBoxMenuItem to enable auto solving (solves the maze upon being clicked as well)
     * @param customDimensions  the JMenuItem that will be the way to change the maze into a new one with custom dimensions
     * @param helpButton        a JButton that ask if the user wants to open README.md in their browser
     * @param pathLengthTextBox a JTextField that indicates to the user the length of the shortest path found (after solving)
     */
    public MazeMenuBar(MazeWindow window, JMenuItem load, JMenuItem save, JMenuItem setDeparture,
                       JMenuItem setArrival, JMenuItem solve, JMenuItem reset, JCheckBoxMenuItem autoSolve,
                       JMenuItem customDimensions, JButton helpButton, JTextField pathLengthTextBox) {
        //The modifications are done in MazeWindow
        super(); //We create the JMenuBar
        this.window = window; //So we know that we have to add the elements to window
        helpButton.setMargin(new Insets(0, 0, 0, 0)); //So the button looks nice
        helpButton.setBorder(null);
        //The 3 menus
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenu menuMaze = new JMenu("Maze");

        //The submenus
        menuFile.add(load);
        menuFile.add(save);
        menuFile.add(autoSolve);
        menuEdit.add(setDeparture);
        menuEdit.add(setArrival);
        menuEdit.add(reset);
        menuEdit.add(customDimensions);
        menuMaze.add(solve);
        menuMaze.add(autoSolve);

        this.add(menuFile);
        this.add(menuEdit);
        this.add(menuMaze);
        this.add(helpButton);
        this.add(pathLengthTextBox);

        //Every submenu upon clicking will generate an action
        //Every icon is self-explanatory here
        load.addActionListener(e -> this.window.stateChanged(new ChangeEvent(load)));

        save.addActionListener(e -> this.window.stateChanged(new ChangeEvent(save)));

        setDeparture.addActionListener(e -> this.window.stateChanged(new ChangeEvent(setDeparture)));

        setArrival.addActionListener(e -> this.window.stateChanged(new ChangeEvent(setArrival)));

        solve.addActionListener(e -> this.window.stateChanged(new ChangeEvent(solve)));

        reset.addActionListener(e -> this.window.stateChanged(new ChangeEvent(reset)));

        autoSolve.addActionListener(e -> this.window.stateChanged(new ChangeEvent(autoSolve)));

        customDimensions.addActionListener(e -> this.window.stateChanged(new ChangeEvent(customDimensions)));

        helpButton.addActionListener((e -> this.window.stateChanged(new ChangeEvent(helpButton))));
    }
}
