
public class Patient {
	// instance variables
	// declare instance variables
	// -----
	public String name;
	public int arr;
	public int urg;
	// constructor
	public Patient(String name, int arrival_time, int urgency) {
		// initialize instance variables
		// -----
		this.name = name;
		this.arr = arrival_time;
		this.urg = urgency;
	}
	
	// functions
	/**
	 * @return this patient's arrival time
	 */
	public int arrival_time() {
		// return this patient's arrival time
		return this.arr;
		// -----
	}
	
	/**
	 * @return this patient's urgency
	 */
	public int urgency() {
		// return this patient's urgency
		return this.urg;
		//-----
	}
	
	/**
	 * @param time - current simulation time
	 * @return wait time of this patient
	 */
	public int wait_time(int time){
		// return this patient's wait time
		return time-this.arr;
		// -----
	}
}
