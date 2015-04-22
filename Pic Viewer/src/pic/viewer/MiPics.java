/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pic.viewer;

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



//import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Andrew
 */
public class MiPics extends JFrame implements TreeSelectionListener {
    
    Image img;
    Image iconImage;
    private JButton getPictureButton, getTag, flipButton, deleteAlbum, renameAlbum;
    private JLabel showName;
    private String nodeString;
    private JTextField textTag; 
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootNode;
    
    private File file;
    
    private DefaultMutableTreeNode tag1, pic;
    private JPanel panel2;
    private JToolBar panel3;
   private JPanel picPanel;
    private String fileString;
    //image editing components
     private Image orImg;
   
 
   private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit;
    private JMenuItem menuFileOpen, menuFileExit, menuEditAddAlbum, 
            menuEditAddTag, MenuEditDeleteAlbum, MenuEditChangeTag;
    private JSlider slider;
 
    public static void main (String [] args){

        new MiPics();
       
    }
    
    
    public MiPics (){
   
         try{
             System.out.println("Exists");
        FileInputStream fis = new FileInputStream("MyObject");
        ObjectInputStream ois= new ObjectInputStream(fis);   
        model = (DefaultTreeModel)ois.readObject();
        ois.close();
        
        }
        catch(IOException  | ClassNotFoundException err)
        {System.out.println("We had a file loading issue");
        }
        
         if(model ==null){
             DefaultMutableTreeNode tagNode = getTagTree();
             model = new DefaultTreeModel(tagNode);  
         }
         
        
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
//  mexit.addActionListener(this);
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
            
   menuEditAddAlbum = new JMenuItem("Create Album");
   menuEditAddAlbum.setMnemonic(KeyEvent.VK_A);
 
   menuEditAddAlbum.setToolTipText("Add Photo to Album");
   menuEditAddAlbum.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        addTag();
      }
    });
  
  MenuEditDeleteAlbum=new JMenuItem("Delete Album");
 MenuEditDeleteAlbum.setMnemonic(KeyEvent.VK_D);
//  mresize.addActionListener(this);
 MenuEditDeleteAlbum.setToolTipText("Delete Selected Album");
 MenuEditDeleteAlbum.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        deleteAlbum();
      }
    });
  
 MenuEditChangeTag=new JMenuItem("Rename Photo Tag");
   MenuEditChangeTag.setMnemonic(KeyEvent.VK_R);
 // mcompress.addActionListener(this);
 MenuEditChangeTag.setToolTipText("Change name of photo tag in album");
  MenuEditChangeTag.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        changeTag();
      }
    });
  menuEdit.add(menuEditAddAlbum);
   menuEdit.add(MenuEditDeleteAlbum);
  menuEdit.add(MenuEditChangeTag);

         //create the image canvas  
          picPanel = new PicturePanel();
          picPanel.setBackground(Color.lightGray);
          JScrollPane scroller = new JScrollPane(picPanel);
          topPanel.add(scroller, BorderLayout.CENTER);
       

         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //   setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
     setVisible(true); 
        
       
       tree = new JTree(model);
   
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(150, 550));
        JPanel panel2 = new JPanel();
        
         panel2.setBackground(Color.lightGray);
        panel2.add(scroll);
        topPanel.add(panel2, BorderLayout.LINE_START);
            
      
          //this.validate();
          // this.repaint();
       JToolBar editMenu = new JToolBar();  
       JLabel sizer = new JLabel("Size %");
       editMenu.add(sizer);
      slider = new JSlider(0, 200);
      slider.setMajorTickSpacing(20);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setOrientation(JSlider.VERTICAL);
      slider.setToolTipText("Change image size");
      slider.addChangeListener(new SliderListener());
      editMenu.add(slider);
         topPanel.add(editMenu, BorderLayout.EAST);
       
        flipButton = new JButton ("Show Album Thumbnails");
        flipButton.setToolTipText("Show thumbnails of all pictures in an album");
        flipButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        addIcon();
      }
    });
         JToolBar buttonPanel = new JToolBar();
         buttonPanel.add(flipButton);
       
        getPictureButton = new JButton("Open Picture", new ImageIcon ("OPEN.gif")); 
        getPictureButton.setToolTipText("Select Picture to Open");
        getPictureButton.setBackground(Color.DARK_GRAY);
        getPictureButton.setForeground(Color.WHITE);
             
        getPictureButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        getPictureButtonClick();
      }
    });
        
         //JToolBar tagPanel = new JToolBar();  
         //tagPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //textTag = new JTextField(10);
        //tagPanel.add(textTag);
        getTag = new JButton ("Add Album tags");
        getTag.setToolTipText("Create new album / add picture to album with given name");
        getTag.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addTag();
      }
    });
        buttonPanel.add(getTag);
        
        
        JButton ChangeTag = new JButton("Change Photo Tag");
        ChangeTag.setToolTipText("Change the name of a photo in an album");
        ChangeTag.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        changeTag();
      }
    });
        buttonPanel.add(ChangeTag);
        
        deleteAlbum = new JButton("Delete Album");
        deleteAlbum.setToolTipText("Delete an album and it's contents");
        deleteAlbum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        deleteAlbum();
      }

    });
        buttonPanel.add(deleteAlbum);
        
        renameAlbum = new JButton("Rename Album");
        renameAlbum.setToolTipText("Rename an album");
        renameAlbum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        renameAlbum();
      }

    });
        buttonPanel.add(renameAlbum);
       // buttonPanel.add(tagPanel);
        buttonPanel.add(getPictureButton);
       // buttonPanel.setf
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
         panel3 = new JToolBar();
         panel3.setToolTipText("Album Thumbnails");
         panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setBackground(Color.DARK_GRAY);
        
        topPanel.add(panel3, BorderLayout.NORTH);
        this.setVisible(true);
        
    }

    public final void getPictureButtonClick(){
     
        getImageFile();
        getImage(fileString);
        validate();  
        repaint();  
        
    }
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
        public void getImage(String s){
        if (s != null){
            
            Toolkit kit = Toolkit.getDefaultToolkit();
           this.validate();
            this.repaint();
            img = kit.getImage(s).getScaledInstance(500, -1, Image.SCALE_SMOOTH);
            
            this.validate();
            this.repaint();
        
    }
    }
    
  
   class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
        
          
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    if (fileString == null){
    if (node.isLeaf()){
          //addIcon();
             Object nodeInfo = node.getUserObject();
            Tag f = (Tag) node.getUserObject();
           System.out.println(f.getFileLocation());
            fileString = f.getFileLocation();
           
    }}
      
          
         
        
        JSlider source = (JSlider)e.getSource();
        if (fileString == null){
         JOptionPane.showMessageDialog(MiPics.this, "Please open a photo to re-size first","Error", JOptionPane.INFORMATION_MESSAGE);
           return;
    }
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
   

    
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

    if (node == null) return;
    
   
    if (node.isLeaf()){
          //addIcon();
             Object nodeInfo = node.getUserObject();
            Tag f = (Tag) node.getUserObject();
           System.out.println(f.getFileLocation());
            nodeString = f.getFileLocation();
            getImage(nodeString);
            validate();
            repaint();
    }
    else{
        //addIcon();
    }
  }

   

     private class PicturePanel extends JPanel{
         public void paint(Graphics g){
            super.paintComponent(g);
             g.drawImage(img, 0, 0, picPanel);
         }
         
     }

 
 
private DefaultMutableTreeNode getTagTree (){
     
    this.rootNode = new DefaultMutableTreeNode("Photo Albums");
    return rootNode;
   
 
}
   
	
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
       
        
    public void addTag() {
        if (file == null)
        {
            JOptionPane.showMessageDialog(MiPics.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String getTagName = JOptionPane.showInputDialog(MiPics.this, "Enter a new album name");
        
        //get just name of file without filepath
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
         if (pos > 0) {
        fname = fname.substring(0, pos);
           }
         System.out.println(fname);  
         String fileLocation = file.toString();
      
  
         if (getTagName.length() ==0)
        {
            JOptionPane.showMessageDialog(MiPics.this, "Please enter a Tag","Error", JOptionPane.INFORMATION_MESSAGE);
            
        }
    else
        {
            System.out.println(getTagName); 
       
           rootNode = (DefaultMutableTreeNode)tree.getModel().getRoot();
          //  System.out.println(rootNode);
         //  DefaultMutableTreeNode getTagTree = new DefaultMutableTreeNode(getTagName);
         // rootNode.add(getTagTree);
          
 
         if( find(rootNode,getTagName)== null){
             System.out.println("Not in tree");
           DefaultMutableTreeNode getTagTree = new DefaultMutableTreeNode(getTagName);
        
           this.model.insertNodeInto(getTagTree, rootNode, rootNode.getChildCount());   
        
          //tag1 = makeShow(tag.getType(), rootNode);   
         //  pic = makeShow(tag.getValue(), tag1); 
          this.model.insertNodeInto(new DefaultMutableTreeNode(new Tag(getTagName,fileLocation, fname)), getTagTree, getTagTree.getChildCount());
          TreePath path = find(rootNode,getTagName);
          tree.setSelectionPath(path);
          tree.scrollPathToVisible(path);
          }
         else{
                TreePath path = find(rootNode,getTagName);
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)path.getLastPathComponent();
             this.model.insertNodeInto(new DefaultMutableTreeNode(new Tag(getTagName,fileLocation, fname)), treeNode, treeNode.getChildCount());
             System.out.println(find(rootNode,getTagName));
             tree.setSelectionPath(path);
             tree.scrollPathToVisible(path);
             
         }
         
        
            saveTree();
           this.validate();
          this.repaint();
     }
    }
    public void saveTree(){
          try
     {
       FileOutputStream fos = new FileOutputStream("MyObject");
        ObjectOutputStream oos= new ObjectOutputStream(fos);
        oos.writeObject(model);
     }
     catch(Exception e)
     {
      }
        
}
   private DefaultMutableTreeNode getSelectedNode(){
       return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
       
   }
    private void addIcon(){
     
       DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
      if ( selectedNode == null) {
        JOptionPane.showMessageDialog(MiPics.this, "Please select an album first","Error", JOptionPane.INFORMATION_MESSAGE);
           
       } 
      if (selectedNode.isLeaf()){
         JOptionPane.showMessageDialog(MiPics.this, "Please select an album not a photo","Error", JOptionPane.INFORMATION_MESSAGE);
           
    }
       
       
       else{
       panel3.removeAll();
       JLabel panLab = new JLabel("Album Thumbnails");
        panLab.setForeground(Color.white);
        panel3.add(panLab);  
       selectedNode.getChildCount();
         System.out.println(selectedNode.getChildCount());
         ArrayList<String> nodeVals = new ArrayList<String>();
         
         for(int i=0;i<selectedNode.getChildCount();i++){
            System.out.println(selectedNode.getChildAt(i).toString());
           DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode) selectedNode.getChildAt(i);
             
            Tag f = (Tag) tempNode.getUserObject();
             
            nodeVals.add(selectedNode.getChildAt(i).toString());
             System.out.println(nodeVals); 
             
            System.out.println(f.getFileLocation());
            nodeString = f.getFileLocation();
             System.out.println(nodeString);
             //JButton[] button = null;
             System.out.println(String.valueOf(i));
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
       
        
     
       //this.add(panel3, BorderLayout.EAST) ;
      validate();
      repaint();
   }
   private void changeTag(){
       DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
       if (node == null){
           JOptionPane.showMessageDialog(MiPics.this, "Select a Photo name to Change", "Error", JOptionPane.ERROR_MESSAGE);
       }
       else {  
           
           if (node.isLeaf()){
               String newName = JOptionPane.showInputDialog(MiPics.this, "Enter a new tag name");
       
       // Insert code to replace selectedNode with newName
           System.out.println(newName);
        Object nodeInfo = node.getUserObject();
       Tag f = (Tag) node.getUserObject();
           System.out.println(f.getTagName());
       f.setFileName(newName);
       model.reload();
       saveTree();
           }
           else{JOptionPane.showMessageDialog(MiPics.this, "Select the picture name to change", "Error", JOptionPane.ERROR_MESSAGE);
       
       }
   }
   }
   
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
              private void renameAlbum() {
                 DefaultMutableTreeNode selectedNode = getSelectedNode();
                 if (selectedNode == null){
                     JOptionPane.showMessageDialog(MiPics.this, "Please select an album to rename first","Error", JOptionPane.INFORMATION_MESSAGE);
                 return;
                 }
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
