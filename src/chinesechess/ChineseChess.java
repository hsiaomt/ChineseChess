/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class ChineseChess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GUIFrame guiFrame = new GUIFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(600, 700);
        guiFrame.setVisible(true);
    }
    
}
