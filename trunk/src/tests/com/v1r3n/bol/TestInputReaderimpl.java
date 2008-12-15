/**
 * 
 */
package tests.com.v1r3n.bol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.v1r3n.bol.parser.InputReader;

/**
 * @author viren
 *
 */
public class TestInputReaderimpl {

	private static final String TEST_INPUT = "function somefunction { one and two [object instance here] }";
	
	@Test
	public void testAll(){
		try{
			
			ByteArrayInputStream bis = new ByteArrayInputStream(TEST_INPUT.getBytes());
			InputReader reader = new InputReader(bis);
			assertNotNull(reader);
			
			String f;
			for(int i=0; i < 10; i++){
				f = reader.peekToken();
				assertEquals(f, "function");
			}
		
			f = reader.nextToken();
			assertEquals(f, "function");
			
			char u = reader.nextChar();
			assertEquals(u,'s');	//as in the "[s]omefunction"
			
			//Read the next token until the white space
			String functionString = reader.nextToken();	
			assertEquals(functionString, "omefunction");	//because [s] has already been read
			
			String startBrace = reader.nextToken();			
			assertEquals(startBrace, "{");
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	

}
