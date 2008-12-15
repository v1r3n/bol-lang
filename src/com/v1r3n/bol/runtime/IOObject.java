/**
 * 
 */
package com.v1r3n.bol.runtime;

import java.util.Date;
import java.util.Map;

/**
 * @author Viren
 *
 */
public class IOObject {
	
	
	public String getTypeName(){
		return null;
	}
	
	public String stringValue(String property){
		return null;
	}
	public String[] stringValues(String property){
		return null;
	}
	
	public Number numberValue(String property){
		return null;
	}
	public Number[] numberValues(String property){
		return null;
	}
	
	public Date dateValue(String property){
		return null;
	}
	public Date[] dateValues(String property){
		return null;
	}
	
	public String[] getPropertyNames(){
		return null;
	}
	
	public static final IOObject create(String typeName, Object obj){
		return null;
	}
	
	public static final IOObject create(String typeName, Map<String, ? extends Object> properties){
		return null;
	}	

}
