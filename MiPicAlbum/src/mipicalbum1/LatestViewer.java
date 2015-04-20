/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipicalbum1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

        
/**
 *
 * @author sbsar6
 */
public class LatestViewer extends JFrame implements ActionListener  {

    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit;
    private JMenuItem menuFileOpen, menuFileSaveAs, menuFileExit, menuEditAddAlbum, 
            menuEditAddTag, MenuEditDeleteAlbum, MenuEditChangeTag;
    
    
    public MenuComponents(){
        setTitle("Picture Album");
        setSize(800,600);
        
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add (topPanel);
        
        //Create the menu bar
        menuBar = new JMenuBar();
        
        //Set this instance as the application's menu bar
        setJMenuBar(menuBar);
        
        //Build the file menu
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menuFile);
        
        //Add file items
        menuFileOpen = JMenuItem ( "Open...", new ImageIcon ("Open.png"));
        m, KeyEvent.VK_O, "Open a new file" );
        
        
    }
   
    
}
