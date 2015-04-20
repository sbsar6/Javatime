/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipicalbum1;

/**
 *
 * @author Andrew
 */
public class Tag implements java.io.Serializable{
    
	private static final long serialVersionUID = 3223704828835351425L;
	private String tagName;
        private String fileLocation;
        private String fileName;

	
	
	/**
	 * Creates a new tag given a type of tag and the value for it.
	 * @param type - a type of a tag, such as Location, People, etc.
	 * @param value - the value of the tag, such as New York, or John
     * @param name
	 */
	public Tag(String tagName, String fileLocation, String fileName ) {
		this.tagName = tagName;
		this.fileLocation = fileLocation;
                this.fileName = fileName;
	}
	
	
	/* get methods */
	/**
	 * returns the Tag type.
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
	 * returns the Tag value.
	 * 
     * @return 
	 */
	public String getFileLocation(){
		return fileLocation;
	}
	
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
	 * set the tag's value to the given string s.
     * @param s
	 */
	
        public void setFileLocation(String s){
		this.fileLocation = s;
	}
        
        public void setFileName(String n){
                this.fileName = n;
        }
        
}