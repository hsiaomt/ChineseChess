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
public class Minister extends Chess {

    public Minister(ChessButton cb) {
        super(cb);
    }
    
    @Override
    public boolean isIllegal(int region, int selectedRow, int selectedCol) {
        int rowDiff = Math.abs(selectedRow - row);
        int colDiff = Math.abs(selectedCol - col);
        int stuckRow, stuckCol;
        if (selectedRow - row < 0) stuckRow = row - 1;
        else stuckRow = row + 1;
        if (selectedCol - col < 0) stuckCol = col - 1;
        else stuckCol = col + 1;
        
        //選擇對面
        if (region != cb.getRegion()) return true;
        //不是走田字
        else if (rowDiff != 2 || colDiff != 2) return true;
        //卡住
        else if (chesses[0][stuckRow][stuckCol] != null) return true;
        
        return false;
    }
    
}
