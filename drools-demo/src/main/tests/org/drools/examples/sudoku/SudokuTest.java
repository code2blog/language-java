package org.drools.examples.sudoku;

import org.drools.examples.sudoku.swing.SudokuGridSamples;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class SudokuTest {
    private Sudoku sudoku;
    private KieSession session;
    private KieContainer kc;
    ExecutorService executor = Executors.newCachedThreadPool();
    Logger logger = Logger.getLogger(SudokuTest.class.getName());

    @Test
    public void should_load_kie_session() {
        this.kc = KieServices.Factory.get().getKieClasspathContainer();
        sudoku = new Sudoku(kc);
        //
        Integer[][] sample = SudokuGridSamples.getInstance().getSample("Simple");
        sudoku.setCellValues(sample);
    }

    @Test
    public void should_invoke_step_without_validate() throws Exception {
        this.kc = KieServices.Factory.get().getKieClasspathContainer();
        sudoku = new Sudoku(kc);
        //
        Integer[][] sample = SudokuGridSamples.getInstance().getSample("Simple");
        sudoku.setCellValues(sample);
        //sudoku.validate();
        String grid = "";
        int maxAttempts = 300;
        while(maxAttempts-- > 0){
            logger.info("step()");
            sudoku.step();
            String currentGrid = sudoku.dumpGrid();
            if(grid.equalsIgnoreCase(currentGrid)){
                logger.info("no change in grid after step " + maxAttempts);
                break;
            }
            grid = currentGrid;
        }
        System.out.println(grid);
        logger.info("end");
    }
}