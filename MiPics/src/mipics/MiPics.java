/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/


package mipics;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author 016171682
 */
public class MiPics extends JFrame implements TreeSelectionListener {
    
    private Image img, iconImage;
    private JButton getPictureButton, getTag, iconButton, deleteAlbum, renameAlbum, changeTag;
    private String nodeString;
    private JTextField textTag; 
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootNode;
    private File file;
    private DefaultMutableTreeNode tag1, pic;
    private JPanel panel2;
    private JToolBar panel3, buttonPanel;
    private JPanel picPanel;
    private String fileString;  
    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuView;
    private JMenuItem menuFileOpen, menuFileExit, menuEditAddAlbum, 
            menuViewShowIcons, MenuEditDeleteAlbum, MenuEditChangeTag, MenuEditRenameAlbum;
    private JSlider slider;
 
    public static void main (String [] args){

        new MiPics();
       
    }
    
    
    public MiPics (){
   //Load the save file with tree model if already created
         try{
             System.out.println("Exists");
        FileInputStream fis = new FileInputStream("MiAlbum");
             try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                 model = (DefaultTreeModel)ois.readObject();
             }
        
        }
        catch(IOException  | ClassNotFoundException err)
        {System.out.println("We had a file loading issue");
        }
   //If no pictures inserted yet create new root node     
         if(model ==null){
             DefaultMutableTreeNode tagNode = getTagTree();
             model = new DefaultTreeModel(tagNode);  
         }
         
   //Set size and title of main panel 
        setSize(1000,800);
        setTitle("Mi Pics");
        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        add( topPanel );
  
   //Create the menu bar
         menuBar=new JMenuBar();

  //Set this instance as the application's menu bar
        setJMenuBar(menuBar);
  
  //Set up the file menu
        menuFile=new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menuFile);
        menuFileOpen = new JMenuItem ( "Open...", new ImageIcon ("OPEN.gif"));
        menuFileOpen.setMnemonic(KeyEvent.VK_O);   
        menuFileOpen.setToolTipText("Open a new file");
        menuFileOpen.addActionListener(new ActionListener() {        
             public void actionPerformed(ActionEvent event) {
              getPictureButtonClick();
            }
          });
        menuFileExit=new JMenuItem("Exit");
        menuFileExit.setMnemonic(KeyEvent.VK_X);
        menuFileExit.setToolTipText("Quit program");
        menuFileExit.addActionListener(new ActionListener() {        
              public void actionPerformed(ActionEvent event) {
              exit() ;
             }
                    private void exit() {
                        System.exit(0);
                    }
         }); 
        menuFile.add(menuFileOpen);
        menuFile.add(menuFileExit);
   
    //Set up the edit menu
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menuEdit);         
     //Add edit album item   
        menuEditAddAlbum = new JMenuItem("Create Album");
        menuEditAddAlbum.setMnemonic(KeyEvent.VK_A);
        menuEditAddAlbum.setToolTipText("Add Photo to Album");
        menuEditAddAlbum.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent event) {
             addTag();
           }
         });
    //Add delete album item
        MenuEditDeleteAlbum=new JMenuItem("Delete Album");
        MenuEditDeleteAlbum.setMnemonic(KeyEvent.VK_D);
        MenuEditDeleteAlbum.setToolTipText("Delete Selected Album");
        MenuEditDeleteAlbum.addActionListener(new ActionListener() {        
              public void actionPerformed(ActionEvent event) {
               deleteAlbum();
             }
           });
    // Add change tag item
        MenuEditChangeTag=new JMenuItem("Rename Photo Tag");
        MenuEditChangeTag.setMnemonic(KeyEvent.VK_P);
        MenuEditChangeTag.setToolTipText("Change name of photo tag in album");
        MenuEditChangeTag.addActionListener(new ActionListener() {        
              public void actionPerformed(ActionEvent event) {
               changeTag();
             }
           });
        
     // Add rename album item
        MenuEditRenameAlbum=new JMenuItem("Rename Album");
        MenuEditRenameAlbum.setMnemonic(KeyEvent.VK_R);
        MenuEditRenameAlbum.setToolTipText("Change name of photo album");
        MenuEditRenameAlbum.addActionListener(new ActionListener() {        
              public void actionPerformed(ActionEvent event) {
               renameAlbum();
             }
           });
        
        menuEdit.add(menuEditAddAlbum);
        menuEdit.add(MenuEditDeleteAlbum);
        menuEdit.add(MenuEditChangeTag);
        menuEdit.add(MenuEditRenameAlbum);
       
      //Set up the view menu
        menuView = new JMenu("View");
        menuEdit.setMnemonic(KeyEvent.VK_V);
        menuBar.add(menuView);         
      //Add view thumbnails item   
        menuViewShowIcons = new JMenuItem("Show Album Thumbnails");
        menuViewShowIcons.setMnemonic(KeyEvent.VK_S);
        menuViewShowIcons.setToolTipText("Select an album and click to show thumbnails of pictures");
        menuViewShowIcons.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent event) {
             addIcon();
           }
         });
        menuView.add(menuViewShowIcons);
     
    //create the image canvas  
        picPanel = new PicturePanel();
        picPanel.setBackground(Color.darkGray);
        JScrollPane scroller = new JScrollPane(picPanel);
        topPanel.add(scroller, BorderLayout.CENTER);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); 
        
    //Create Tree to save photo albums   
        tree = new JTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(150, 550));
        panel2 = new JPanel();    
        panel2.setBackground(Color.darkGray);
        panel2.add(scroll);
        topPanel.add(panel2, BorderLayout.LINE_START);
            
    // Create a slider widget in a toolbar to adjust picture size  
        JToolBar editMenu = new JToolBar();  
        JLabel sizer = new JLabel("Size %");
        editMenu.add(sizer);
        slider = new JSlider(0, 200);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOrientation(JSlider.HORIZONTAL);
        slider.setToolTipText("Change image size");
        slider.addChangeListener(new SliderListener());
        editMenu.add(slider);
        topPanel.add(editMenu, BorderLayout.SOUTH);
    
    //Create set of buttons in a toolbar    
        iconButton = new JButton ("", new ImageIcon("showIcons.png"));
        iconButton.setToolTipText("Show thumbnails of all pictures in an album");
        iconButton.addActionListener(new ActionListener() {        
        public void actionPerformed(ActionEvent event) {
           addIcon();
          }
          });
        buttonPanel = new JToolBar(null, JToolBar.VERTICAL);
        buttonPanel.add(iconButton);
       
        getPictureButton = new JButton("", new ImageIcon ("OpenButton.png")); 
        getPictureButton.setToolTipText("Select Picture to Open");         
        getPictureButton.addActionListener(new ActionListener() {        
        public void actionPerformed(ActionEvent event) {
           getPictureButtonClick();
           }
          });
  
        getTag = new JButton ("",new ImageIcon("createAlbum.png"));
        getTag.setToolTipText("Create new album / add picture to album with given name");
        getTag.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
          addTag();
          }
         });
               
        changeTag = new JButton("",new ImageIcon("changeName.png") );
        changeTag.setToolTipText("Change the name of a photo in an album");
        changeTag.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        changeTag();
        }
         });
        buttonPanel.add(changeTag);
        
        deleteAlbum = new JButton("",new ImageIcon("deleteAlbum.png"));
        deleteAlbum.setToolTipText("Delete an album and it's contents");
        deleteAlbum.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        deleteAlbum();
        }
        });
        buttonPanel.add(deleteAlbum);
        
        renameAlbum = new JButton("",new ImageIcon("renameAlbum.png"));
        renameAlbum.setToolTipText("Rename an album");
        renameAlbum.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        renameAlbum();
        }
        });
    
        buttonPanel.add(renameAlbum);
        buttonPanel.add(getTag);
        buttonPanel.add(getPictureButton); 
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
     //Set up panel for showing album thumbnails   
        panel3 = new JToolBar();
        panel3.setToolTipText("Album Thumbnails");
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setBackground(Color.DARK_GRAY);
        topPanel.add(panel3, BorderLayout.NORTH);
        this.setVisible(true);
        
    }

    //Use methods to open picture file when open button clicked
    public final void getPictureButtonClick(){
     
        getImageFile();
        getImage(fileString);
        validate();  
        repaint();  
        
    }
    
    //Gets the file location and filename from chosen file
    public String getImageFile(){   

        String userhome = System.getProperty("user.home");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file only","JPEG file", "jpg", "jpeg","gif", "png");    
        JFileChooser fc = new JFileChooser(userhome+"\\Pictures");
        ImagePreview preview = new ImagePreview(fc);
        fc.addPropertyChangeListener(preview);
        fc.setAccessory(preview);
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        int result = fc.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
            fileString = file.getPath();
            return fileString;
        }
        else
            return null;
        
    }
    
    //Opens the image based on the filelocation as a String input
    public void getImage(String s){
        if (s != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(s).getScaledInstance(500, -1, Image.SCALE_SMOOTH);        
        }
    }
    
  //Listens for changes in slider selection value
    class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {       
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        // make sure picture is selected
        if (fileString == null){
            //make sure a picture not an album
            if (node.isLeaf()){      
                   Object nodeInfo = node.getUserObject();
                   Tag f = (Tag) node.getUserObject();
                   System.out.println(f.getFileLocation());
                   fileString = f.getFileLocation();
        }
            }
           
        //Get slider selection   
        JSlider source = (JSlider)e.getSource();
        //make sure photo is open first
        if (fileString == null){
         JOptionPane.showMessageDialog(MiPics.this, "Please open a photo to re-size first","Error", JOptionPane.INFORMATION_MESSAGE);
           return;
        }
        //make sure slider had stopped moving
        if (!source.getValueIsAdjusting()) {
            double fps = (int)source.getValue();
            System.out.println(fps);
            double ratio = fps/100;
            System.out.println(ratio);
            double newSize = ratio * 500;  
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(fileString);
            img = img.getScaledInstance((int) newSize, -1, Image.SCALE_SMOOTH);
            validate();
            repaint();
        }   
    }
}
   

  //Add listener to record changes in tree selections  
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        //make sure something is selected
        if (node == null) return;
     //Check if selection is a picture
        if (node.isLeaf()){
            Object nodeInfo = node.getUserObject();
            Tag f = (Tag) node.getUserObject();
            nodeString = f.getFileLocation();
            getImage(nodeString);
            validate();
            repaint();
        }
      
  }
 
  //Sets up a unique picture panel JPanel for drawing on
    private class PicturePanel extends JPanel{
         public void paint(Graphics g){
            super.paintComponent(g);
             g.drawImage(img, 0, 0, picPanel);
         }
         
     }

   //Sets up a tree root node if first instance
    private DefaultMutableTreeNode getTagTree (){
    
        this.rootNode = new DefaultMutableTreeNode("Photo Albums");
        return rootNode;
    }
   
 //Finds treepath	
    private TreePath find(DefaultMutableTreeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }
  
  //Adds photo to album or creates new album  
    public void addTag() {
        if (file == null){
            JOptionPane.showMessageDialog(MiPics.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //Get album name
        String getTagName = JOptionPane.showInputDialog(MiPics.this, "Enter a new album name");
        
        //get just name of file without filepath
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        String fileLocation = file.toString();
      
        //Make sure users enter something
        if (getTagName.length() ==0){
            JOptionPane.showMessageDialog(MiPics.this, "Please enter a Name","Error", JOptionPane.INFORMATION_MESSAGE);
        }
        //If file is opened and name added
        else
        { 
            rootNode = (DefaultMutableTreeNode)tree.getModel().getRoot();
            //If no albm created, then create new one
            if( find(rootNode,getTagName)== null){
                DefaultMutableTreeNode getTagTree = new DefaultMutableTreeNode(getTagName);
                this.model.insertNodeInto(getTagTree, rootNode, rootNode.getChildCount());          
                this.model.insertNodeInto(new DefaultMutableTreeNode(new Tag(getTagName,fileLocation, fname)), getTagTree, getTagTree.getChildCount());
                TreePath path = find(rootNode,getTagName);
                tree.setSelectionPath(path);
                tree.scrollPathToVisible(path);
           }
            //If album created then add photo to that album
            else{
                TreePath path = find(rootNode,getTagName);
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)path.getLastPathComponent();
                this.model.insertNodeInto(new DefaultMutableTreeNode(new Tag(getTagName,fileLocation, fname)), treeNode, treeNode.getChildCount());
                System.out.println(find(rootNode,getTagName));
                tree.setSelectionPath(path);
                tree.scrollPathToVisible(path);    
            }   
            //save the tree
            saveTree();
            this.validate();
            this.repaint();
        }
    }
    
    //Save tree model
    public void saveTree(){
        try{
            FileOutputStream fos = new FileOutputStream("MiAlbum");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(model);
        }
        catch(Exception e){
        }
    }
   
    //Get selected tree node
    private DefaultMutableTreeNode getSelectedNode(){
       return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();    
   }
    
    //Add album icons to top panel
    private void addIcon(){
     
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        //Make sure album is selected
        if ( selectedNode == null) {
            JOptionPane.showMessageDialog(MiPics.this, "Please select an album first","Error", JOptionPane.INFORMATION_MESSAGE);    
        } 
        if (selectedNode.isLeaf()){
         JOptionPane.showMessageDialog(MiPics.this, "Please select an album not a photo","Error", JOptionPane.INFORMATION_MESSAGE);
        }
       
        else{
        //remove any old picture in panel
        panel3.removeAll();
        JLabel panLab = new JLabel("Album Thumbnails");
        panLab.setForeground(Color.white);
        panel3.add(panLab);    
        selectedNode.getChildCount();
        System.out.println(selectedNode.getChildCount());
        ArrayList<String> nodeVals = new ArrayList<String>();
         //Loop to add number of buttons coressponding to number of pics in album
        for(int i=0;i<selectedNode.getChildCount();i++){
            DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode) selectedNode.getChildAt(i);
            Tag f = (Tag) tempNode.getUserObject();
            nodeVals.add(selectedNode.getChildAt(i).toString());
            nodeString = f.getFileLocation();
            JButton button = new JButton ("Button" + i);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image img = kit.getImage(this.nodeString);
            Image simg = img.getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            ImageIcon picIcon = new ImageIcon(simg);
            JLabel pIcon = new JLabel(picIcon);
            button.add(pIcon);
            this.panel3.add(pIcon);
            panel3.revalidate();
            panel3.repaint();
        } 
        }     
        validate();
        repaint();
   }
    //Change picture name in album
   private void changeTag(){
       DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        if (node == null){
           JOptionPane.showMessageDialog(MiPics.this, "Select a Photo name to Change", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {  
           if (node.isLeaf()){
                String newName = JOptionPane.showInputDialog(MiPics.this, "Enter a new tag name");
                Object nodeInfo = node.getUserObject();
                Tag f = (Tag) node.getUserObject();
                f.setFileName(newName);
                model.reload();
                saveTree();
           }
           else{JOptionPane.showMessageDialog(MiPics.this, "Select the picture name to change", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }
   }
   
   //Delete an album from tree
    private void deleteAlbum() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode != null){
            model.removeNodeFromParent(selectedNode);
            saveTree();
        }
        else{
            JOptionPane.showMessageDialog(MiPics.this, "Please select an album to delete first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
   }
   
    //Rename an album
    private void renameAlbum() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null){
            JOptionPane.showMessageDialog(MiPics.this, "Please select an album to rename first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //if selected node is album not picture
        if (!selectedNode.isLeaf()){
            String newAlbumName = JOptionPane.showInputDialog(MiPics.this, "Enter a new album name");
            selectedNode.setUserObject(newAlbumName);
            model.reload();
            saveTree();
        }
        else{
            JOptionPane.showMessageDialog(MiPics.this, "Please select an album to rename not a picture","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
}
