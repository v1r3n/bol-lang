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
 * <pre>
 * 	expr -> expr OPR expr
 *  expr -> (expr)
 *  expr -> object_instance
 *  expr -> number
 *  expr -> statement_block
 *  expr -> statement
 * </pre>
 * 2+3*4/5
 */
public class Expression extends Node {
	
	private Node left;
	
	private Node operator;
	
	private Node right;
	
	Expression(Node parent){
		super(parent, NodeType.EXPRESSION);
	}
	
	Expression(Node parent, Node left){
		super(parent, NodeType.EXPRESSION);
		this.left = left;
	}
	
	public Node getLeft(){
		return this.left;
	}
	
	public Node getRight(){
		return this.right;
	}
	
	public Node getOperator(){
		return this.operator;
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		
		if(isUnaryOperator(reader.peekToken())){
			operator = new Operator(this);
			operator.parse(reader);			
			right = parseExpression(reader);
			
			return this;			
		}
		
		if(left == null){
			left = parseExpression(reader);
		}
				
		if(isOperator(reader.peekToken())){
			//Binay expression expr OPR expr type
			operator = new Operator(this);
			operator.parse(reader);			
			right = parseExpression(reader);
		}
		return this;
	}

	private Node parseExpression(InputReader reader) throws ParserException, IOException {
	
		String next = reader.peekToken();
		Node node = null;
		if(isUnaryOperator(reader.peekToken())){
			node = new Expression(this);
			node.parse(reader);
			return node;
		}
		
		if("[".equals(next)){
			//Object instance
			node = new ObjectInstance(this);
			node.parse(reader);				
		}else if("(".equals(next)){				
			//expression group
			node = new ExpressionGroup(this);
			node.parse(reader);
			
		}else if("\"".equals(next) || "'".equals(next)){				
			//expression group
			node = new QuotedString(this, next.charAt(0));
			node.parse(reader);
			
		}else if("$".equals(next)){				
			//instance of the variable			
			node = new VariableInstance(this);
			node.parse(reader);
			
		}else if(isNumber(next)){				
			//number
			node = new NumberInstance(this);
			node.parse(reader);
			
		}else{
			//statement
			node = new Statement(this);
			node.parse(reader);
		}
		
		if(isOperator(reader.peekToken())){			
			Expression expr = new Expression(this, node);
			expr.parse(reader);
			return expr;
		}else{
			return node;
		}
		
		
	}

	public String toString(){
		if(operator != null){
			return "<" + (left==null?"":left) + " " + operator + " " + right + ">";
		}
		return left.toString();
	}
	
	
	
}
