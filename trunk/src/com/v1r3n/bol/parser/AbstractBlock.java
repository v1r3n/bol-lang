package com.v1r3n.bol.parser;

import java.io.IOException;
import java.util.HashMap;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author viren
 *
 */
public abstract class AbstractBlock extends Node {
	
	private HashMap<String, VariableDeclaration> vars;
	
	AbstractBlock(Node parent, NodeType type) {
		super(parent, type);
		this.vars = new HashMap<String, VariableDeclaration>();
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		String begin = reader.nextToken();
		assertExpected(begin, getStartSymbol());
				
		while (reader.hasNext()) {
						
			String token = reader.peekToken();
			
			if ("$".equals(token) ) {
				
				VariableDeclaration varDeclaration = new VariableDeclaration(this);
				varDeclaration.parse(reader);
				children.add(varDeclaration);
				
				VariableDeclaration existing = this.vars.get(varDeclaration.getVarName());
				if(existing == null){
					varDeclaration.setFirstUse(true);
				}
				this.vars.put(varDeclaration.getVarName(), varDeclaration);
				
			}else if ("(".equals(token)) {
				
				Node statementBlock = new StatementBlock(this);
				statementBlock.parse(reader);
				children.add(statementBlock);
				
			}else if(isOperator(token)){
				Node left = children.size() > 0?children.get(children.size()-1):null;
				Node node = new Expression(this, left);
				node.parse(reader);
				if(children.size() > 0){
					//replace the last child with the expression
					children.remove(children.size() -1);					
				}
				children.add(node);
				
			}else if ("[".equals(token)) {
				
				ObjectInstance oi = new ObjectInstance(this);
				oi.parse(reader);
				children.add(oi);								
								
			}else if (isNumber(token)) {
				
				NumberInstance ni = new NumberInstance(this);
				ni.parse(reader);
				children.add(ni);								
							
			}else if ("\"".equals(token) || "'".equals(token)) {
				
				QuotedString qs = new QuotedString(this,token.charAt(0));
				qs.parse(reader);
				children.add(qs);								
							
			}else if (token.equals(getTerminator())) {
				
				reader.nextToken();		//consume the next token
				return this;
				
			}else {
				Node statement = new Statement(this);
				statement.parse(reader);
				children.add(statement);				
			}				
		}
		
		throw new ParserException(getTerminator() + " missing!: " + getClass().getName());
	}

	public abstract String getStartSymbol();
	
	public abstract String getTerminator();
}
