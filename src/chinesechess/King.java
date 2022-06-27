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
public class King extends Chess {

    public King(ChessButton cb) {
        super(cb);
    }
    
    @Override
    public boolean isIllegal(int region, int selectedRow, int selectedCol) {
        int rowDiff = Math.abs(selectedRow - row);
        int colDiff = Math.abs(selectedCol - col);
        
        //選擇對面
        if (region != cb.getRegion()) return true;
        //離開九宮格
        else if (selectedRow < 2 || selectedCol < 3 || selectedCol > 5) return true;
        //不是走直線或不是走一格
        else if (rowDiff*colDiff != 0 || rowDiff > 1 || colDiff > 1) return true;
        
        return false;
    }
    
}
