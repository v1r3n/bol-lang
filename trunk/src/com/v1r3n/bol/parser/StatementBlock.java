/**
 * 
 */
package com.v1r3n.bol.parser;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;

/**
 * @author viren
 *
 */
public class StatementBlock extends AbstractBlock {

	private String startSymbol;
	
	private String terminalSymbol;
	
	public StatementBlock(Node parent,  String startSymbol, String terminalSymbol) {
		super(parent, NodeType.STATEMENT_BLOCK);
		this.startSymbol = startSymbol;
		this.terminalSymbol = terminalSymbol;
	}

	public StatementBlock(Node parent) {
		super(parent, NodeType.STATEMENT_BLOCK);
		this.startSymbol = "(";
		this.terminalSymbol = ")";
	}
	
	@Override
	public String getTerminator() {
		return terminalSymbol;
	}

	@Override
	public String getStartSymbol(){
		return startSymbol;
	}
	
	public String toString(){
		return "@{" + children  + "}";
	}
}
