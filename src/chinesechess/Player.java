/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.Color;

/**
 *
 * @author user
 */
public class Player {
    
    private Color color;
    private String[] text;
    private ChessButton selected;
    private boolean isMyTurn;
    
    public Player(Color color, String[] text) {
        this.color = color;
        this.text = text;
        selected = null;
        
        
    }
    
    public void setSelected(ChessButton cb) {
        if (selected != null) {
            selected.setOpaque(false);
            selected.setBackground(null);
        }
        selected = cb;
        if (selected != null) {
            selected.setOpaque(true);
            selected.setBackground(new Color(125, 255, 120));
        }
    }
    
    public void setIsMyTurn(boolean b) {
        isMyTurn = b;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String[] getText() {
        return text;
    }
    
    public ChessButton getSelected() {
        return selected;
    }
    
    public boolean isMyTurn() {
        return isMyTurn;
    }
}
