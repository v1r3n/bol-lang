/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.IOException;
import java.math.BigDecimal;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author Viren
 *
 */
public class NumberInstance extends Node {

	private BigDecimal value;
	
	NumberInstance(Node parent){
		super(parent, NodeType.NUMBER_INSTANCE);
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {

		String next = reader.nextToken();
		String[] tokens = reader.peekTokens(2);		
		if(".".equals(tokens[0]) && isNumber(tokens[1])){
			reader.nextToken();			
			next = next + "."  + reader.nextToken();
		}
		this.value = new BigDecimal(next);			
		return this;
		
	}

	public String toString(){
		return "[" + value.toString() + "]";
	}
}
