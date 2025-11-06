import java.util.Scanner;
import java.util.ArrayList;

/**
 * Tests ThreadCheckArray with two threads.
 */
public class TestThreadCheckArray {
	/**
	 * Main program.
	 * @param args command line args
	 */
	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			Thread thread1, thread2;

			System.out.println("Enter array size");
			int num = input.nextInt();

			ArrayList<Integer> array = new ArrayList<>(num);
			System.out.println("Enter numbers for array");

			for (int index = 0; index < num; index++)
				array.add(input.nextInt());

			System.out.println("Enter number");
			int target = input.nextInt();

			SharedData sd = new SharedData(array, target);

			thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
			thread2 = new Thread(new ThreadCheckArray(sd), "thread2");

			// start measuring total runtime
			long startTime = System.currentTimeMillis();

			thread1.start();
			thread2.start();
			try {
				thread1.join();
				thread2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// end measuring total runtime
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			// print total runtime
			System.out.println("Total threads runtime: " + totalTime + "ms");

			if (!sd.getFlag()) {
				System.out.println("Sorry");
				return;
			}

			System.out.println("Solution for b : " + sd.getB() + ", n = " + sd.getArray().size());
			System.out.println();

			// print results
			System.out.print("I:   ");
			for (int i = 0; i < sd.getArray().size(); i++) {
				System.out.printf("%-5d", i);
			}
			System.out.println();

			System.out.print("A:   ");
			for (int numValue : sd.getArray()) {
				System.out.printf("%-5d", numValue);
			}
			System.out.println();

			System.out.print("C:   ");
			for (boolean win : sd.getWinArray()) {
				System.out.printf("%-5d", win ? 1 : 0);
			}
			System.out.println();
		}
	}
}
