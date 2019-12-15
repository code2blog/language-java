package org.drools.examples.sudoku;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Sudoku {
    public static Sudoku sudoku;
    private KieContainer kc;
    private KieSession session;
    private FactHandle steppingFactHandle;
    private Boolean explain = false;
    private Counter counter;

    public Sudoku(KieContainer kc) {
        this.kc = kc;
        sudoku = this;
    }

    public void setCellValues(Integer[][] cellValues) {
        if (session != null) {
//            session.removeEventListener(workingMemoryListener);
            session.dispose();
            steppingFactHandle = null;
        }

        this.session = kc.newKieSession("SudokuKS");
        session.setGlobal("explain", explain);
//        session.addEventListener(workingMemoryListener);

        Setting s000 = new Setting(0, 0, 0);
        FactHandle fh000 = this.session.insert(s000);

        int initial = 0;
        for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                Integer value = cellValues[iRow][iCol];
                if (value != null) {
                    session.insert(new Setting(iRow, iCol, value));
                    initial++;
                }
            }
        }
        this.counter = new Counter(initial);
        this.session.insert(counter);
        this.session.delete(fh000);
        this.session.fireAllRules();

    }

    public void validate(){
        session.getAgenda().getAgendaGroup( "validate" ).setFocus();
        session.fireUntilHalt();
    }

}