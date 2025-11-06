import java.util.ArrayList;
/**
 * The sharedData class is used to share data safely between multiple threads.
 * It keeps the main array of numbers, a matching boolean array that marks results,
 * and a flag to show if the desired number has been found.  
 * 
 * This object helps coordinate the work of threads that are checking 
 * different parts of the array at the same time.
 * 
 * @author Team Member 
 * @version 1.0 (build 2025.11.04) 
 */
public class SharedData 
{
	//The main integer array that both threads will check.
	 private ArrayList<Integer> array;
	// A boolean array showing which elements in the array met the search condition.
	private boolean [] winArray;
	//A flag used to indicate whether the searched number was found in the array.
	private boolean flag;
	// The target number that both threads are looking for
	private final int b;
	
	/**
     * Creates a new SharedData object to hold the array and the target value.
     * 
     * @param array   The array of integers that will be searched by the threads.
     * @param b       The number that the threads need to find in the array.
     */
	public SharedData(ArrayList<Integer> array, int b) {
	    this.array = array;
	    this.b = b;
	    this.winArray = new boolean[array.size()];
	    this.flag = false;
	}

	
	/**
     * Returns the boolean array that marks which numbers in the array 
     * matched the search condition.
     * 
     * @return  A boolean array where each element is true if it matched the condition.
     */
	public boolean[] getWinArray() 
	{
		return winArray;
	}
	

	 /**
     * Updates the boolean array that marks which elements in the array 
     * met the desired condition.
     * 
     * @param winArray   The updated boolean array containing the results for each position.
     */
	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}

	 /**
     * Returns the main integer array that threads are working on.
     * 
     * @return  The integer array shared by the threads.
     */
	public ArrayList<Integer> getArray() {
	    return array;
	}

	/**
     * Returns the target number that the threads are searching for.
     * 
     * @return  The number that is being searched in the array.
     */
	public int getB() 
	{
		return b;
	}
	
	 /**
     * Returns the value of the flag that shows if the target number was found.
     * 
     * @return  true if the target number was found, false otherwise.
     */
	public boolean getFlag() 
	{
		return flag;
	}
 
	/**
     * Sets the flag that tells if the target number was found in the array.
     * 
     * @param flag   true if the number was found, false otherwise.
     */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}