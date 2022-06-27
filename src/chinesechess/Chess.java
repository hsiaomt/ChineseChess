/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.Color;
import javax.swing.JComponent;

/**
 *
 * @author user
 */
public abstract class Chess {
    
    ChessButton cb;
    int row;
    int col;
    
    public Chess(ChessButton cb) {
        this.cb = cb;
        this.row = cb.getRow();
        this.col = cb.getColumn();
    }
    
    public abstract boolean isIllegal(int region, int selectedRow, int selectedCol);
    
    public boolean canEat(int region, int selectedRow, int selectedCol) {
        return !isIllegal(region, selectedRow, selectedCol);
    }
    
    public int processRow(int row, int processor) {
        return (row + processor) * (int) Math.pow(-1, processor);
    }
    
    public void setCB(ChessButton cb) {
        this.cb = cb;
    }
    
    @Override
    public String toString() {
        return String.format("region: %d", cb.getRegion());
    }
    
}
