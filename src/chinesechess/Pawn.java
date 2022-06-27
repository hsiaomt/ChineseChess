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
public class Pawn extends Chess {
    
    boolean isCrossRiver;
    int player;

    public Pawn(ChessButton cb) {
        super(cb);
    }
    
    public Pawn(ChessButton cb, int player) {
        super(cb);
        this.player = player;
        System.out.println(this);
    }
    
    @Override
    public boolean isIllegal(int region, int selectedRow, int selectedCol) {
        int regionDiff = Math.abs(region - cb.getRegion());
        int rowDiff = Math.abs(processRow(selectedRow, regionDiff) - row);
        int colDiff = Math.abs(selectedCol - col);
        if (region != player) isCrossRiver = true;
        
        //不是走直線或不是走一格
        if (rowDiff*colDiff != 0 || rowDiff > 1 || colDiff > 1) return true;
        //直
        else if (rowDiff > 0) {
            //往後走
            if (player == 0) {
                if (processRow(selectedRow, region) > processRow(row, cb.getRegion())) 
                    return true;
            } else {
                if (processRow(selectedRow, region) < processRow(row, cb.getRegion())) 
                    return true;
            }
        } 
        //橫但不是在對面
        else if (colDiff > 0 && !isCrossRiver) return true;
//        System.out.println("false");
        return false;
    }
    
}
