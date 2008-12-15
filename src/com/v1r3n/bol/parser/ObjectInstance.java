/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author Viren
 *
 */
public class ObjectInstance extends Node {

	private List<Node> array;
	
	private Map<String, Node> properties;
	
	ObjectInstance(Node parent){
		super(parent, NodeType.OBJECT_INSTANCE);
		properties = new HashMap<String, Node>();		
		array = new ArrayList<Node>();
	}
	
	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		
		String left = reader.nextToken();
		assertExpected(left, "[");
		
		/**
		 * For the structures
		 * tokens[0] = key
		 * tokens[1] = "="
		 * 
		 * For the array
		 * tokens[0] = value
		 * tokens[1] = value
		 */
		String[] tokens = reader.peekTokens(2);
		if("=".equals(tokens[1])){
			return parseObject(reader);
		}else{
			return parseArray(reader);
		}
	}

	/**
	 * object -> [key_val_pair*]
	 * key_val_pair -> key '=' value ','
	 * value -> object
	 * key -> ['a'..'z']+
	 * 
	 */
	private Node parseObject(InputReader reader) throws ParserException, IOException {
		
		while(reader.hasNext()){
			
			String property = reader.nextToken();
			if("]".equals(property)){
				//This is the end
				return this;
			}
			
			String equals = reader.nextToken();
			assertExpected(equals, "=");
			String value = reader.peekToken();
			Node node = null;
						
			if("\"".equals(value)){	
				node = new QuotedString(this, '"');
			}else if("'".equals(value)){
				node = new QuotedString(this, '\'');
			}else if("[".equals(value)){
				node = new ObjectInstance(this);				
			}else if(isNumber(value)){
				node = new NumberInstance(this);				
			}else if("]".equals(value)){
				reader.nextToken();		//Consume the last token
				return this;
			}else if("$".equals(value)){
				node = new VariableDeclaration(this);				
			}else{
				node = new Statement(this);
			}
			node.parse(reader);
			properties.put(property, node);
			
			String comma = reader.nextToken();
			if("]".equals(comma)){
				return this;
			}
			assertExpected(comma, ",");
		}
		
		throw new ParserException("misssing ]!");
	}
	/**
	 * object -> array
	 * 		  |  structure
	 * 
	 * array  -> [value*]
	 * value  -> string
	 * 		  |  number
	 *        |  object
	 */
	private Node parseArray(InputReader reader) throws ParserException, IOException {
				
		while(reader.hasNext()){
			
			Node node = null;
			String value = reader.peekToken();
			if("]".equals(value)){
				reader.nextToken();
				return this;
			}
			
			if("\"".equals(value)){	
				node = new QuotedString(this, '"');
			}else if("'".equals(value)){
				node = new QuotedString(this, '\'');
			}else if("[".equals(value)){
				node = new ObjectInstance(this);				
			}else if(isNumber(value)){
				node = new NumberInstance(this);				
			}else if("]".equals(value)){
				reader.nextToken();		//Consume the last token
				return this;
			}else if("$".equals(value)){
				node = new VariableDeclaration(this);				
			}else{
				node = new Statement(this);
			}
			node.parse(reader);			
			array.add(node);	
			
			String comma = reader.nextToken();
			if("]".equals(comma)){
				return this;
			}
			assertExpected(comma, ",");
		}
		
		throw new ParserException("misssing ]!");
	}
	public String toString(){
		if(properties.size() > 0)
			return "#object: " + properties;
		else
			return array.toString();
	}

}
