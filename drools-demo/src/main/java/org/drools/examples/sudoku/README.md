---
title: "Sample yaml file"
output:
  ioslides_presentation:
  toc: yes
widescreen: yes
---
<style>
/* div div { 
  font-size: 6px;
} */
</style>

## Documentation for Sudoku.java
----


Use below links for console logs.        
* [rule - set a value](notes-console-logs-rule-set-a-value.txt)   
* [rule - eliminate a value from cell](notes-console-logs-rule-eliminate-a-value-from-cell.txt)   

<pre style="font-size: 12px;">
sudoku.setCellValues(sample); 
-> this.create();
       // session.insert(Integer.valueOf(i+1));
       session.insert(cells[iRow][iCol]); // Cell[][]
       session.insert(rows[iRow]); // CellRow[] 
       session.insert(cols[iRow]); // CellCol[]
       session.insert(sqrs[iRow/3][iRow%3]); // CellSqr[][] 
session.insert(new Setting(iRow, iCol, value));
this.session.insert(counter);

Counter value will be set as size(cells 9*9 )
Decrease counter value with [rule "set a value"] and halt when counter == 0
</pre>
       

