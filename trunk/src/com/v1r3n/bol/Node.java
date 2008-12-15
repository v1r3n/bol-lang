/**
 * 
 */
package com.v1r3n.bol;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.v1r3n.bol.codegen.CodeGenFactory;
import com.v1r3n.bol.parser.InputReader;


/**
 * @author Viren
 *
 */
public abstract class Node {

	private static final Pattern operators = Pattern.compile("\\+|\\-|\\*|\\/|\\%|\\=|\\>|\\<|\\!|\\~|\\|");
	
	private Node parent;
	
	private NodeType type;
	
	protected ArrayList<Node> children;

	protected Node(Node parent, NodeType type){
		this.parent = parent;
		this.type = type;
		this.children = new ArrayList<Node>();
	}
	
	public NodeType getType(){
		return type;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	protected boolean isOperator(String token){
		return operators.matcher(token).matches();
	}
	
	protected boolean isUnaryOperator(String token){
		return "~".equals(token);
	}
	
	protected void assertExpected(String found, String expected) throws ParserException {
		if(!found.equals(expected)){
			throw new ParserException("Expected " + expected + ", found " + found);
		}
	}
	
	protected boolean isNumber(String value){
		try{			
			new BigDecimal(value);			
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
	public void generateCode(Language target, OutputStream out) throws CodeGenerationException, UnsupportedLanguageException, IOException {
		CodeGenerator codegen = getCodeGenerator(target);
		if(codegen != null)
			codegen.generate(out);
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer("\nType=" + type);
		buffer.append(",children:\n");
		for(Node child : children){
			buffer.append("\n\t" + child.toString());
		}
		return buffer.toString();
	}
	
	private CodeGenerator getCodeGenerator(Language target) throws UnsupportedLanguageException {
		return CodeGenFactory.getInstance(target).getCodeGenerator(this);
	}
	
	
	public abstract Node parse(InputReader reader) throws ParserException, IOException;
	
}
