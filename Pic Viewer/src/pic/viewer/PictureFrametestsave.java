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
/**
 *
 * @author Andrew
 */
public class PictureFrametestsave extends JFrame implements TreeSelectionListener {
    
    Image img;
    Image iconImage;
    JButton getPictureButton;
    JButton getTag;
    private JLabel showName;

    private JTextField textTag; 
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultTreeModel innerModel;
    private DefaultMutableTreeNode rootNode;
    
    private File file;
    private HashMap tagList; 
    private DefaultMutableTreeNode tag1, category, pic;
    private JPanel panel2;
    private String nodeString;
  
    public static void main (String [] args){

        new PictureFrametestsave();
       
    }
    
    
    public PictureFrametestsave(){
       
        this.setSize(600,600);
        this.setTitle("Mi Pics");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        final File saveF = new File("saveTreetest.ser");
        
        if (saveF.exists()){
         try{
        ObjectInputStream ois= new ObjectInputStream(new FileInputStream(saveF));
        model = (DefaultTreeModel)ois.readObject();
        ois.close();
        
        }
        catch(IOException  | ClassNotFoundException err)
        {System.out.println("We had a file loading issue");
        }
        }
         if(model ==null){
             model = new DefaultTreeModel(new DefaultMutableTreeNode("Photo Albums"));    
         }
         
         //final DefaultTreeModel innerModel = model;
          tree = new JTree(model);
        
         // tree.add(category);
          tree.setEditable(true);
          category = new DefaultMutableTreeNode("Books for Java Programmers");
        //  tree.add(category);
         this.addWindowListener(new WindowAdapter(){
            public void windowClosed(WindowEvent event){
                try{
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveF));
                    oos.writeObject(model);
                    oos.close();
                }
                catch(IOException err){
                    err.printStackTrace();
                }
                System.exit(0);
            }
        });
         
        tree.addTreeSelectionListener(this);
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(150, 200));
        JPanel panel2 = new JPanel();
        panel2.add(scroll);
        this.add(panel2, BorderLayout.WEST);
        
            
       
    
    
        
 
        

          //this.validate();
          // this.repaint();
        
        JPanel picPanel = new PicturePanel();
        this.add(picPanel, BorderLayout.CENTER);
        
         JPanel buttonPanel = new JPanel();
        getPictureButton = new JButton ("Open Picture");
        getPictureButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        getPictureButtonClick();
      }
    });
        buttonPanel.add(getPictureButton);
              
        textTag = new JTextField(10);
        buttonPanel.add(textTag);
        getTag = new JButton ("Add picture tags");
        getTag.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addTag();
      }
    });
        buttonPanel.add(getTag);
        
        JButton ChangeTag = new JButton("Change Tag");
        //ChangeTag.addActionListener(e -> changeTag());
        buttonPanel.add(ChangeTag);
        
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
        
    }

    public final void getPictureButtonClick(){
     
         String file = getImageFile();
        if (file != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(file);
            img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            this.repaint();
            
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
            return file.getPath();
        }
        else
            return null;
        
    }
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

    if (node == null) return;
    
    Object nodeInfo = node.getUserObject();
    
    this.nodeString = nodeInfo.toString();
    if (node.isLeaf()){
        System.out.println("Is leaf");
        
        
        System.out.println( nodeString);
         if (nodeString != null){
            Toolkit kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(nodeString);
            img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            this.repaint();
        // addIcon();
    }
  }
  }

    private DefaultMutableTreeNode getStartNode() {
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
     
public DefaultMutableTreeNode addPhoto(String title, DefaultMutableTreeNode parent)
    {
        DefaultMutableTreeNode show;
        show = new DefaultMutableTreeNode(title);
        parent.add(show);
        return show;
    }
 

 

    public void tree1Changed()
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        
        if (node == null) return;
        
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()){
            
        }
    }
   
	

    public void addTag() {
        if (file == null)
        {
            JOptionPane.showMessageDialog(PictureFrametestsave.this, "Please open a photo to tag first","Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String getTagName = textTag.getText();
        Tag tag = new Tag(getTagName, file.toString());
        //DefaultMutableTreeNode parent = getSelectedNode();
      //  System.out.println(parent);
        
      
        
        if (getTagName.length() ==0)
        {
            JOptionPane.showMessageDialog(PictureFrametestsave.this, "Please enter a Tag","Error", JOptionPane.INFORMATION_MESSAGE);
            
        }
    else
        {
            System.out.println(getTagName); 
                  rootNode = (DefaultMutableTreeNode)tree.getModel().getRoot();
            System.out.println(rootNode);  
            TreePath path = tree.getLeadSelectionPath();
           
                Object last = path.getLastPathComponent();
               
               
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)last;
            node.setUserObject(last);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Node");
                model.insertNodeInto(newNode,node,0);
            
                
                
           
            DefaultMutableTreeNode getTagTree = new DefaultMutableTreeNode(getTagName);
           rootNode.add(getTagTree);
            this.model.insertNodeInto(getTagTree, rootNode, 0);   
           
          this.model.insertNodeInto(new DefaultMutableTreeNode(tag.getValue()), getTagTree, 0);
          
        
// tag1 = makeShow(tag.getType(), rootNode);   
         //  pic = makeShow(tag.getValue(), tag1); 
           //tree2 = new JTree(root1);
           //tree2.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
           //tree2.setVisibleRowCount(12);
           //tree2.addTreeSelectionListener(e -> tree1Changed());
         
           //JPanel panel2 = new JPanel ();
          // JScrollPane scroll = new JScrollPane(tree2);
         //  scroll.setPreferredSize(new Dimension(150, 200));
          // panel2.add(scroll);
       
         //  this.add(panel2, BorderLayout.WEST);
          // this.validate();
          // this.repaint();
        
        
    }
    }
  // private DefaultMutableTreeNode getSelectedNode(){
   //    return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
       
  // }
   private void addIcon(){
      /* JPanel panel3 = new JPanel();
       Toolkit kit = Toolkit.getDefaultToolkit();
       iconImg = kit.getImage(this.nodeString);
            img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            this.repaint();
       
       ImageIcon picIcon = new ImageIcon(this.nodeString);
       

       JLabel pIcon = new JLabel(picIcon);
       panel3.add(pIcon);
       this.add(panel3, BorderLayout.EAST);
       this.validate();
       this.repaint();
   */}
   /*private void changeTag(){
       DefaultMutableTreeNode selectedNode = getSelectedNode();
       if (selectedNode == null){
           JOptionPane.showMessageDialog(PictureFrametestsave.this, "Select a Tag to Change", "Error", JOptionPane.ERROR_MESSAGE);
       }
       else { String newName = JOptionPane.showInputDialog(PictureFrametestsave.this, "Enter a new tag name");
       // Insert code to replace selectedNode with newName
       }
   }*/
}

