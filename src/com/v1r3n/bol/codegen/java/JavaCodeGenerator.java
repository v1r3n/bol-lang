/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import com.v1r3n.bol.CodeGenerator;
import com.v1r3n.bol.Language;

/**
 * @author viren
 *
 */
public abstract class JavaCodeGenerator extends CodeGenerator {
	
	@Override
	public final Language getLanguage() {
		return Language.JAVA;
	}

}
