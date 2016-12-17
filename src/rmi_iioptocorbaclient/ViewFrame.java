/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_iioptocorbaclient;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author pc
 */ 
public class ViewFrame {   
    
    public static JFrame mainFrame;    
    public static TextPanel textEntryPanel;                        
    
    
    public static void main () {         
        initComponents();               
    }    
    
    public static void initComponents() {
        mainFrame = new JFrame();
        textEntryPanel = new TextPanel();
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        mainFrame.setTitle("DataBase");
        mainFrame.setSize(500, 300);
        mainFrame.setResizable(false);
        
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(textEntryPanel, BorderLayout.NORTH);            
        
        mainFrame.setVisible(true);
        mainFrame.setBackground(Color.WHITE); 
    }
        
}
