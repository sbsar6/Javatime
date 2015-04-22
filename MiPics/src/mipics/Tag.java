/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipics;

/**
 *
 * @author 016171682
 */
public class Tag implements java.io.Serializable{
    
	
private String tagName;
private String fileLocation;
private String fileName;

	
	public Tag(String tagName, String fileLocation, String fileName ) {
		this.tagName = tagName;
		this.fileLocation = fileLocation;
                this.fileName = fileName;
	}
	
	
	/* get methods */
	/**
	 * returns the Tag name.
	 * 
     * @return 
	 */
	public String toString(){
            return fileName;
        }
        
        public String getTagName(){
		return tagName;
	}
	
	
	/**
	 * returns the File Location value.
	 * 
        * @return 
	 */
	public String getFileLocation(){
		return fileLocation;
	}
	/**
	 * returns the File name value.
	 * 
        * @return 
	 */
        // returns tag Name
        public String getFileName(){
                return fileName;    
        }
	
	/* set methods */
	/**
	 * set the tag's type to the given string t.
     * @param t
	 */
	public void setTagName(String t){
		this.tagName = t;
	}
	
	
	/**
	 * set the file location value to the given string s.
     * @param s
	 */
	
        public void setFileLocation(String s){
		this.fileLocation = s;
	}
        
	/**
	 * set the file name value to the given string s.
     * @param n
	 */
        public void setFileName(String n){
                this.fileName = n;
        }
        
}