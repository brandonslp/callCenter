package employees;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class EmployeeManager {
	private ConcurrentLinkedDeque<Employee> employees;
	private EmployeeFinderStrategy finderStrategy;
	
	public EmployeeManager(List<Employee> employees) {
		super();
		if(employees == null || employees.isEmpty())
			throw new IllegalArgumentException("Queue of employees cannot be null or empty");
		this.employees = new ConcurrentLinkedDeque<Employee>(employees);
		this.finderStrategy = new DefaultEmployeeFinder();
	}

	public EmployeeManager(List<Employee> employees, EmployeeFinderStrategy finderStrategy) {
		super();
		this.employees = new ConcurrentLinkedDeque<Employee>(employees);
		this.finderStrategy = finderStrategy;
	}

	public synchronized ConcurrentLinkedDeque<Employee> getEmployees() {
		return employees;
	}
	
	public synchronized Employee findEmployee() {
		return this.finderStrategy.find(this.employees);
	}
	
	public static Employee buildOperator(String name) {
		return new Employee(name, EmployeeRole.OPERATOR);
	}
	
	public static Employee buildSupervisor(String name) {
		return new Employee(name, EmployeeRole.SUPERVISOR);
	}
	
	public static Employee buildDirector(String name) {
		return new Employee(name, EmployeeRole.DIRECTOR);
	}
	
}
