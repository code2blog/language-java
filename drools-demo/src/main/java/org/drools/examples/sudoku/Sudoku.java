package org.drools.examples.sudoku;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Sudoku {
    public static Sudoku sudoku;
    private KieContainer kc;

    public Sudoku(KieContainer kc) {
        this.kc = kc;
        sudoku = this;
    }
}