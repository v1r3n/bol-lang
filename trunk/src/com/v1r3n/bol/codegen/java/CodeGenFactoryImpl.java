/**
 * 
 */
package com.v1r3n.bol.codegen.java;

import com.v1r3n.bol.CodeGenerator;
import com.v1r3n.bol.Node;
import com.v1r3n.bol.codegen.CodeGenFactory;
import com.v1r3n.bol.parser.Expression;
import com.v1r3n.bol.parser.FunctionBody;
import com.v1r3n.bol.parser.Operator;
import com.v1r3n.bol.parser.Statement;
import com.v1r3n.bol.parser.VariableDeclaration;

/**
 * @author viren
 *
 */
public class CodeGenFactoryImpl extends CodeGenFactory {

	@Override
	public CodeGenerator getCodeGenerator(Node node) {
		
		if(node instanceof FunctionBody){			
			return new FunctionBodyGenerator((FunctionBody)node);			
		}else if(node instanceof Statement){
			return new StatementGenerator((Statement)node);
		}else if(node instanceof VariableDeclaration){
			return new VariableDeclGenerator((VariableDeclaration)node);
		}else if(node instanceof Expression){
			return new ExpressionGenerator((Expression)node);
		}else if(node instanceof Operator){
			return new OperatorGenerator((Operator)node);
		}else{
			//return new NOPGenerator();
		}
		
		
		throw new IllegalArgumentException(node.getType() + " not supported yet");
	}

}
