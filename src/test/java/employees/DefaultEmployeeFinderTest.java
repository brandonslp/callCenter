package employees;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DefaultEmployeeFinderTest {

	@Test
	public void testFindOperator() {
		Employee operator = new Employee("Juan", EmployeeRole.OPERATOR);
		Employee supervisor = new Employee("Carlos", EmployeeRole.SUPERVISOR);
		Employee director = new Employee("John", EmployeeRole.DIRECTOR);
		List<Employee> employees = Arrays.asList(operator, supervisor, director);
		EmployeeFinderStrategy strategy = new DefaultEmployeeFinder();
		Employee employee = strategy.find(employees);
		assertNotNull(employee);
		assertThat(employee.getRole(), is(EmployeeRole.OPERATOR));
	}
	
	@Test
	public void testFindSupervisor() {
		Employee operator = mock(Employee.class);
		when(operator.getState()).thenReturn(EmployeeState.BUSY);
		Employee supervisor = new Employee("Carlos", EmployeeRole.SUPERVISOR);
		Employee director = new Employee("John", EmployeeRole.DIRECTOR);
		List<Employee> employees = Arrays.asList(operator, supervisor, director);
		EmployeeFinderStrategy strategy = new DefaultEmployeeFinder();
		Employee employee = strategy.find(employees);
		assertNotNull(employee);
		assertThat(employee.getRole(), is(EmployeeRole.SUPERVISOR));
	}
	
	@Test
	public void testFindDirector() {
		Employee operator = mock(Employee.class);
		when(operator.getState()).thenReturn(EmployeeState.BUSY);
		Employee supervisor = mock(Employee.class);
		when(supervisor.getState()).thenReturn(EmployeeState.BUSY);
		Employee director = new Employee("John", EmployeeRole.DIRECTOR);
		List<Employee> employees = Arrays.asList(operator, supervisor, director);
		EmployeeFinderStrategy strategy = new DefaultEmployeeFinder();
		Employee employee = strategy.find(employees);
		assertNotNull(employee);
		assertThat(employee.getRole(), is(EmployeeRole.DIRECTOR));
	}
	

}
