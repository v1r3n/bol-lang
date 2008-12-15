/**
 * 
 */
package com.v1r3n.bol;

import java.util.Hashtable;

import com.v1r3n.bol.codegen.CompilerContext;
import com.v1r3n.bol.codegen.java.JavaCompilerContext;



/**
 * @author Viren
 * 
 */
public abstract class ContextFactory {
	
	private static Hashtable<Language, CompilerContext> contexts = new Hashtable<Language, CompilerContext>();
	
	public static final CompilerContext getCompilerContext(Language target){
		CompilerContext ctx = contexts.get(target);
		
		if(ctx == null){
			synchronized(CompilerContext.class){
				if(target.equals(Language.JAVA)){
					ctx = new JavaCompilerContext();
					contexts.put(Language.JAVA, ctx);
				}
			}
		}
		
		if(ctx == null){
			throw new UnsupportedOperationException("Unsupported target " + target.name());
		}
		
		return ctx;
	}
}
