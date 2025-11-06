import java.util.ArrayList;

/**
 * Thread that checks if target b can be made from array elements.
 */
public class ThreadCheckArray implements Runnable {

	/** True if solution found. */
	private boolean flag;
	/** Marks chosen elements. */
	private boolean[] winArray;
	/** Shared data between threads. */
	private SharedData sd;
	/** Array to check. */
	private ArrayList<Integer> array;
	/** Target value. */
	private int b;

	/**
	 * Constructor.
	 * @param sd shared data object
	 */
	public ThreadCheckArray(SharedData sd) {
		this.sd = sd;

		synchronized (sd) {
			array = sd.getArray();
			b = sd.getB();
		}

		winArray = new boolean[array.size()];
	}

	/**
	 * Recursive check.
	 * @param n index
	 * @param b target
	 */
	void rec(int n, int b) {
		synchronized (sd) {
			if (sd.getFlag())
				return;
		}

		if (n == 1) {
			int last = array.get(n - 1);
			if (b == 0 || b == last) {
				flag = true;
				synchronized (sd) {
					sd.setFlag(true);
				}
			}
			if (b == last)
				winArray[n - 1] = true;
			return;
		}

		rec(n - 1, b - array.get(n - 1));
		if (flag)
			winArray[n - 1] = true;

		synchronized (sd) {
			if (sd.getFlag())
				return;
		}

		rec(n - 1, b);
	}

	/**
	 * Runs the thread logic.
	 */
	public void run() {
		int size = array.size();

		if (size != 1) {
			if (Thread.currentThread().getName().equals("thread1"))
				rec(size - 1, b - array.get(size - 1));
			else
				rec(size - 1, b);
		}

		if (size == 1) {
			int val = array.get(0);
			if (b == val && !flag) {
				winArray[0] = true;
				flag = true;
				synchronized (sd) {
					sd.setFlag(true);
				}
			}
		}

		if (flag) {
			if (Thread.currentThread().getName().equals("thread1"))
				winArray[array.size() - 1] = true;
			synchronized (sd) {
				sd.setWinArray(winArray);
			}
		}
	}
}
