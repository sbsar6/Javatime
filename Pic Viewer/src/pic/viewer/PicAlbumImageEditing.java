/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pic.viewer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
//import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Andrew
 */
public class PicAlbumImageEditing extends JFrame implements TreeSelectionListener {
    
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
    
    
    public static void main (String [] args){

        new PicAlbumImageEditing();
       
    }
    
    
    public PicAlbumImageEditing (){
   
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
         
        
        this.setSize(800,600);
        this.setTitle("Mi Pics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
       
       
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
        
        JPanel picPanel = new PicturePanel();
        this.add(picPanel, BorderLayout.CENTER);
        
        JToolBar editMenu = new JToolBar();
        flipButton = new JButton ("Flip");
        flipButton.addActionListener(new ActionListener() {        
       public void actionPerformed(ActionEvent event) {
        flipImage();
      }

             private void flipImage() {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
             
    });
        
        JToolBar buttonPanel = new JToolBar();
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
                     JOptionPane.showMessageDialog(PicAlbumImageEditing.this, "Please select an album to delete first","Error", JOptionPane.INFORMATION_MESSAGE);
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
       BufferedImage bimg;
         String file = getImageFile();
        if (file != null){
  
            try {
           bimg = ImageIO.read(new File("C:\\Users\\sbsar6\\Desktop\\Java-master\\NetBeansProjects\\MiPics\\src\\mipics\\bld.jpg"));
       } catch (IOException e) {
       }
    JFrame f = new JFrame("Load Image Sample");
   f.add(bimg);
    this.add(f);
          
    //   picPanel.add(img); 
    }
    }

    /**
     *
     */
  

 
      public class LoadImageApp extends Component {
           
    BufferedImage bimg;
 
    public void paint(Graphics g) {
        g.drawImage(bimg, 0, 0, null);
    }
 
    public LoadImageApp() {
       try {
           bimg = ImageIO.read(new File("C:\\Users\\sbsar6\\Desktop\\Java-master\\NetBeansProjects\\MiPics\\src\\mipics\\bld.jpg"));
       } catch (IOException e) {
       }
 
    }
 
    public Dimension getPreferredSize() {
        if (bimg == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(bimg.getWidth(null), bimg.getHeight(null));
       }
    }
 
    public void launch() {
 
        JFrame f = new JFrame("Load Image Sample");
             
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
 
        f.add(new LoadImageApp());
        f.pack();
        f.setVisible(true);
    }
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
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

    if (node == null) return;
    
   
    if (node.isLeaf()){
          //addIcon();
             Object nodeInfo = node.getUserObject();
            Tag f = (Tag) node.getUserObject();
           System.out.println(f.getFileLocation());
            this.nodeString = f.getFileLocation().toString();
    if (nodeString != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(nodeString);
            img = img.getScaledInstance(500, -1, Image.SCALE_SMOOTH);
            this.repaint();
        }
        // addIcon();
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
            JOptionPane.showMessageDialog(PicAlbumImageEditing.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(PicAlbumImageEditing.this, "Please enter a Tag","Error", JOptionPane.INFORMATION_MESSAGE);
            
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
           JOptionPane.showMessageDialog(PicAlbumImageEditing.this, "Select a Tag to Change", "Error", JOptionPane.ERROR_MESSAGE);
       }
       else { String newName = JOptionPane.showInputDialog(PicAlbumImageEditing.this, "Enter a new tag name");
       // Insert code to replace selectedNode with newName
       }
   }
}