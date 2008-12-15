/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import com.v1r3n.bol.codegen.CompilerContext;

/**
 * @author Viren
 *
 */
public class JavaCompilerContext extends CompilerContext {
	
	@Override
	public String[] resolveFunctionName(String statement) {		
		return new String[]{statement.replaceAll(" ", "").replaceAll("#", "")};
	}
	
	@Override
	protected String[] resolveTypeName(String type) {
		return new String[]{type.replaceAll(" ", "")};
	}
	@Override
	protected String generateFnName(String typename) {
		return typename.replaceAll(" ", "").replaceAll("#", "");
	}
	
	@Override
	protected String generateTypeName(String typename) {
		return typename.replaceAll(" ", "");
	}
}
