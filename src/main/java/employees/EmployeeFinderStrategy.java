package employees;

import java.util.Collection;

public interface EmployeeFinderStrategy {
	
	/**
	 *  
	 * @param employees
	 * @return next available employee if the collection has available employees otherwise return null
	 */
	Employee find(Collection<Employee> employees);
}
