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
 * $varname = [value] | NUMBER
 * $varname as Type
 * $varname = value1 +/-*%^~ value2
 * 
 * </pre>
 */
public class VariableDeclaration extends Node {

	private VariableInstance var;
	
	private Expression value;
	
	private TypeName type;
	
	private String equalOrAs;
	
	private boolean firstUse;
	
	VariableDeclaration(Node parent){
		super(parent, NodeType.VARIABLE_DECLARATION);
	}
	
	
	/**
	 * @return returns if the variable instance is used for the first time in the code
	 */
	public boolean isFirstUse(){
		return this.firstUse;
	}
	
	/**
	 * @param value
	 */
	public void setFirstUse(boolean value){
		this.firstUse = value;
	}
	
	/**
	 * @return Returns the instance name for the variable
	 */
	public String getVarName(){
		return this.var.getInstanceName();
	}
	
	/**
	 * @return
	 */
	public boolean hasType(){
		return ( (equalOrAs != null) && equalOrAs.equalsIgnoreCase(("as")) );
	}
	
	/**
	 * @return
	 */
	public boolean isAssigned(){
		return !hasType();
	}
	
	public String getVarType(){
		if(hasType()){
			return type.toString();
		}
		return null;
	}
		
	/**
	 * @return
	 */
	public Expression getValue(){
		return this.value;
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		
		String dollarSign = reader.nextToken();
		assertExpected(dollarSign, "$");

		var = new VariableInstance(this);
		var.parse(reader);
		String[] token = reader.peekTokens(2);
		
		if("=".equals(token[0]) && !"=".equals(token[1])){
			equalOrAs = reader.nextToken();
			value = new Expression(this);
			value.parse(reader);
			
		}else if("as".equalsIgnoreCase(token[0])){
			equalOrAs = reader.nextToken();
			type = new TypeName(this);
			type.parse(reader);
		}
		
		return this;	
	
	}

	public String toString(){
		return "$" + var + " " + isFirstUse() + " " + (equalOrAs==null?"":equalOrAs) + " " + (value == null?type:value);
	}
	
}
