/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class GUIFrame extends JFrame {

    private GamePanel gamePanel;

    public GUIFrame() {
        super("象棋");
        setLayout(new GridLayout());
//        setResizable(false);

        gamePanel = new GamePanel(Color.BLACK);
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (gamePanel.isGameOver()) {
                    getContentPane().removeAll();
                    gamePanel = new GamePanel(Color.BLACK);
                    getContentPane().add(gamePanel);
                    gamePanel.revalidate();
                    setSize(601, 701);
                    setSize(600, 700);
                    addGamePanelMouseListener();
//                    gamePanel.repaint();
                }
            }
        });
        add(gamePanel);

    }
    
    private void addGamePanelMouseListener() {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (gamePanel.isGameOver()) {
                    getContentPane().removeAll();
                    gamePanel = new GamePanel(Color.BLACK);
                    getContentPane().add(gamePanel);
                    gamePanel.revalidate();
                    setSize(601, 701);
                    setSize(600, 700);
                }
            }
        });
    }
    
}
