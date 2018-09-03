package core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import calls.CallManager;
import employees.Employee;
import employees.EmployeeManager;

public class DispatcherTest {
	
	private static final int CALLS = 10;
	private static final int MIN_CALL_DURATION = 5;
	private static final int MAX_CALL_DURATION = 10;

	@Test(expected = IllegalArgumentException.class)
	public void testCreateDispatcherInvalid() {
		new Dispatcher(null);
	}
	
	@Test
	public void testCreateDispatcherValid() {
		Dispatcher  dispatcher =  new Dispatcher(mock(EmployeeManager.class));
		assertNotNull(dispatcher);
	}
	
	@Test
	public void testDispatchTenCalls() throws InterruptedException {
		List<Employee> employees = buildEmployeesList();
		EmployeeManager employeeManager = new EmployeeManager(employees);
		Dispatcher dispatcher = new Dispatcher(employeeManager);
		dispatcher.start();
		TimeUnit.SECONDS.sleep(1);
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(dispatcher);
		TimeUnit.SECONDS.sleep(1);
		CallManager.buildListOfCalls(CALLS, MIN_CALL_DURATION, MAX_CALL_DURATION)
			.stream().forEach( call -> {
				dispatcher.dispatchCall(call);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					fail();
				}
		});
		service.awaitTermination(MAX_CALL_DURATION * 2, TimeUnit.SECONDS);
		assertThat(employees.stream()
				.mapToInt(e -> e.getAttendedCalls().size())
				.sum(),
				is(CALLS));
	}
	
	private static List<Employee> buildEmployeesList(){
		Employee operator1 = EmployeeManager.buildOperator("Juan");
        Employee operator2 = EmployeeManager.buildOperator("Carlos");
        Employee operator3 = EmployeeManager.buildOperator("John");
        Employee operator4 = EmployeeManager.buildOperator("Katherine");
        Employee operator5 = EmployeeManager.buildOperator("Daniela");
        Employee operator6 = EmployeeManager.buildOperator("Karla");
        Employee supervisor1 = EmployeeManager.buildSupervisor("Jose");
        Employee supervisor2 = EmployeeManager.buildSupervisor("Andres");
        Employee supervisor3 = EmployeeManager.buildSupervisor("Andrea");
        Employee director = EmployeeManager.buildDirector("Diego");
        return Arrays.asList(
        		operator1,
        		operator2,
        		operator3,
        		operator4,
        		operator5,
        		operator6,
        		supervisor1,
        		supervisor2,
        		supervisor3,
        		director
        		);
	}
}
