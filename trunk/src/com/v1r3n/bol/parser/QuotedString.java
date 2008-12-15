/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.v1r3n.bol.Node;
import com.v1r3n.bol.NodeType;
import com.v1r3n.bol.ParserException;


/**
 * @author viren_baraiya
 *
 */
public class QuotedString extends Node {

	private Character quoteChar;
	
	private StringBuffer value;
	
	QuotedString(Node parent, Character quoteChar){
		super(parent, NodeType.STRING);
		this.quoteChar = quoteChar;
		this.value = new StringBuffer();
	}

	@Override
	public Node parse(InputReader reader) throws ParserException, IOException {
		String quote = reader.nextToken();
		assertExpected(quote, quoteChar.toString());
				
		boolean escaped = false;
		
		while(reader.hasNext()){
			char c = reader.nextChar();
			if(c == '\\'){
				escaped = true;				
			}else if(c == quoteChar && !escaped){
				return this;				
			}else{
				if(escaped) value.append('\\');
				value.append(c);
				escaped = false;
			}
		}
		
		throw new ParserException("String not termniated, expected " + quoteChar);
	}
	
	public String toString(){
		return "\"" + value.toString() + "\"";
	}
	public static void main(String[] args) throws Exception {
		String a = "'\"'";
		System.out.println("a=" + a);
		InputReader reader = new InputReader(new ByteArrayInputStream(a.getBytes()));
		QuotedString str = new QuotedString(null, '\'');
		str.parse(reader);
		System.out.println(str.value.toString());
		
	}
}
