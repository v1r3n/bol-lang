/**
 * 
 */
package com.v1r3n.bol.codegen;

import java.io.IOException;
import java.util.Properties;

import com.v1r3n.bol.CodeGenerator;
import com.v1r3n.bol.Language;
import com.v1r3n.bol.Node;

/**
 * @author viren
 *
 */
public abstract class CodeGenFactory {

	private static final Properties props = new Properties();
	
	static {
		try{
			props.load(CodeGenFactory.class.getResourceAsStream("codegen.properties"));			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static CodeGenFactory getInstance(Language language){
		
		String key = language.name().toLowerCase() + ".factory";
		String classname = props.getProperty(key);
		if(classname == null){
			throw new RuntimeException("Unsupported language runtime " + language);
		}
		try{
			
			Class<?> clazz = Class.forName(classname);
			return (CodeGenFactory)clazz.newInstance();
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	public abstract CodeGenerator getCodeGenerator(Node node);
	
}
