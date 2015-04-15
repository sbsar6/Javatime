/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pic.viewer;

/**
 *
 * @author Andrew
 */
public class Tag {
    
	private static final long serialVersionUID = 3223704828835351425L;
	private String tagType;	
	private String value;

	
	
	/**
	 * Creates a new tag given a type of tag and the value for it.
	 * @param type - a type of a tag, such as Location, People, etc.
	 * @param value - the value of the tag, such as New York, or John
	 */
	public Tag(String type, String value) {
		this.tagType = type;
		this.value = value;
	}
	
	
	/* get methods */
	/**
	 * returns the Tag type.
	 * 
	 */
	public String getType(){
		return tagType;
	}
	
	
	/**
	 * returns the Tag value.
	 * 
	 */
	public String getValue(){
		return value;
	}
	
	
	/* set methods */
	/**
	 * set the tag's type to the given string t.
	 */
	public void setType(String t){
		this.tagType = t;
	}
	
	
	/**
	 * set the tag's value to the given string s.
	 */
	public void setValue(String s){
		this.value = s;
	}
}