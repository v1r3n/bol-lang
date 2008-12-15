/**
 * 
 */
package com.v1r3n.bol.parser;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.regex.Pattern;



/**
 * @author Viren
 * 
 */
public class InputReader {

	public static final Pattern WHITESPACE = Pattern.compile("\\s");
	
	public static final Pattern TOKEN = Pattern.compile("\\[|\\]|\\(|\\)|\\{|\\}|\\$|\\\"|\\'|\\=|\\+|\\%|\\*|\\/|\\>|\\<|\\!|\\.|\\,|\\~|\\;");
	
	private BufferedInputStream bis;
	
	private boolean eofReached;

	private LinkedList<String> lookaheads;
	
	private int lineNo;
		
	public InputReader(InputStream is){
		bis = new BufferedInputStream(is);
		eofReached = false;
		lookaheads = new LinkedList<String>();
	}
	
	public synchronized Character nextChar()  throws IOException {
		char c;
		if(lookaheads.size() > 0 && lookaheads.getFirst().length() > 0){
			String first = lookaheads.removeFirst();			
			c = first.charAt(0);
			first = first.substring(1);
			if(first.length() > 0)
				lookaheads.addFirst(first);
		}else{
			int i = bis.read();
			if(i == -1){
				eofReached = true;
				return (char)-1;
			}
			c= (char)i;
		}		
		return c;
	}

	public synchronized Character peekChar() throws IOException {
		if(lookaheads.size() > 0){
			return lookaheads.getFirst().charAt(0);
		}
		String next = peekToken();
		return next.charAt(0);
	}
	
	public synchronized String nextToken()  throws IOException {
		if(lookaheads.size() > 0){
			return lookaheads.removeFirst().trim();
		}		
		return readNextToken().trim();
	}
	
	public synchronized String peekToken() throws IOException {
		if(lookaheads.size() > 0){
			return lookaheads.getFirst().trim();
		}else{
			String token = readNextToken();
			lookaheads.addFirst(token);
			return token.trim();
		}				
	}
	
	public synchronized String[] peekTokens(int i) throws IOException {
		String token;
		int j = i;
		while(j > 0){
			int b4 = lookaheads.size();
			token = readNextToken();
			if(!"".equals(token))
				lookaheads.add(b4, token);
			if(eofReached) break;			
			j--;
		}
		String[] tokens = new String[i];
		for(int k=0; k < i && k < lookaheads.size(); k++){
			tokens[k] = lookaheads.get(k).trim();
		}
		return tokens;
	}
	
	public synchronized boolean hasNext() {		
		return (lookaheads.size() > 0) || !eofReached;		
	}
	
	public synchronized void close() throws IOException{
		bis.close();
		eofReached = true;
	}
	
	public int lineNumber() {
		return lineNo;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String readNextToken() throws IOException {
		
		StringBuffer buffer = new StringBuffer();
			
		int read;
		while((read = bis.read()) != -1){
			
			char c = (char)read;
			
			boolean isWhiteSpace = WHITESPACE.matcher(""+c).matches(); 
			
			if(isWhiteSpace && buffer.length() > 0){
				buffer.append(c);
				return buffer.toString();
			}else if(TOKEN.matcher(""+c).matches()){
				if(buffer.length() > 0){					
					lookaheads.add(""+c);
					return buffer.toString();
				}else{
					buffer.append(c);
					return buffer.toString();
				}
			}else if(!isWhiteSpace){
				buffer.append(c);
			}
		}
		if(read == -1) eofReached = true;
		return buffer.toString();
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		
//		LinkedList<String> list = new LinkedList<String>();
//		list.add("one");
//		System.out.println(list);
//		list.add("one2");
//		System.out.println(list);
//		list.add("one3");
//		System.out.println(list);
//		
//		if(true) return;
		
		String a = "one\ntwo\rthree;four";
		//a = "(statement1.).";
		//a = "$name as number";
		InputReader reader = new InputReader(new ByteArrayInputStream(a.getBytes()));
		
		//System.out.println(a);
		while(reader.hasNext()){
			
			reader.peekToken();
			reader.peekTokens(3);		
			//System.out.print(reader.nextToken());
			System.out.print(reader.nextChar());
		}		
		
		System.out.println("Done");
		
	}
	
}
