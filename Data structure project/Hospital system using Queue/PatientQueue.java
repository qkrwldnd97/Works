import java.util.LinkedList;


public class PatientQueue {

	public static Patient[] MPqueue;
	public static int front;
	public static int rear;
	//	public static int numPatient;
	private static int currentPatient;
	private static int maxPatient = 7;


	// constructor
	public MyPatientQueue() {
		// initialize instance variables
		// -----
		front = 0;
		rear = 0;
		currentPatient = 0;
		MPqueue = new Patient[maxPatient];
	}

	// functions

	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// return the number of patients in the queue
		// -----
		return currentPatient;
	}

	/**
	 * add patient to end of queue.
	 *
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// add patient to end of queue
		// resize array, if needed
		// -----
		if (rear >= MPqueue.length - 1) {
			MPqueue[MPqueue.length-1] = p;
			rear = 0;
		} else {
			MPqueue[rear] = p;
			rear++;
		}
		currentPatient++;
		// resize an array
		if (currentPatient == MPqueue.length) {
			int i = 0;
			Patient[] temp = MPqueue;
			MPqueue = new Patient[temp.length * 2];
			while (i < currentPatient) {
				MPqueue[i] = temp[front];
				if (front == temp.length - 1) {
					front = 0;
				} else { // front < MPqueue.length-1
					front++;
				}
				i++;
			}
//			i=0;
			front = 0;
			rear = currentPatient;
		}

	}

	
	public Patient dequeue() {
		// remove and return the patient at the head of the queue
		// resize array, if needed
		// -----
		if (currentPatient == 0) {
			return null;
		}
		Patient a;
		if (front == MPqueue.length - 1) {
			a = MPqueue[front];
			MPqueue[front] = null;
			front = 0;
		} else {
			a = MPqueue[front];
			MPqueue[front] = null;
			front++;
		}
		currentPatient--;
		if (MPqueue.length >= 4 * currentPatient) {
			int i = 0;
			Patient[] temp = MPqueue;
			MPqueue = new Patient[temp.length / 2];
			while (i < currentPatient) {
				MPqueue[i] = temp[front];
				if (front == temp.length - 1) {
					front = 0;
				} else { // front < MPqueue.length-1
					front++;
				}
				i++;
			}
			front = 0;
			rear = currentPatient;
		}
		return a;


	}

	/**
	 * return, but do not remove, the patient at index i
	 *
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// return, but do not remove, the patient at index i
		// -----
		if(i > MPqueue.length){
			return null;
		}else if((i+front) < MPqueue.length){
			return MPqueue[i+front];
		}else{
			return MPqueue[(i+front)%MPqueue.length];
		}
	}

	/**
	 * add patient to front of queue
	 *
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// add Patient p to front of queue
		// resize array, if needed
		// -----


		Patient temp2 = MPqueue[MPqueue.length-1];
		for (int k = MPqueue.length - 1; k > 0; k--) {
			MPqueue[k] = MPqueue[k-1];
		}
		MPqueue[0] = temp2;


		MPqueue[front] = p;
		currentPatient++;
		if(rear+1 == MPqueue.length){
			rear=0;
		}else {
			rear++;
		}

		//resize
		if (currentPatient == MPqueue.length) {
			int i = 0;
			Patient[] temp = MPqueue;
			MPqueue = new Patient[temp.length * 2];
			while (i < currentPatient) {
				MPqueue[i] = temp[front];
				if (front == temp.length - 1) {
					front = 0;
				} else { // front < MPqueue.length-1
					front++;
				}
				i++;
			}
			i=0;
			front = 0;
			rear = currentPatient;
		}

	}

	/**
	 * remove and return patient at index i from queue
	 *
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// remove and return Patient at index i from queue
		// shift patients down to fill hole left by removed patient
		// resize array, if needed
		// -----
		Patient a;
		if(i >= currentPatient || i < 0){
			return null;
		}else if( (i+front) < MPqueue.length){
			a = MPqueue[i+front];
			MPqueue[i+front] = null;
		}else{
			a = MPqueue[(i+front)%MPqueue.length];
			MPqueue[(i+front)%MPqueue.length] = null;
		}
		currentPatient--;

		//shift
		if(front<rear){
			for(int j = (front+i+1); j<=rear; j++){
				MPqueue[j-1] = MPqueue[j];
			}
		}else{
			Patient temp2 = MPqueue[0];
			for(int j = 1; j<=rear;j++){
				MPqueue[j-1] = MPqueue[j];
			}

			for(int j = front+1; j<MPqueue.length;j++){
				MPqueue[j-1] = MPqueue[j];
			}
			MPqueue[MPqueue.length-1] = temp2;
		}
		rear--;


		//resize
		if (MPqueue.length >= 4 * currentPatient) {
			int j = 0;
			Patient[] temp = MPqueue;
			MPqueue = new Patient[temp.length / 2];
			while (j < currentPatient) {
				MPqueue[j] = temp[front];
				if (front == temp.length - 1) {
					front = 0;
				} else { // front < MPqueue.length-1
					front++;
				}
				j++;
			}
			front = 0;
			rear = currentPatient;
		}
		return a;
	}

}
