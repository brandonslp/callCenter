package employees;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EmployeeManagerTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmployeeManagerInvalid() {
		new EmployeeManager(null);
	}
	
	@Test
	public void testCreateEmployeeManagerValid() {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(EmployeeManager.buildDirector("Juan"));
		EmployeeManager manager = new EmployeeManager(employees);
		assertNotNull(manager);
	}

	@Test
	public void testBuilderOperator() {
		Employee employee = EmployeeManager.buildOperator("Juan");
		assertNotNull(employee);
		assertThat(employee.getState(), is(EmployeeState.AVAILABLE));
		assertThat(employee.getRole(), is(EmployeeRole.OPERATOR));
	}

	@Test
	public void testBuilderSupervisor() {
		Employee employee = EmployeeManager.buildSupervisor("Juan");
		assertNotNull(employee);
		assertThat(employee.getState(), is(EmployeeState.AVAILABLE));
		assertThat(employee.getRole(), is(EmployeeRole.SUPERVISOR));
	}

	@Test
	public void testBuilderDirector() {
		Employee employee = EmployeeManager.buildDirector("Juan");
		assertNotNull(employee);
		assertThat(employee.getState(), is(EmployeeState.AVAILABLE));
		assertThat(employee.getRole(), is(EmployeeRole.DIRECTOR));
	}

}
