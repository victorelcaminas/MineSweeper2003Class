/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class Board extends javax.swing.JPanel implements InitGamer {
    
    public static final int BOMB = -1;
    
    private int[][] matrix;
    private TimerInterface timerInterface;
    private FlagInterface flagInterface;
    private List<Button> listOfButtons;
    
    public Board() {
        initComponents();        
    }
    
    public void initGame() {
        removeComponents();
        myInit();
    }
    
    public void setFlagInterface(FlagInterface flagInterface) {
        this.flagInterface = flagInterface;
    }
    
    public void removeComponents() {
        for (Component component : getComponents()) {
            remove(component);
        }
    }
    
    private void initMatrix(int rows, int cols) {
        matrix = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                matrix[row][col] = 0;
            }
        }
    }
    
    private void addBombs(int rows, int cols) {
        int maxBombs = Config.instance.getNumBombs();
        int bombCounter = 0;
        while (bombCounter < maxBombs) {
            int randRow = (int) (Math.random() * rows);
            int randCol = (int) (Math.random() * cols);
            if (matrix[randRow][randCol] == 0) {
                matrix[randRow][randCol] = BOMB;
                bombCounter++;
            }
        }
    }

    private void generateMatrix(int rows, int cols) {
        initMatrix(rows, cols);
        addBombs(rows, cols);        
        calculateMatrixNumbers(rows, cols);
        printMatrix(rows, cols);
    }

    private void calculateMatrixNumbers(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == BOMB) {
                    incrementMatrixNumbers(row, col);
                }
            }
        }
    }

    
    private void incrementMatrixNumbers(int row, int col) {
        for (int incRow = - 1; incRow <= 1; incRow ++) {
            for (int incCol = - 1; incCol <= 1; incCol++) {
                //if (incRow != 0 || incCol !=0) {
                int checkRow = row + incRow;
                int checkCol = col + incCol;
                if (!(incRow == 0 && incCol == 0) &&
                        isValid(checkRow,checkCol) &&
                        matrix[checkRow][checkCol] != BOMB) {
                    
                    matrix[checkRow][checkCol] += 1;
                }
            }
        }
    }
    
    
    private void printMatrix(int rows, int cols) {
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.format("%3d", matrix[row][col]);
            }
            System.out.println();
        }
    }
    
    
    private boolean isValid(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }
        int numRows = Config.instance.getNumRows();
        int numCols = Config.instance.getNumCols();
        if (row >= numRows || col >= numCols) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 255, 204));
        setLayout(new java.awt.GridLayout(10, 10));
    }// </editor-fold>//GEN-END:initComponents

    public void myInit() {        
        
        int numRows = Config.instance.getNumRows();
        int numCols = Config.instance.getNumCols();
        
        generateMatrix(numRows, numCols);
        
        GridLayout gridLayout = (GridLayout) getLayout();
        gridLayout.setRows(numRows);
        gridLayout.setColumns(numCols);
        
        Image imageBack = new ImageIcon(getClass().getResource("/images/back.png")).getImage();
        Image newimgBack = imageBack.getScaledInstance(Button.SIZE, Button.SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        Icon iconBack = new ImageIcon(newimgBack);
        
        createGameBoard(numRows, numCols, iconBack);
    }
    

    private void createGameBoard(int numRows, int numCols, Icon iconBack) {
        listOfButtons = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JPanel panel = new JPanel();                
                panel.setLayout(new OverlayLayout(panel));
                
                JLabel label = addLabel(row, col);
                Button button = addButton(row, col);
                listOfButtons.add(button);
                
                panel.add(button);
                panel.add(label);
                
                panel.add(new JLabel(iconBack));
                
                add(panel);
            }
        }
    }

    private Button addButton(int row, int col) {
        Button button = new Button();
        button.setFlagInterface(flagInterface);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                timerInterface.startTimer();
                processClick(row, col);
            }
        });
        button.setSize(getSquareDimension());
        return button;
    }
    
    private void processClick(int row, int col) {
        if (matrix[row][col] == BOMB) {
            processGameOver();
        }
    }
    
    private void processGameOver() {
        timerInterface.stopTimer();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    for (Button b : listOfButtons) {
                        Icon icon = Util.getIcon("/images/boton_semi.png");
                        b.setIcon(icon);
                        b.removeMouseAdapter();
                    }
                } catch (InterruptedException ex) {
                    
                }
            }
        }).start();
        
        
    }
    
    public ImageIcon convert(Icon icon) {
        Image image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        ImageIcon imageIcon = new ImageIcon(image);
        return imageIcon;
    }
    
    private Dimension getSquareDimension() {
        int numRows = Config.instance.getNumRows();
        int numCols = Config.instance.getNumCols();
        int width = getWidth();
        int height = getHeight();
        Dimension d = new Dimension(width / numCols, height / numRows);
        return d;
    }
    
    public void setTimerInterface(TimerInterface timerInterface) {
        this.timerInterface = timerInterface;
    }

    private JLabel addLabel(int row, int col) {
        Color[] COLORS = {Color.decode("#FFFFFF"), 
                          Color.decode("#0000FF"),
                          Color.decode("#009900"),
                          Color.decode("#FF0000"),
                          Color.decode("#000099"),
                          Color.decode("#990000"),
                          Color.decode("#009999"),
                          Color.decode("#7F00FF"),
                          Color.decode("#808080")};
        int item = matrix[row][col];
        JLabel label = new JLabel();
        if (item == BOMB) {
            label.setIcon(Util.getIcon("/images/bomb.png"));
        } else {
            Color color = COLORS[item];
            Font font = new Font("Dialog", Font.BOLD, 20);
            label.setFont(font);
            label.setForeground(color);
            label.setText(" " + (item == 0 ? "" : item));
        }
        return label;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
