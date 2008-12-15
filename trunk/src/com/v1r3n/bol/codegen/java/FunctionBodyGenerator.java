/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import java.io.IOException;
import java.io.OutputStream;

import com.v1r3n.bol.CodeGenerationException;
import com.v1r3n.bol.Language;
import com.v1r3n.bol.Node;
import com.v1r3n.bol.UnsupportedLanguageException;
import com.v1r3n.bol.parser.FunctionBody;


/**
 * @author Viren
 *
 */
public class FunctionBodyGenerator extends JavaCodeGenerator {

	private FunctionBody fndef;
	
	FunctionBodyGenerator(FunctionBody fndef){
		this.fndef = fndef;
	}
	
	public void generate(OutputStream out) throws CodeGenerationException, IOException {
		try{
			
			StringBuffer buffer = new StringBuffer("{\n");
			out.write(buffer.toString().getBytes());
						
			for(Node node : fndef.getChildren()){
				node.generateCode(Language.JAVA, out);
			}
			
			out.write("}".getBytes());
			
			
		}catch(UnsupportedLanguageException usle){
			throw new CodeGenerationException(usle.getMessage(), usle);
		}
	}

}
