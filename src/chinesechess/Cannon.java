/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import static chinesechess.GamePanel.chesses;
import java.awt.Color;

/**
 *
 * @author user
 */
public class Cannon extends Chess {
    
    int count;

    public Cannon(ChessButton cb) {
        super(cb);
    }
    
    @Override
    public boolean isIllegal(int region, int selectedRow, int selectedCol) {
        int regionDiff = Math.abs(region - cb.getRegion());
        int rowDiff = Math.abs(processRow(selectedRow, regionDiff) - row);
        int colDiff = Math.abs(selectedCol - col);
        
        //不是走直線
        if (rowDiff*colDiff != 0) return true;
        //直
        else if (rowDiff > 0) {
            //沒有跨區
            if (regionDiff == 0) {
                for (int i = Math.min(row, selectedRow)+1; i < Math.max(row, selectedRow); i++) 
                    //中間有棋子
                    if (chesses[region][i][col] != null) return true;
            } else { 
            //有跨區
                for (int i = 0; i < row; i++) 
                    //中間有棋子
                    if (chesses[cb.getRegion()][i][col] != null) return true;
                for (int i = 0; i < selectedRow; i++) 
                    //中間有棋子
                    if (chesses[region][i][col] != null) return true;
            }
        } 
        //橫
        else if (colDiff > 0) {
            for (int i = Math.min(col, selectedCol)+1; i < Math.max(col, selectedCol); i++) 
                //中間有棋子
                if (chesses[region][row][i] != null) return true;
        }

        return false;
    }

    @Override
    public boolean canEat(int region, int selectedRow, int selectedCol) {
        count = 0;
        int regionDiff = Math.abs(region - cb.getRegion());
        int rowDiff = Math.abs(processRow(selectedRow, regionDiff) - row);
        int colDiff = Math.abs(selectedCol - col);
        
        //不是走直線
        if (rowDiff*colDiff != 0) return false;
        //直
        else if (rowDiff > 0) {
            //沒有跨區
            if (regionDiff == 0) {
                for (int i = Math.min(row, selectedRow)+1; i < Math.max(row, selectedRow); i++)
                    //中間有棋子
                    if (chesses[region][i][col] != null && isCountExceed()) return false;
            } else { 
            //有跨區
                for (int i = 0; i < row; i++) 
                    //中間有棋子
                    if (chesses[cb.getRegion()][i][col] != null && isCountExceed()) return false;
                for (int i = 0; i < selectedRow; i++) 
                    //中間有棋子
                    if (chesses[region][i][col] != null && isCountExceed()) return false;
            }
        } 
        //橫
        else if (colDiff > 0) {
            for (int i = Math.min(col, selectedCol)+1; i < Math.max(col, selectedCol); i++) 
                //中間有棋子
                if (chesses[region][row][i] != null && isCountExceed()) return false;
        }

        return count == 1;
    }
    
    private boolean isCountExceed() {
        count++;
        return count > 1;
    }
    
}
