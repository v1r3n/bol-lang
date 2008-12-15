/**
 * 
 */
package tests.com.v1r3n.bol;

import java.io.InputStream;

import org.junit.Test;

import com.v1r3n.bol.Language;
import com.v1r3n.bol.Node;
import com.v1r3n.bol.parser.FunctionBody;
import com.v1r3n.bol.parser.InputReader;


/**
 * @author viren
 *
 */
public class TestFunctionBody {

	@Test
	public void testAll() throws Exception {
		FunctionBody body = new FunctionBody(null);
		
		try {
			
			String input = "tests/com/v1r3n/bol/data/test3.hiir";
			InputStream is = getClass().getClassLoader().getResourceAsStream(input);
			InputReader reader = new InputReader(is);			
			
			body.parse(reader);
			for(Node node : body.getChildren()){
				System.out.println(node.toString() );
			}
			System.out.println("\n\n");

			body.generateCode(Language.JAVA, System.out);
			
		} catch (Exception e) {			
			throw e;
		}
	}
}
