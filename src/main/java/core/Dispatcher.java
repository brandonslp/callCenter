package core;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import calls.Call;
import employees.Employee;
import employees.EmployeeManager;

public class Dispatcher implements Runnable{
	
	public static final Integer MAX_THREADS = 10;
	private static final Logger LOGGER =  Logger.getLogger(Dispatcher.class.getName());
	
	private boolean state; //turn on or turn off dispatcher
	private ExecutorService service;
	private EmployeeManager employeeManager;
	private ConcurrentLinkedDeque<Call> incomingCalls;
	
	
	
	public Dispatcher(EmployeeManager employeeManager) {
		super();
		if(employeeManager == null)
			throw new IllegalArgumentException("EmployeeManager cannot be null");
		this.employeeManager = employeeManager;
		this.incomingCalls = new ConcurrentLinkedDeque<Call>();
		this.service = Executors.newFixedThreadPool(MAX_THREADS);
	}

	public synchronized boolean isState() {
		return state;
	}

	public synchronized void dispatchCall(Call call) {
		LOGGER.log(Level.INFO,"A new call was received");
		this.incomingCalls.add(call);
	}
	
	/**
	 * Add employees to attends calls
	 */
	public synchronized void start() {
		this.state = true;
		for (Employee e : employeeManager.getEmployees()) {
			this.service.execute(e);
		}
	}
	
	/**
	 * stop dispatcher
	 */
	public synchronized void stop() {
		this.state = false;
		this.service.shutdown();
	}

	/**
	 * while dispatch is active, this method send call to employee for attend
	 * If incomingCalls queue has calls and
	 * If employeeManager has employees available
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isState()) {
			if(this.incomingCalls.isEmpty())
				continue;
			else {
				Employee employee = employeeManager.findEmployee();
				if(employee == null)
					continue;
				else {
					Call call = this.incomingCalls.poll();
					try {
						employee.attend(call);
					} catch (Exception e) {
						LOGGER.log(Level.WARNING, e.getMessage());
						this.incomingCalls.addFirst(call);
					}
				}
			}
		}
	}
	
	
	
}
