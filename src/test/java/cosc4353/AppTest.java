package cosc4353;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
	@Test
	public void testPrintHelloWorld() {
		Assert.assertEquals(App.getHelloWorld(), "Hello World");
	}
	
	@Test
	public void testPrintHelloWorld2() {
		Assert.assertEquals(App.getHelloWorld2(), "Hello World 2");
	}
}
