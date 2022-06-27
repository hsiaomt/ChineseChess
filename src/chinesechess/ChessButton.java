/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import static chinesechess.GamePanel.gridWidth;
import static chinesechess.GamePanel.gridHeight;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import static chinesechess.GamePanel.locations;
import java.awt.BorderLayout;

/**
 *
 * @author user
 */
public class ChessButton extends JButton {
    
    private static double proportion = 0.8;
    
    private int region;
    private int row;
    private int column;
    private String text;
    private Color color;
    
    private ImageIcon chessImg;
    
    public ChessButton(int region, int row, int column, String text, Color color) {
        this.region = region;
        this.row = row;
        this.column = column;
        this.text = text;
        this.color = color;
        setImage(text);
        
        setBorderPainted(false);
        setBackground(null);
        setOpaque(false);
//        setContentAreaFilled(false);
//        Border blackline = BorderFactory.createLineBorder(Color.RED);
//        setBorder(blackline);
//        addActionListener(e -> System.out.println("clicked"));
        //repaint();
        
//        addActionListener(e -> {
//            
//        });
    }
    
    @Override    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        
        int chessWidth = (int) Math.floor(gridWidth*ChessButton.proportion);
        int chessHeight = (int) Math.floor(gridHeight*ChessButton.proportion);
        setLocation(locations[region][row][column].x - chessWidth/2, locations[region][row][column].y - chessHeight/2);
        setPreferredSize(new Dimension(chessWidth, chessHeight));
        
        if (text != "") g2D.drawImage(chessImg.getImage(), 0, 0, getSize().width, getSize().height, this);
    }
    
    public void setImage(String text) {
        if (text != "") chessImg = new ImageIcon(getClass().getResource(text + ".png"));
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public int getRegion() {
        return region;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public String getText() {
        return text;
    }
    
    public Color getColor() {
        return color;
    }
}
