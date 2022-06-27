/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import static chinesechess.GamePanel.chesses;
import java.awt.Color;
import javax.swing.JComponent;

/**
 *
 * @author user
 */
public class Knight extends Chess {

    public Knight(ChessButton cb) {
        super(cb);
    }
    
    @Override
    public boolean isIllegal(int region, int selectedRow, int selectedCol) {
        int regionDiff = Math.abs(region - cb.getRegion());
        int rowDiff = Math.abs(processRow(selectedRow, regionDiff) - row);
        int colDiff = Math.abs(selectedCol - col);
        int stuckRow, stuckCol, r, sr;
        r = processRow(row, cb.getRegion());
        sr = processRow(selectedRow, region);
        if (sr < r) stuckRow = r - (rowDiff - 1);
        else stuckRow = r + (rowDiff - 1);
        if (selectedCol < col) stuckCol = col - (colDiff - 1);
        else stuckCol = col + (colDiff - 1);
        
        //不是走日字
        if (rowDiff*colDiff != 2) return true;
        //卡住
        else if (stuckRow >= 0 && chesses[0][stuckRow][stuckCol] != null) return true;
        else if (stuckRow < 0 && chesses[1][processRow(stuckRow, 1)][stuckCol] != null) return true;
        
        return false;
    }
    
}
