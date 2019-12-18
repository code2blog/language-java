package org.drools.examples.sudoku;

/**
 * Created by 25423 on 12/12/2019.
 */
import org.drools.core.util.IoUtils;
import org.drools.examples.sudoku.swing.SudokuGridSamples;
import org.drools.examples.sudoku.swing.SudokuGridView;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class SudokuExample implements ActionListener {
    private JFrame mainFrame;
    private SudokuGridView sudokuGridView;
    private Sudoku sudoku;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu samplesMenu = new JMenu("Samples");
    private JMenuItem openMenuItem = new JMenuItem("Open...");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private BorderLayout borderLayout = new BorderLayout();
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
    private JPanel buttonPanel = new JPanel(flowLayout);
    private JButton solveButton = new JButton("Solve");
    private JButton stepButton  = new JButton("Step");
    private JButton dumpButton  = new JButton("Dump");
    private JFileChooser fileChooser;

    public static void main(String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        new SudokuExample().init(kc, true);
    }

    public void init(KieContainer kc, boolean exitOnClose) {
        mainFrame = new JFrame("Sudoku Example");
        for (String sampleName : SudokuGridSamples.getInstance().getSampleNames()){
            JMenuItem menuItem = new JMenuItem(sampleName);
            menuItem.addActionListener(this);
            samplesMenu.add(menuItem);
        }
        fileMenu.add(samplesMenu);
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);
        sudokuGridView = new SudokuGridView();

        sudoku = new Sudoku( kc );

        mainFrame.setLayout(borderLayout);
        mainFrame.add(BorderLayout.CENTER, sudokuGridView);

        buttonPanel.add(solveButton);
        solveButton.addActionListener(this);
        buttonPanel.add(stepButton);
        stepButton.addActionListener(this);
        buttonPanel.add(dumpButton);
        buttonsActive( false );
        dumpButton.addActionListener(this);
        mainFrame.add(BorderLayout.SOUTH, buttonPanel);
        mainFrame.setSize(400,400);
        mainFrame.setLocationRelativeTo(null); // Center in screen
        mainFrame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable( false );
        mainFrame.setVisible(true);
        //TODO: sudokuGridView.setModel(sudoku);
    }

    private void buttonsActive(boolean active) {
        solveButton.setEnabled(false);
        stepButton.setEnabled(active);
        dumpButton.setEnabled(active);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource().equals(stepButton)) {
            sudoku.step();
            if (sudoku.isSolved() || sudoku.isUnsolvable()) buttonsActive(false);
            if (sudoku.isUnsolvable()) {
                sudoku.dumpGrid();
                System.out.println("Sorry - can't solve this grid.");
            }
        }
        if (ev.getSource().equals(dumpButton)) {
            System.out.println(sudoku.dumpGrid());
        }
        if (ev.getSource().equals(exitMenuItem)) {
            if (mainFrame.getDefaultCloseOperation() == WindowConstants.EXIT_ON_CLOSE) {
                System.exit(0);
            } else {
                mainFrame.dispose();
            }
        }
        if (ev.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) ev.getSource();
            Integer[][] sample = SudokuGridSamples.getInstance().getSample(menuItem.getText());
            sudoku.setCellValues(sample);
//            sudoku.validate();
            buttonsActive(true);
        }
    }
}
