package org.drools.examples.sudoku;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Formatter;
import java.util.Set;
import java.util.logging.Logger;

public class Sudoku {
    public static Sudoku sudoku;
    private KieContainer kc;
    private KieSession session;
    private FactHandle steppingFactHandle;
    private Boolean explain = false;
    private Counter counter;
    Logger logger = Logger.getLogger(Sudoku.class.getName());

    public  Cell[][]    cells;
    private CellSqr[][] sqrs = new CellSqr[][]{ new CellSqr[3], new CellSqr[3], new CellSqr[3] };
    private CellRow[]   rows = new CellRow[9];
    private CellCol[]   cols = new CellCol[9];

    private Stepping stepping;
    private boolean unsolvable = false;

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
        this.create();

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
        logger.info("invoking fireUntilHalt();");
        session.fireUntilHalt();
        logger.info("finished executing fireUntilHalt();");
    }

    public void step() {
        if (this.isSolved()) return;
        explain = true;
        session.setGlobal("explain", explain);
        this.counter.setCount(1);
        session.update(session.getFactHandle(this.counter), this.counter);
        if( steppingFactHandle == null ){
            steppingFactHandle = session.insert( stepping = new Stepping() );
        }
        this.session.fireUntilHalt();
        if( stepping.isEmergency() ){
            this.unsolvable = true;
        }
        //dumpGrid();
    }

    public boolean isSolved() {
        for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                if (cells[iRow][iCol].getValue() == null) return false;
            }
        }
        return true;
    }

    private void create() {
        for (int i = 0; i < 9; i++) {
            //session.insert(Integer.valueOf(i+1));
            rows[i] = new CellRow(i);
            cols[i] = new CellCol(i);
        }

        cells = new Cell[9][];
        for (int iRow = 0; iRow < 9; iRow++) {
            cells[iRow] = new Cell[9];
            for (int iCol = 0; iCol < 9; iCol++) {
                Cell cell = cells[iRow][iCol] = new Cell();
                rows[iRow].addCell(cell);
                cols[iCol].addCell(cell);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sqrs[i][j] = new CellSqr(rows[i*3], rows[i*3+1], rows[i*3+2],
                        cols[j*3], cols[j*3+1], cols[j*3+2]);
            }
        }

        for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                cells[iRow][iCol].makeReferences(rows[iRow], cols[iCol], sqrs[iRow/3][iCol/3]);
                session.insert(cells[iRow][iCol]);
            }
            session.insert(rows[iRow]);
            session.insert(cols[iRow]);
            session.insert(sqrs[iRow/3][iRow%3]);
        }
    }
    /**
     * Nice printout of the grid.
     */
    public String dumpGrid() {
        StringBuilder dumpGridSb = new StringBuilder();
        Formatter fmt = new Formatter(dumpGridSb);
        fmt.format("       ");
        for (int iCol = 0; iCol < 9; iCol++) {
            fmt.format("Col: %d     ", iCol);
        }
        fmt.format("\n");
        for (int iRow = 0; iRow < 9; iRow++) {
            fmt.format("Row " + iRow + ": ");
            for (int iCol = 0; iCol < 9; iCol++) {
                if (cells[iRow][iCol].getValue() != null) {
                    fmt.format(" --- %d --- ", cells[iRow][iCol].getValue());
                } else {
                    StringBuilder sb = new StringBuilder();
                    Set<Integer> perms = cells[iRow][iCol].getFree();
                    for (int i = 1; i <= 9; i++) {
                        if (perms.contains(i)) {
                            sb.append(i);
                        } else {
                            sb.append(' ');
                        }
                    }
                    fmt.format(" %-10s", sb.toString());
                }
            }
            fmt.format("\n");
        }
        fmt.close();
        // System.out.println(dumpGridSb);
        return dumpGridSb.toString();
    }

    public boolean isUnsolvable(){
        return unsolvable;
    }

}