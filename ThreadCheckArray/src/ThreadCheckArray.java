import java.util.ArrayList;

public class ThreadCheckArray implements Runnable {
	private boolean flag;
	private boolean[] winArray;
	private SharedData sd;
	private ArrayList<Integer> array;
	private int b;

	public ThreadCheckArray(SharedData sd) {
		this.sd = sd;

		synchronized (sd) {
			array = sd.getArray();
			b = sd.getB();
		}

		winArray = new boolean[array.size()];
	}

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