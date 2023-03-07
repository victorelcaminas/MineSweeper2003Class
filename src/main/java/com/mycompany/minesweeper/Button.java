/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author victor
 */
public class Button extends JButton {
    private CellState state;
    
    private class MymouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Button button = (Button) e.getSource();
            if (button.state == CellState.CLOSED) {
                button.setIcon(getIcon("/images/boton_pressed.jpg"));
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            Button button = (Button) e.getSource();
            button.updateState();
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            Button button = (Button) e.getSource();
            button.state = CellState.OPEN;
            button.updateState();
        }
    }
    
    private static MymouseAdapter mouseAdapter;
    
    public Button() {
        super();
        myInit();
        
    }
    
    private void myInit() {
        setMargin(new Insets(0,0,0,0));
        setBorderPainted(false);
        setContentAreaFilled(false);
        
        if (mouseAdapter == null) {
            mouseAdapter = new MymouseAdapter();
        }
        addMouseListener(mouseAdapter);
        state = CellState.CLOSED;
        updateState();
        
    }
    
    public Icon getIcon(String path) {
        Image image = new ImageIcon(getClass()
                        .getResource(path))
                        .getImage();
        Image newimg = image.getScaledInstance
                (30, 30,  java.awt.Image.SCALE_SMOOTH); 
        Icon icon = new ImageIcon(newimg);
        return icon;
 
    }
    
    public void updateState() {
        switch (state) {
            case CLOSED:
                setIcon(getIcon("/images/boton.jpg"));
                break;
            case OPEN:
                setVisible(false);
                break;
            case FLAG:
                setIcon(getIcon("/images/boton.jpg"));
                Icon flag = getIcon("/images/flag.png");
                JLabel label = new JLabel(flag);
                add(label);
                break;
            case QUESTION:
                setIcon(getIcon("/images/boton.jpg"));
                Icon question = getIcon("/images/question.png");
                JLabel labelQ = new JLabel(question);
                add(labelQ);
                break;
            default:
                throw new AssertionError();
        }
    }
            
    
}
