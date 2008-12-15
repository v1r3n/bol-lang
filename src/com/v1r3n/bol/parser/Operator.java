/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.IOException;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author viren
 *
 */
public class Operator extends Node {

	private String operator;
	
	Operator(Node parent){
		super(parent, NodeType.OPERATOR);		
	}
	
	Operator(Node parent, String operator){
		super(parent, NodeType.OPERATOR);
		this.operator = operator;
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		operator = reader.nextToken();
		String next = reader.peekToken();
		if("=".equals(next)){
			operator += reader.nextToken();
		}
		return this;
	}

	public String getOperator(){
		return this.operator;
	}
	
	public String toString(){
		return operator;
	}

}
