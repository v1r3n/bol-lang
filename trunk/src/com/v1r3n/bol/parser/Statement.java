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
public class Statement extends Node {

		
	private static final String TERMINATOR = ";";
	
	private String statement;
	
	Statement(Node parent) {
		super(parent, NodeType.STATEMENT);
	}

	/**
	 * do something with $abc's name and $gef's work {}
	 */
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		
		StringBuffer stmtbuf = new StringBuffer();
		
		while (reader.hasNext()) {
			String token = reader.peekToken();
			if ("$".equals(token)) {
				//Instance of the variable				
				//VariableDeclaration vi = new VariableDeclaration(this);
				VariableInstance vi = new VariableInstance(this);
				vi.parse(reader);
				children.add(vi);				
				//stmtbuf.append("#" + children.size() + " ");
				stmtbuf.append("#");
			} else if ("[".equals(token)) {
				
				ObjectInstance oi = new ObjectInstance(this);
				oi.parse(reader);
				children.add(oi);								
				//stmtbuf.append("#" + children.size());
				stmtbuf.append("#");
			} else if ("\"".equals(token) || "'".equals(token)) {
				
				QuotedString qs = new QuotedString(this,token.charAt(0));
				qs.parse(reader);
				children.add(qs);								
				//stmtbuf.append("#" + children.size());
				stmtbuf.append("#");
			} else if (isNumber(token)) {
				NumberInstance ni = new NumberInstance(this);
				ni.parse(reader);
				children.add(ni);								
				//stmtbuf.append("#" + children.size());
				stmtbuf.append("#");
			}else if ("(".equals(token)) {
				Node statementBlock = new StatementBlock(this);
				statementBlock.parse(reader);
				children.add(statementBlock);
				//stmtbuf.append(" {#" + children.size() + "}");
				stmtbuf.append("{#}");
			}else if (TERMINATOR.equals(token)) {			
				reader.nextToken(); // Consume the token
				statement = stmtbuf.toString();
				return this;
			}else if (")".equals(token)) {			
				statement = stmtbuf.toString();
				return this;
			}else if (isOperator(token)) {			
				statement = stmtbuf.toString();
				return this;
			} else {				
				Character c = reader.nextChar();				
				if(!Character.isWhitespace(c)){
					stmtbuf.append(c);
				}
			}
		}

		throw new ParserException(TERMINATOR + " missing! read=(" + stmtbuf.toString() + "), CHILDREN: " + children);
	}
	
	public String getStatement(){
		return this.statement;
	}
	public String toString(){
		return statement + "()" + (children.size() > 0?""+children:"");		
	}	
	
}
