/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author Timot
 */

import java.awt.*;
import java.awt.event.*;

public class AWTMenuDemo {
   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
   private Panel controlPanel;

   public AWTMenuDemo(){
      prepareGUI();
   }

   public static void main(String[] args){
      AWTMenuDemo  awtMenuDemo = new AWTMenuDemo();     
      awtMenuDemo.showPopupMenuDemo();
   }

   private void prepareGUI(){
      mainFrame = new Frame("Java AWT Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new Label();
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.CENTER);
      statusLabel.setSize(350,100);

      controlPanel = new Panel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   private void showPopupMenuDemo(){
      final PopupMenu editMenu = new PopupMenu("Edit"); 

      MenuItem cutMenuItem = new MenuItem("Cut");
      cutMenuItem.setActionCommand("Cut");

      MenuItem copyMenuItem = new MenuItem("Copy");
      copyMenuItem.setActionCommand("Copy");

      MenuItem pasteMenuItem = new MenuItem("Paste");
      pasteMenuItem.setActionCommand("Paste");

      MenuItemListener menuItemListener = new MenuItemListener();

      cutMenuItem.addActionListener(menuItemListener);
      copyMenuItem.addActionListener(menuItemListener);
      pasteMenuItem.addActionListener(menuItemListener);

      editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
      editMenu.add(pasteMenuItem);   
      
      controlPanel.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {            
               editMenu.show(controlPanel, e.getX(), e.getY());
         }               
      });
      controlPanel.add(editMenu); 

      mainFrame.setVisible(true);
   }
	
   class MenuItemListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {            
         statusLabel.setText(e.getActionCommand() 
            + " MenuItem clicked.");
      }    
   }
}