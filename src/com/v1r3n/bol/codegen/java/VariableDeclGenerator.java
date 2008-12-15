package com.v1r3n.bol.codegen.java;

import java.io.IOException;
import java.io.OutputStream;

import com.v1r3n.bol.CodeGenerationException;
import com.v1r3n.bol.UnsupportedLanguageException;
import com.v1r3n.bol.parser.VariableDeclaration;

/**
 * @author viren
 *
 */
public class VariableDeclGenerator extends JavaCodeGenerator {

	private VariableDeclaration vd;
	
	VariableDeclGenerator(VariableDeclaration vd){
		this.vd = vd;
	}
	
	@Override
	public void generate(OutputStream out) throws CodeGenerationException, IOException {
		
		try{
			StringBuffer buffer = new StringBuffer();
			if(vd.isFirstUse()){
				if(vd.hasType()){
					
					buffer.append("\nObject " + vd.getVarName() + " = JAVON.create(\"" + vd.getVarType() + "\")");
					out.write(buffer.toString().getBytes());
					out.flush();
					
				}else{
					buffer.append("\nObject " + vd.getVarName() + " = JAVON.createFromValue(");
					out.write(buffer.toString().getBytes());
					out.flush();
					vd.getValue().generateCode(getLanguage(), out);
					buffer = new StringBuffer(");");
					out.write(buffer.toString().getBytes());
					out.flush();
				}				
			}
		}catch(UnsupportedLanguageException usle){
			throw new CodeGenerationException(usle.getMessage(), usle);
		}
		
	}
}
