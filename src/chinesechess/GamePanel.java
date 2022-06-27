/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class GamePanel extends JPanel {

    static int gridWidth;
    static int gridHeight;
    static Point[][][] locations;
    static Chess[][][] chesses;
    Player[] players;
    Player currentPlayer;
    Map<String, Integer> textIndexMap;
    Map<ChessButton, Chess> chessMap;
    private boolean isGameOver;

    public GamePanel(Color myColor) {
        setBackground(new Color(230, 175, 100));
        
        locations = new Point[2][5][9];
        chesses = new Chess[2][5][9];
        chessMap = new HashMap();
        isGameOver = false;
        initTextIndexMap();
        initPlayers(myColor);
        initAllCB();
        
        for (ChessButton cb : chessMap.keySet()) {
            cb.addActionListener(e -> {
                //有棋子
                if (chessMap.get(cb) != null) {
                    //選自己的棋
                    if (players[getCBPlayer(cb)] == currentPlayer) {
                        currentPlayer.setSelected(cb);
                        System.out.println(currentPlayer.getSelected());
                    } else {
                    //選對方的棋:吃棋
                        ChessButton selected = currentPlayer.getSelected();
                        if (selected != null && chessMap.get(selected).canEat(cb.getRegion(), cb.getRow(), cb.getColumn())) {
                            if (cb.getText() == "將" || cb.getText() == "帥") {
                                isGameOver = true;
                                JOptionPane.showMessageDialog(this, "Game Over");
                            } else {
                                playerDoSomething(selected, cb);
                            }
                        }
                    }
                } else {
                //沒棋子
                    ChessButton selected = currentPlayer.getSelected();
                    //已選棋且合法:走棋
                    if (selected != null && !chessMap.get(selected).isIllegal(cb.getRegion(), cb.getRow(), cb.getColumn())) {
                        playerDoSomething(selected, cb);
                    }
                }
            });
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.BLACK);
        drawBoard(g2D);
    }

    private void drawBoard(Graphics2D g2D) {
        int panelWidth = getSize().width;
        int panelHeight = getSize().height;
        int pad;

        //outside frame
        pad = Math.max(panelWidth, panelHeight) / 20;
        Point outsideStart = new Point(pad, pad);
        int outsideWidth = panelWidth - 2 * pad;
        int outsideHeight = panelHeight - 2 * pad;
        //inside frame
        pad /= 2;
        Point insideStart = new Point(outsideStart.x + pad, outsideStart.y + pad);
        int insideWidth = outsideWidth - 2 * pad;
        int insideHeight = outsideHeight - 2 * pad;
        //adjust
        outsideWidth -= insideWidth % 8;
        outsideHeight -= insideHeight % 9;
        insideWidth = outsideWidth - 2 * pad;
        insideHeight = outsideHeight - 2 * pad;
        //draw
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRect(outsideStart.x, outsideStart.y, outsideWidth, outsideHeight);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawRect(insideStart.x, insideStart.y, insideWidth, insideHeight);

        //grid
        gridWidth = insideWidth / 8;
        gridHeight = insideHeight / 9;
        int[] x = new int[9];
        int[] y = new int[10];
        //horizontal
        for (int i = 0; i < y.length; i++) {
            y[i] = insideStart.y + i * gridHeight;
            g2D.drawLine(insideStart.x, y[i], insideStart.x + insideWidth, y[i]);
        }
        //vertical
        for (int i = 0; i < x.length; i++) {
            x[i] = insideStart.x + i * gridWidth;
            if (i > 0 && i < x.length - 1) {
                g2D.drawLine(x[i], insideStart.y, x[i], insideStart.y + 4 * gridHeight);
                g2D.drawLine(x[i], insideStart.y + 5 * gridHeight, x[i], insideStart.y + 9 * gridHeight);
            }
        }

        //setLocations
        for (int i = 0; i < locations[0].length; i++) {
            for (int j = 0; j < x.length; j++) {
                locations[0][i][j] = new Point(x[j], y[i + 5]);
                locations[1][i][j] = new Point(x[j], y[locations[0].length - 1 - i]);
            }
        }

        //drawAngle
        for (int i = 0; i < players.length; i++) {
            drawAngle(g2D, locations[i][1][0], false, true);
            for (int j = 1; j <= 7; j++) {
                if (j % 2 == 0) {
                    drawAngle(g2D, locations[i][1][j], true, true);
                } else if (j == 1 || j == 7) {
                    drawAngle(g2D, locations[i][2][j], true, true);
                }
            }
            drawAngle(g2D, locations[i][1][8], true, false);
            //drawSlantLine
            g2D.drawLine(locations[i][2][3].x, locations[i][2][3].y, locations[i][4][5].x, locations[i][4][5].y);
            g2D.drawLine(locations[i][2][5].x, locations[i][2][5].y, locations[i][4][3].x, locations[i][4][3].y);
        }
    }

    private void drawAngle(Graphics2D g2D, Point p, boolean hasLeft, boolean hasRight) {
        int width = (int) Math.floor(gridWidth / 5);
        int height = (int) Math.floor(gridHeight / 5);
        int pad = 7;
        if (hasLeft) {
            g2D.drawPolyline(new int[]{p.x - pad - width, p.x - pad, p.x - pad}, new int[]{p.y - pad, p.y - pad, p.y - pad - height}, 3);
            g2D.drawPolyline(new int[]{p.x - pad - width, p.x - pad, p.x - pad}, new int[]{p.y + pad, p.y + pad, p.y + pad + height}, 3);
        }
        if (hasRight) {
            g2D.drawPolyline(new int[]{p.x + pad + width, p.x + pad, p.x + pad}, new int[]{p.y - pad, p.y - pad, p.y - pad - height}, 3);
            g2D.drawPolyline(new int[]{p.x + pad + width, p.x + pad, p.x + pad}, new int[]{p.y + pad, p.y + pad, p.y + pad + height}, 3);
        }
    }
    
    private void playerDoSomething(ChessButton selected, ChessButton clicked) {
        String text = selected.getText();
        setChess(text, textToChess(clicked, text, getCBPlayer(selected)), clicked);
        setChess("", null, selected);
        currentPlayer.setSelected(null);
        switchPlayer();
    }
    
    private void setChess(String text, Chess chess, ChessButton cb) {
        cb.setText(text);
        cb.setImage(text);
        if (chess != null) chess.setCB(cb);
        
        chessMap.put(cb, chess);
        chesses[cb.getRegion()][cb.getRow()][cb.getColumn()] = chess;

        repaint();
    }
    
    private int getCBPlayer(ChessButton cb) {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].getText().length-1; j++) {
                if (players[i].getText()[j] == cb.getText()) 
                    return i;
            }
        }
        return -1;
    }
    
    private void switchPlayer() {
        if (players[0].isMyTurn() == true) {
            players[0].setIsMyTurn(false);
            players[1].setIsMyTurn(true);
            currentPlayer = players[1];
        } else {
            players[0].setIsMyTurn(true);
            players[1].setIsMyTurn(false);
            currentPlayer = players[0];
        }
//        System.out.println(players[0].isMyTurn());
//        System.out.println(players[1].isMyTurn());
    }
    
    private void initTextIndexMap() {
        textIndexMap = new HashMap();
        textIndexMap.put("King", 0);
        textIndexMap.put("Guard", 1);
        textIndexMap.put("Minister", 2);
        textIndexMap.put("Knight", 3);
        textIndexMap.put("Rook", 4);
        textIndexMap.put("Cannon", 5);
        textIndexMap.put("Pawn", 6);
        textIndexMap.put("", 7);
    }
    
    private void initPlayers(Color myColor) {
        Color enemyColor;
        String[] myText, enemyText;
        if (myColor == Color.BLACK) {
            enemyColor = Color.RED;
            myText = new String[]{"將", "士", "象", "馬", "車", "砲", "卒", ""};
            enemyText = new String[]{"帥", "仕", "相", "傌", "俥", "炮", "兵", ""};
        } else {
            enemyColor = Color.BLACK;
            myText = new String[]{"帥", "仕", "相", "傌", "俥", "炮", "兵", ""};
            enemyText = new String[]{"將", "士", "象", "馬", "車", "砲", "卒", ""};
        }
        Player me = new Player(myColor, myText);
        Player enemy = new Player(enemyColor, enemyText);
        players = new Player[]{me, enemy};
        initPlayerTurn();
    }
    
    private void initPlayerTurn() {
        if (players[0].getColor() == Color.BLACK) {
            players[0].setIsMyTurn(true);
            players[1].setIsMyTurn(false);
            currentPlayer = players[0];
        } else {
            players[0].setIsMyTurn(false);
            players[1].setIsMyTurn(true);
            currentPlayer = players[1];
        }
    }
    
    private void initAllCB() {
        for (int i = 0; i < locations[0][0].length; i++) {
            initCB(0, i, textIndexMap.get(""));
            initCB(3, i, textIndexMap.get(""));
            if (i % 2 == 0) initCB(1, i, textIndexMap.get("Pawn"));
            else initCB(1, i, textIndexMap.get(""));
            if (i == 1 || i == 7) {
                initCB(2, i, textIndexMap.get("Cannon"));
                initCB(4, i, textIndexMap.get("Knight"));
            } else {
                initCB(2, i, textIndexMap.get(""));
                switch (i) {
                    case 0:
                    case 8:
                        initCB(4, i, textIndexMap.get("Rook"));
                        break;
                    case 2:
                    case 6:
                        initCB(4, i, textIndexMap.get("Minister"));
                        break;
                    case 3:
                    case 5:
                        initCB(4, i, textIndexMap.get("Guard"));
                        break;
                    default:
                        initCB(4, i, textIndexMap.get("King"));
                        break;
                }
            }
        }
    }
    
    private void initCB(int row, int col, int textIndex) {
        for (int i = 0; i < players.length; i++) {
            String text = players[i].getText()[textIndex];
            ChessButton cb = new ChessButton(i, row, col, text, players[i].getColor());
            Chess chess = textToChess(cb, text, getCBPlayer(cb));
            
            chessMap.put(cb, chess);
            chesses[i][row][col] = chess;
            add(cb);
            repaint();
        }
    }
    
    private Chess textToChess(ChessButton cb, String text, int player) {
        Chess chess;
        switch (text) {
            case "將":
            case "帥":
                chess = new King(cb);
                break;
            case "士":
            case "仕":
                chess = new Guard(cb);
                break;
            case "象":
            case "相":
                chess = new Minister(cb);
                break;
            case "馬":
            case "傌":
                chess = new Knight(cb);
                break;
            case "車":
            case "俥":
                chess = new Rook(cb);
                break;
            case "砲":
            case "炮":
                chess = new Cannon(cb);
                break;
            case "卒":
            case "兵":
                chess = new Pawn(cb, player);
                break;
            default:
                chess = null;
        }
        return chess;
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }

}
