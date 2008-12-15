/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import java.io.IOException;
import java.io.OutputStream;

import com.v1r3n.bol.CodeGenerationException;
import com.v1r3n.bol.parser.StatementBlock;

/**
 * @author viren
 *
 */
public class StatementBlockGenerator extends JavaCodeGenerator {

	private StatementBlock block;
	
	StatementBlockGenerator(StatementBlock block){
		this.block = block;
	}
	
	@Override
	public void generate(OutputStream out) throws CodeGenerationException, IOException {

	}

}
