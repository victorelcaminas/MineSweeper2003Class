/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author victor
 */
public class Board extends javax.swing.JPanel {
    
    public static final int BOMB = -1;
    
    private int[][] matrix;


    public Board() {
        initComponents();
        myInit();
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

    private void myInit() {        
        
        int numRows = Config.instance.getNumRows();
        int numCols = Config.instance.getNumCols();
        
        generateMatrix(numRows, numCols);
        
        GridLayout gridLayout = (GridLayout) getLayout();
        gridLayout.setRows(numRows);
        gridLayout.setColumns(numCols);
        
        Image imageBack = new ImageIcon(getClass().getResource("/images/back.png")).getImage();
        Image newimgBack = imageBack.getScaledInstance(Button.SIZE, Button.SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        Icon iconBack = new ImageIcon(newimgBack);
        
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JPanel panel = new JPanel();                
                panel.setLayout(new OverlayLayout(panel));
                
                
                Button button = new Button();
                button.setSize(getSquareDimension());
                
                panel.add(button);
                panel.add(new JLabel(iconBack));
                
                add(panel);
            }
        }
    }
    
    private Dimension getSquareDimension() {
        int numRows = Config.instance.getNumRows();
        int numCols = Config.instance.getNumCols();
        int width = getWidth();
        int height = getHeight();
        Dimension d = new Dimension(width / numCols, height / numRows);
        return d;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
