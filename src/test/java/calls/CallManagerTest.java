package calls;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CallManagerTest {

	@Test(expected = IllegalArgumentException.class)
	public void testBuildACallInvalid() {
		CallManager.buildACall(3, 2);
	}

	@Test
	public void testBuildACallValid() {
		Call call = CallManager.buildACall(1, 2);
		assertNotNull(call);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testbuildListOfCallsInvalid() {
		CallManager.buildListOfCalls(0, 1, 2);
	}
	
	@Test
	public void testbuildListOfCallsValid() {
		List<Call> calls = CallManager.buildListOfCalls(3, 1, 2);
		assertNotNull(calls);
		assertThat(calls.size(), is(3));
	}

}
