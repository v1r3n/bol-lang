/**
 * 
 */
package com.v1r3n.bol;

/**
 * @author Viren
 *
 */
public class UnsupportedLanguageException extends Exception {
	
	private static final long serialVersionUID = -6199441102691465164L;
	
	private Language language;
	
	public UnsupportedLanguageException(Language language) {
		super();
		this.language = language;
	}
	
	public Language getLanguage(){
		return this.language;
	}

}
