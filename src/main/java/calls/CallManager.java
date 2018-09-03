package calls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * This class is just a call generator
 */

public class CallManager {
	
	public static Call buildACall(int minSeconds, int maxSeconds) {
		if(maxSeconds < minSeconds && minSeconds < 0)
			throw new IllegalArgumentException("minSecond parameter must be greater than zero and less than maxSeconds");
		return new Call(ThreadLocalRandom.current().nextInt(minSeconds, maxSeconds + 1));
	}
	
	public static List<Call> buildListOfCalls(Integer listSize, int minSeconds, int maxSeconds){
		if(listSize == null || listSize <= 0)
			throw new IllegalArgumentException("listSize must be greater than zero");
		List<Call> calls = new ArrayList<Call>();
		for (int i = 0; i < listSize; i++) {
			calls.add(CallManager.buildACall(minSeconds, maxSeconds));
		}
		return calls;
	}
	
}
