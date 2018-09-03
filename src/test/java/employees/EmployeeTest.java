package employees;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import calls.Call;
import calls.CallManager;

public class EmployeeTest{

	@Test(expected = NullPointerException.class)
	public void testCreateEmployeeTwoInvalidParameters() {
		new Employee(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateEmployeeOneInvalidParameters() {
		new Employee("Juan", null);
	}
	
	@Test
	public void testCreateEmployeeValid() {
		Employee employee = new Employee("Juan", EmployeeRole.OPERATOR);
		assertNotNull(employee);
		assertThat(employee.getState(), is(EmployeeState.AVAILABLE));
		assertThat(employee.getRole(), is(EmployeeRole.OPERATOR));
	}
	
	@Test
	public void testAttendCallWhileAvailable() throws InterruptedException {
		Employee employee = new Employee("Juan", EmployeeRole.OPERATOR);
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(employee);
		Call call = CallManager.buildACall(2,3);
		employee.attend(call);
		service.awaitTermination(4, TimeUnit.SECONDS);
		assertThat(employee.getAttendedCalls().size(), is(1));
	}
	
	@Test
	public void testAttendCallWhileBusy() throws InterruptedException {
		Employee employee = new Employee("Juan", EmployeeRole.OPERATOR);
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(employee);
		Call call1 = CallManager.buildACall(1,2);
		Call call2 = CallManager.buildACall(3,4);
		Call call3 = CallManager.buildACall(5,6);
		employee.attend(call1);
		employee.attend(call2);
		employee.attend(call3);
		TimeUnit.SECONDS.sleep(1);
		assertThat(employee.getState(), is(EmployeeState.BUSY));
		service.awaitTermination(15, TimeUnit.SECONDS);
		assertThat(employee.getAttendedCalls().size(), is(3));
	}

}
