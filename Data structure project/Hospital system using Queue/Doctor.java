import java.util.Random;

/**
 * A class representing a doctor.
 * 
 */
public class Doctor {
	private int busy_until;
	private int id;
	private int num_patients;
	private int busy_time;
	
	private static Random random = new Random(Parameters.seed);
	private static int nextID = 0;

	public Doctor() {
		this.busy_until = 0;
		this.id = nextID++;
		this.num_patients = 0;
		this.busy_time = 0;
	}

	public int busy_until() {
		return busy_until;
	}
	

	public int id() {
		return id;
	}
	

	public boolean busy(int time) {
		return time < busy_until;
	}
	

	public void see_patient(Patient p, int time) {
		if (busy(time)) return;
		num_patients++;
		int max_tt = Parameters.max_treatment_time[p.urgency()-1];
		int min_tt = Parameters.min_treatment_time[p.urgency()-1];
		int tt_range = max_tt-min_tt;
		int dT = random.nextInt(tt_range)+min_tt;
		busy_until = time + dT;
		busy_time += dT;
	}
	
	public String toString() {
		return "Doctor " + id + 
				", " + num_patients + " patients seen" +
				", busy until " + busy_until + 
				", busy for " + busy_time + " steps" + 
				", busy-ness = " + (((double)busy_time)/busy_until); 
	}
	
	public boolean equals(Object o) {
		if (o instanceof Doctor)
			return ((Doctor)o).id() == this.id;
		return false;
	}
	
	public Object[] getReport() {
		return new Object[]{num_patients,busy_until,busy_time,((double)busy_time)/busy_until};
	}
}
