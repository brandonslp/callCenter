package calls;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CallTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCreateCallNullParams() {
		new Call(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCallTwoInvalidParams() {
		new Call(0);
	}
	
	@Test
	public void testCreateCallValid() {
		Call call = new Call(2);
		assertNotNull(call);
	}

}
