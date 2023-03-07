/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 *
 * @author victor
 */
public class Button extends JButton {
    private CellState state;
    
    private class MymouseAdapter extends MouseAdapter {
        
    }
    
    private static MymouseAdapter mouseAdapter;
    
    public Button() {
        super();
        myInit();
        addMouseListener(mouseAdapter);
    }
    
    private void myInit() {
        if (mouseAdapter == null) {
            mouseAdapter = new MymouseAdapter();
        }
        
    }
            
    
}
