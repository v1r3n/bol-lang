/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import java.io.IOException;
import java.io.OutputStream;

import com.v1r3n.bol.CodeGenerationException;
import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.codegen.CodeGenFactory;
import com.v1r3n.bol.parser.Statement;


/**
 * @author viren
 *
 */
public class StatementGenerator extends JavaCodeGenerator {
	
	private Statement stmt;
	
	StatementGenerator(Statement stmt){
		this.stmt = stmt;
	}

	public void generate(OutputStream out) throws CodeGenerationException {
		StringBuffer buffer = new StringBuffer();
		
		try{
			if(stmt.getStatement().trim().equals("")){
				buffer.append(";\n");
			}else{
				if(stmt.getParent().getType().equals(NodeType.FUNCTION) ||
						stmt.getParent().getType().equals(NodeType.STATEMENT_BLOCK)){
					buffer.append("\n");
				}
				
				String[] function = getContext().getFunctionName(stmt.getStatement());
				if(function.length > 1){
					throw new CodeGenerationException("multiple statement resolutions found");
				}
				buffer.append(function[0]);
				buffer.append("(");
				out.write(buffer.toString().getBytes());			
				out.flush();
				
				for (int i=0; i < stmt.getChildren().size(); i++) {
					Node param = stmt.getChildren().get(i);
					CodeGenFactory.getInstance(getLanguage()).getCodeGenerator(param).generate(out);					
					out.write(",".getBytes());
					out.flush();
				}
				out.write(")".getBytes());
				out.flush();
			}			
			
			
			
		}catch(IOException ioe){
			throw new CodeGenerationException(ioe.getMessage(), ioe);
		}
	}
}
