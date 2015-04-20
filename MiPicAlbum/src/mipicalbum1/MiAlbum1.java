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
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import mipicalbum.DetachedMagnifyingGlass;

//import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Andrew
 */
public class MiAlbum1 extends JFrame implements TreeSelectionListener {
    
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
    private JFrame frame;
     ImgArea ia;
    //image editing components
    Image orImg;
  BufferedImage orBufferedImage;
  BufferedImage bimg; 
  BufferedImage bimg1; 
  float e;
  float radian;
  Dimension ds;
  int mX;
  int mY;
  int x;
  int y;
  static boolean imageLoaded;
  boolean actionSlided;
  boolean actionResized;
  boolean actionCompressed;
  boolean actionTransparent;
  boolean actionRotated;
  boolean actionDraw;
  boolean drawn;
  MediaTracker mt;
  static Color c;
  Color colorTextDraw;
  Robot rb;
  boolean dirHor;
  String imgFileName;
  String fontName;
  int fontSize;
  String textToDraw;
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
 
 
    public static void main (String [] args){

        new MiAlbum1();
       
    }
    
    
    public MiAlbum1 (){
   
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
         frame = new JFrame("Mi Pic Album");
        
    
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
  frame.add (mainmenu);
 // setJMenuBar(mainmenu);
 
  
  
          final JPanel picPanel = new PicturePanel();
        frame.add(picPanel, BorderLayout.CENTER);
       

         frame.setSize(800,600); 
           
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //   setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
     
        
       
       tree = new JTree(model);
   
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(150, 250));
        JPanel panel2 = new JPanel();
        panel2.add(scroll);
        frame.add(panel2, BorderLayout.WEST);
            
      
          //this.validate();
          // this.repaint();
        
       
        
        JToolBar editMenu = new JToolBar();
        flipButton = new JButton ("Brighten");
        flipButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        magnify(file);
      }

             public void magnify (File f) {
            
           
// image frame
        ImageIcon i = new ImageIcon (f.getPath());
        JLabel l = new JLabel (i);
        JFrame imgFrame = new JFrame ("Image");
        imgFrame.getContentPane().add(l);
        imgFrame.pack();
        imgFrame.setVisible(true);
        frame.add(imgFrame);
        // magnifying glass frame
        JFrame magFrame = new JFrame ("Mag");
        DetachedMagnifyingGlass mag =
            new DetachedMagnifyingGlass (l, new Dimension (150, 150), 2.0);
        magFrame.getContentPane().add (mag);
        magFrame.pack();
        magFrame.setLocation (new Point (
                             picPanel.getLocation().x + picPanel.getWidth(), 
                             picPanel.getLocation().y));
        magFrame.setVisible(true);
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
        
        JButton ChangeTag = new JButton("Change Album Tag");
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
                     JOptionPane.showMessageDialog(MiAlbum1.this, "Please select an album to delete first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
                 }
                 
             }
    });
        buttonPanel.add(DeleteAlbum);
        
       // buttonPanel.setf
       frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        
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
          
            frame.repaint();
        
    }
    }
    
        public BufferedImage createBufferedImageFromImage(Image image, int width, int height, boolean tran)
   { BufferedImage dest ;
  if(tran) 
       dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  else
   dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       Graphics2D g2 = dest.createGraphics();
       g2.drawImage(image, 0, 0, null);
       g2.dispose();
       return dest;
   }
    public void prepareImage(String filename){
   initialize();
   try{
   //track the image loading
   mt=new MediaTracker(this);    
   orImg=Toolkit.getDefaultToolkit().getImage(filename); 
   mt.addImage(orImg,0);
    mt.waitForID(0); 
   //get the image width and height  
   int width=orImg.getWidth(null);
   int height=orImg.getHeight(null);
   //create buffered image from the image so any change to the image can be made
   orBufferedImage=createBufferedImageFromImage(orImg,width,height,false);
   //create the blank buffered image
   //the update image data is stored in the buffered image   
   bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
   imageLoaded=true; //now the image is loaded
   }catch(Exception e){System.exit(-1);}
  }     
   
    
 //initialize variables
  public void initialize(){
   imageLoaded=false; 
   actionSlided=false;
   actionResized=false;
   actionCompressed=false;
   actionTransparent=false;
   actionRotated=false;
   actionDraw=false;
   drawn=false;
   dirHor=false;
   c=null;
   radian=0.0f;
   e=0.0f;
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

     private class PicturePanel extends JPanel{
         public void paint(Graphics g){
             g.drawImage(img, 0, 0, this);
         }
         
     }

         public String getDescription(){
             return "Image files (*.jpg, *.gif, *.png)";
         }
     
public DefaultMutableTreeNode makeShow(String title, DefaultMutableTreeNode parent)
    {
        DefaultMutableTreeNode show;
        show = new DefaultMutableTreeNode(title);
        parent.add(show);
        return show;
    }
 
private DefaultMutableTreeNode getTagTree (){
     
    this.rootNode = new DefaultMutableTreeNode("Photo Albums");
    return rootNode;
   
 
}
    public void tree1Changed()
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        
        if (node == null) return;
        
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()){
            if (nodeString != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(nodeString);
            img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            this.repaint();
        }
    }
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
            JOptionPane.showMessageDialog(MiAlbum1.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(MiAlbum1.this, "Please enter a Tag","Error", JOptionPane.INFORMATION_MESSAGE);
            
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
         
         
        //This will get selected node may need to change it to find if You can create a method that will return you all the matching nodes. You can do it by iterating over all the nodes in the tree and check if there names matches the one in the set.
/*
   public java.util.List<TreePath> find(DefaultMutableTreeNode root, Set<String> s) {
        java.util.List<TreePath> paths = new ArrayList<>();
@SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (s.contains(node.toString())) {
                paths.add(new TreePath(node.getPath()));
            }
        }
        return paths;
    }
You can then call this method passing the tree node and Set of strings. Please note that you will need to cast the root to DefaultMutableTreeNode because getRoot returns Object.
java.util.List<TreePath> treePaths=   find((DefaultMutableTreeNode)tree.getModel().getRoot(), someSet);
Then iterate over treePaths and invoke removeSelectionPath to deselect the nodes
    for (TreePath treePath : treePaths) {
        tree.getSelectionModel().removeSelectionPath(treePath);
    }*/
       
        //if (parent.toString() == getTagName){
        //    model.insertNodeInto(new DefaultMutableTreeNode(getTagName),parent, parent.getChildCount());
       // }
        

        
        //tagList.put(tag.getType(), tag.getValue());
 
               
// 
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
       DefaultMutableTreeNode selectedNode = getSelectedNode();
       if (selectedNode == null){
           JOptionPane.showMessageDialog(MiAlbum1.this, "Select a Tag to Change", "Error", JOptionPane.ERROR_MESSAGE);
       }
       else { String newName = JOptionPane.showInputDialog(MiAlbum1.this, "Enter a new tag name");
       // Insert code to replace selectedNode with newName
       }
   }
   
   
   ////start the ImageBrightness class
 //The ImageBrightness class represents the interface to allow the user to make the image 
 //brighter or darker by changing the value of the image slider
 //The ImageBrightness class is in the Main class
 public class ImageBrightness extends JFrame implements ChangeListener{
  JSlider slider;
 
  ImageBrightness(){
  addWindowListener(new WindowAdapter(){
     public void windowClosing(WindowEvent e){
      dispose();
      
     }
    });
  Container cont=getContentPane();  
  slider=new JSlider(-10,10,0); 
  slider.setEnabled(false);
  slider.addChangeListener(this);
  cont.add(slider,BorderLayout.CENTER); 
  slider.setEnabled(true);
  setTitle("Image brightness");
  setPreferredSize(new Dimension(300,100));
  setVisible(true);
  pack();
  enableSlider(false);
  }
  public void enableSlider(boolean enabled){
   slider.setEnabled(enabled);
  }
  public void stateChanged(ChangeEvent e){
    ia.setValue(slider.getValue()/10.0f);
    ia.setActionSlided(true);   
    ia.filterImage();
    ia.repaint();
    enableSaving(true);
   
  }

 } ////end of the ImageBrightness class
   
    public void setValue(float value){ 
   e=value;
  } 
    public void enableSaving(boolean f){
 // msaveas.setEnabled(f);
 // msave.setEnabled(f); 
  
  }
}