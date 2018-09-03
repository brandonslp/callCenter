package employees;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import calls.Call;

public class Employee implements Runnable{
	
	private static final Logger LOGGER =  Logger.getLogger(Employee.class.getName());
	
	private String name;
	private EmployeeRole role;
	private EmployeeState state;
	private ConcurrentLinkedDeque<Call> incomingCalls;
	private ConcurrentLinkedDeque<Call> attendedCalls;
	
	public Employee(String name, EmployeeRole role) {
		super();
		if(name == null || role == null)
			throw new NullPointerException("name o role params cannot be null");
		this.name = name;
		this.role = role;
		this.state = EmployeeState.AVAILABLE;
		this.incomingCalls = new ConcurrentLinkedDeque<Call>();
		this.attendedCalls = new ConcurrentLinkedDeque<Call>();
	}

	

	public String getName() {
		return name;
	}

	public EmployeeRole getRole() {
		return role;
	}

	public synchronized EmployeeState getState() {
		return state;
	}

	public synchronized void setState(EmployeeState state) {
		this.state = state;
	}

	public synchronized ArrayList<Call> getAttendedCalls() {
		return new ArrayList<Call>(attendedCalls);
	}
	
	/**
	 * 
	 * @param call to be add to incomingCalls queue for be attended
	 */
	public synchronized void attend(Call call) {
		LOGGER.log(Level.INFO, "Employee "+ this.getName() + " attend call with "+ call.getDuration() + "s");
		this.incomingCalls.add(call);
	}

	/**
	 * Method for read incomingCalls queue and if employee's is available, 
	 * Call be answered otherwise call be keep in the queue.
	 * If call is answered change status of employee to busy and sleep thread
	 * When call is finished, change status of employee to available again and
	 * Add call to attended queue
	 */
	public void run() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.INFO, "Employee "+ this.getName() + " starts to works");
		while(true) {
			if(!this.incomingCalls.isEmpty()) {
				Call call = this.incomingCalls.poll();
				this.setState(EmployeeState.BUSY);
				LOGGER.log(Level.INFO, "Employee "+ this.getName() + " answer call with " + call.getDuration() +"s of duration");
				try {
					TimeUnit.SECONDS.sleep(call.getDuration());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.INFO, "Call of employee " + this.getName() + " was interruped");
				} finally {
					this.setState(EmployeeState.AVAILABLE);
				}
				this.attendedCalls.add(call);
				LOGGER.log(Level.INFO, "Employee "+ this.getName() + " hang up call with " + call.getDuration() +"s of duration");
			}
		}
	}
	
}
