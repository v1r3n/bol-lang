/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.IOException;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author Viren
 * expression_group = ( expression )
 */
public class ExpressionGroup extends Node {

	private Expression expression;
	
	ExpressionGroup(Node parent){
		super(parent, NodeType.EXPRESSION_GROUP);
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {

		String leftPara = reader.nextToken();
		assertExpected(leftPara, "(");
		
		
		expression = new Expression(this);
		expression.parse(reader);
		children.add(expression);
				
		String rightPara = reader.nextToken();
		assertExpected(rightPara, ")");

		return this;
	}

	public String toString(){
		return "{" + expression.toString() + "}";
	}

}
