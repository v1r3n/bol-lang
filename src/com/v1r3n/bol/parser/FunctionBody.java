/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.ByteArrayInputStream;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;


/**
 * @author viren
 *
 */
public class FunctionBody extends AbstractBlock {

	private static final String TERMINATOR = "#end";
	
	public FunctionBody(Node parent){
		super(parent, NodeType.FUNCTION);
	}

	@Override
	public String getTerminator() {
		return TERMINATOR;
	}
	
	@Override
	public String getStartSymbol(){
		return "#begin";
	}

	public static void main(String[] args) throws Exception {

		String a = "#begin hi there. $name = 1+2 [name='viren c++ baraiya']. #end";
		InputReader reader = new InputReader(new ByteArrayInputStream(a.getBytes()));
		FunctionBody body = new FunctionBody(null);
		body.parse(reader);
		
		System.out.println(body.children);
	
	}
}
