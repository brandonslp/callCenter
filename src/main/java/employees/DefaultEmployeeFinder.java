package employees;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DefaultEmployeeFinder implements EmployeeFinderStrategy{
	
	private static final Logger LOGGER =  Logger.getLogger(DefaultEmployeeFinder.class.getName());
	
	private Optional<Employee> findEmployee(List<Employee> employees, EmployeeRole role){
		return employees.stream() // find operator
				.filter(e -> e.getRole() == role)
				.findAny();
	}
	
	public Employee find(Collection<Employee> employees) {
		if(employees.isEmpty()) {
			LOGGER.log(Level.INFO, "Employees null");
			throw new NullPointerException("Employees null");
		}
		//get available employees
		List<Employee> availablesEmployees = employees.stream()
				.filter(e -> e.getState() == EmployeeState.AVAILABLE)
				.collect(Collectors.toList());
		if(availablesEmployees.isEmpty()) {
			LOGGER.log(Level.INFO, "there aren't availables employees");
			throw new NullPointerException("there aren't availables employees");
		}
		Optional<Employee> employee = this.findEmployee(availablesEmployees,EmployeeRole.OPERATOR); //find Operators
		if(!employee.isPresent()) {
			LOGGER.log(Level.INFO, "there aren't availables Operators");
			employee = this.findEmployee(availablesEmployees,EmployeeRole.SUPERVISOR); //find Supervisor
			if(!employee.isPresent()) {
				LOGGER.log(Level.INFO, "there aren't availables Supervisors");
				employee = this.findEmployee(availablesEmployees,EmployeeRole.DIRECTOR); //find Director
			}
			if(!employee.isPresent()) {
				LOGGER.log(Level.INFO, "there aren't availables Directors");
				return null;
			}
		}
		return employee.get();
	}

}
