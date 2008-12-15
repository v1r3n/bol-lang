/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import java.io.IOException;
import java.io.OutputStream;

import com.v1r3n.bol.CodeGenerationException;
import com.v1r3n.bol.codegen.CodeGenFactory;
import com.v1r3n.bol.parser.Expression;

/**
 * @author viren
 *
 */
public class ExpressionGenerator extends JavaCodeGenerator {

	private Expression expr;
	
	ExpressionGenerator(Expression expr){
		this.expr = expr;
	}
	
	@Override
	public void generate(OutputStream out) throws CodeGenerationException, IOException {
		
		if(expr.getLeft() != null){
			CodeGenFactory.getInstance(getLanguage()).getCodeGenerator(expr.getLeft()).generate(out);
		}
		if(expr.getOperator() != null){
			CodeGenFactory.getInstance(getLanguage()).getCodeGenerator(expr.getOperator()).generate(out);
		}
		if(expr.getRight() != null){
			CodeGenFactory.getInstance(getLanguage()).getCodeGenerator(expr.getRight()).generate(out);
		}
	}

}
