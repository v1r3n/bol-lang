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
 *
 */
public class TypeName extends Node {

	private String type;
	
	TypeName(Node parent){
		super(parent, NodeType.TYPENAME);
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		type = reader.nextToken();
		return this;
	}
	
	public String toString(){
		return type;
	}

}
