/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipicalbum1;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import mipicalbum1.MiAlbum1.ImageBrightness;

//import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Andrew
 */
public class RecentAlbum extends JFrame implements TreeSelectionListener {
    
    Image img;
    Image iconImage;
    JButton getPictureButton;
    JButton getTag;
    JButton flipButton;
    private JLabel showName;
    private String nodeString;
    private JTextField textTag; 
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootNode;
    
    private File file;
    private HashMap tagList; 
    private DefaultMutableTreeNode tag1, pic;
    private JPanel panel2;
  
    //image editing components
    Image orImg;
  BufferedImage orBufferedImage;
  BufferedImage bimg; 
  BufferedImage bimg1; 
 
    JMenuBar mainmenu;
 JMenu menu;
 JMenu editmenu;
 JMenuItem mopen;
 JMenuItem msaveas;
 JMenuItem msave;
 JMenuItem mexit; 
 JMenuItem mbright; 
 JMenuItem mcompress; 
 JMenuItem mresize;
 JMenuItem mrotate;
 JMenuItem mtransparent;
 JMenuItem maddtext;
 JMenuItem mcancel;
 JSlider slider;
 
    public static void main (String [] args){

        new RecentAlbum();
       
    }
    
    
    public RecentAlbum (){
   
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
         
        
    
         mainmenu=new JMenuBar();
  menu=new JMenu("File");
  menu.setMnemonic(KeyEvent.VK_F);

  mopen=new JMenuItem("Open...");
  mopen.setMnemonic(KeyEvent.VK_O);
 mopen.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        getPictureButtonClick();
      }
    });

  msaveas=new JMenuItem("Save as...");
  msaveas.setMnemonic(KeyEvent.VK_S);
//  msaveas.addActionListener(this);

  msave=new JMenuItem("Save");
  msave.setMnemonic(KeyEvent.VK_V);
 // msave.addActionListener(this);  

  mexit=new JMenuItem("Exit");
  mexit.setMnemonic(KeyEvent.VK_X);
//  mexit.addActionListener(this);
  menu.add(mopen);
  menu.add(msaveas);
  menu.add(msave);
  menu.add(mexit);  

  editmenu=new JMenu("Edit");
  editmenu.setMnemonic(KeyEvent.VK_E);
  mbright=new JMenuItem("Image brightness");
  mbright.setMnemonic(KeyEvent.VK_B);
 // mbright.addActionListener(this);

  maddtext=new JMenuItem("Add text on image");
  maddtext.setMnemonic(KeyEvent.VK_A);
 // maddtext.addActionListener(this);  

  mresize=new JMenuItem("Image resize");
  mresize.setMnemonic(KeyEvent.VK_R);
//  mresize.addActionListener(this);
 
  mcompress=new JMenuItem("Image compression");
  mcompress.setMnemonic(KeyEvent.VK_P);
 // mcompress.addActionListener(this);

  mrotate=new JMenuItem("Image rotation");
  mrotate.setMnemonic(KeyEvent.VK_T);
 // mrotate.addActionListener(this);

  mtransparent=new JMenuItem("Image transparency");
  mtransparent.setMnemonic(KeyEvent.VK_T);
 // mtransparent.addActionListener(this);
 
  mcancel=new JMenuItem("Cancel editing");
  mcancel.setMnemonic(KeyEvent.VK_L);
 // mcancel.addActionListener(this);

  editmenu.add(maddtext);
  editmenu.add(mbright);
  editmenu.add(mcompress);
  editmenu.add(mresize);
  editmenu.add(mrotate);
  editmenu.add(mtransparent);
  editmenu.add(mcancel);

  mainmenu.add(menu);
  mainmenu.add(editmenu);
  setJMenuBar(mainmenu);
  
 

          JPanel picPanel = new PicturePanel();
        this.add(picPanel, BorderLayout.CENTER);
       

         setSize(800,600); 
       
        setTitle("Mi Pics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //   setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
     setVisible(true); 
        
       
       tree = new JTree(model);
   
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(150, 250));
        JPanel panel2 = new JPanel();
        panel2.add(scroll);
        this.add(panel2, BorderLayout.WEST);
            
      
          //this.validate();
          // this.repaint();
       JToolBar editMenu = new JToolBar();  
       JLabel sizer = new JLabel("Re-size image");
       editmenu.add(sizer);
      slider = new JSlider(0, 200);
      slider.setMajorTickSpacing(20);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setOrientation(JSlider.VERTICAL);
      slider.setToolTipText("Change image size");
      slider.addChangeListener(null);
      editMenu.add(slider);
         this.add(editMenu, BorderLayout.EAST);
       
        flipButton = new JButton ("Brighten");
        flipButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        flipImage();
      }

             private void flipImage() {
                 System.out.println("Does nothing yet");
 }  
    });
         JToolBar buttonPanel = new JToolBar();
         buttonPanel.add(flipButton);
       
        getPictureButton = new JButton ("Open Picture");
        getPictureButton.setBackground(Color.DARK_GRAY);
        getPictureButton.setForeground(Color.WHITE);
      
        getPictureButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        getPictureButtonClick();
      }
    });
        buttonPanel.add(getPictureButton);
              
        textTag = new JTextField(10);
        buttonPanel.add(textTag);
        getTag = new JButton ("Add Album tags");
        getTag.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addTag();
      }
    });
        buttonPanel.add(getTag);
        
        JButton ChangeTag = new JButton("Change Photo Tag");
        ChangeTag.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        changeTag();
      }
    });
        buttonPanel.add(ChangeTag);
        
        JButton DeleteAlbum = new JButton("Delete Album");
        DeleteAlbum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        deleteAlbum();
      }

             private void deleteAlbum() {
                 DefaultMutableTreeNode selectedNode = getSelectedNode();
                 if (selectedNode != null){
                     model.removeNodeFromParent(selectedNode);
                     saveTree();
                 }
                 else{
                     JOptionPane.showMessageDialog(RecentAlbum.this, "Please select an album to delete first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
                 }
                 
             }
    });
        buttonPanel.add(DeleteAlbum);
        
       // buttonPanel.setf
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
        
    }

    public final void getPictureButtonClick(){
     
         String file = getImageFile();
        getImage(file);
            
        
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
            this.file = fc.getSelectedFile();
            System.out.println(file);
            return file.getPath();
        }
        else
            return null;
        
    }
        public void getImage(String s){
        if (s != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(s);
            img = img.getScaledInstance(500, -1, Image.SCALE_SMOOTH);
            this.repaint();
        
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
            this.nodeString = f.getFileLocation().toString();
            getImage(nodeString);
    }
  }

    private void sliderChanged() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     private class PicturePanel extends JPanel{
         public void paint(Graphics g){
             g.drawImage(img, 0, 0, this);
         }
         
     }

         public String getDescription(){
             return "Image files (*.jpg, *.gif, *.png)";
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
            JOptionPane.showMessageDialog(RecentAlbum.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DefaultMutableTreeNode parent = getSelectedNode();
       
        String getTagName = textTag.getText();
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
         if (pos > 0) {
        fname = fname.substring(0, pos);
           }
         System.out.println(fname);  
         String fileLocation = file.toString();
        // Tag tag = new Tag(getTagName,fileLocation, fname);
       

   //String basename = FilenameUtils.getBaseName(fileName);
  
         if (getTagName.length() ==0)
        {
            JOptionPane.showMessageDialog(RecentAlbum.this, "Please enter a Tag","Error", JOptionPane.INFORMATION_MESSAGE);
            
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
      JPanel panel3 = new JPanel();
       Toolkit kit = Toolkit.getDefaultToolkit();
        Image iconImg = kit.getImage(this.nodeString);
            img = img.getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            this.repaint();
       
       ImageIcon picIcon = new ImageIcon(this.nodeString);
       
       JLabel pIcon = new JLabel(picIcon);
       panel3.add(pIcon);
       this.add(panel3, BorderLayout.EAST);
       this.validate();
       this.repaint();
   }
   private void changeTag(){
       DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
       if (node == null){
           JOptionPane.showMessageDialog(RecentAlbum.this, "Select a Photo name to Change", "Error", JOptionPane.ERROR_MESSAGE);
       }
       else {  
           
           if (node.isLeaf()){
               String newName = JOptionPane.showInputDialog(RecentAlbum.this, "Enter a new tag name");
       
       // Insert code to replace selectedNode with newName
           System.out.println(newName);
        Object nodeInfo = node.getUserObject();
       Tag f = (Tag) node.getUserObject();
           System.out.println(f.getTagName());
       f.setFileName(newName);
       model.reload();
       saveTree();
           }
           else{JOptionPane.showMessageDialog(RecentAlbum.this, "Select the picture name to change", "Error", JOptionPane.ERROR_MESSAGE);
       
       }
   }
   }
   


}