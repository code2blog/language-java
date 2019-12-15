package org.drools.examples.sudoku;

import org.drools.examples.sudoku.swing.SudokuGridSamples;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class SudokuTest {
    private Sudoku sudoku;
    private KieSession session;
    private KieContainer kc;

    @Test
    public void should_load_kie_session(){
        this.kc = KieServices.Factory.get().getKieClasspathContainer();
        sudoku = new Sudoku( kc );
        //
        Integer[][] sample = SudokuGridSamples.getInstance().getSample("Simple");
        sudoku.setCellValues(sample);
        sudoku.validate();
    }
}