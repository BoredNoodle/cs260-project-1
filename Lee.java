import java.util.Random;
import java.util.Scanner;

public class Lee {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int[] numbers = genData(100);
		int choice;
		
		/* Debug Code:
		System.out.print("UNSORTED: ");
		for (int i = 0; i < numbers.length; ++i)
			System.out.print(numbers[i] + " ");
		System.out.println("\n");
		*/
		
		do {
			System.out.println("What sorting algorithm would you like to use to sort the array?");
			
			choice = scan.nextInt();
			if (choice == -1)
				break;

			System.out.println("The selected algorithm took " + sortTime(numbers, choice) + "ms to sort the array.\n");
		} while (true);
		
		System.out.println("Program Terminated.");
		
		//Debug Code:
		
		//SelectionSort(numbers);
		//InsertionSort(numbers);
		//Quicksort(numbers, 0, numbers.length - 1);
		//MergeSort(numbers, 0, numbers.length - 1);
		
		/*
		System.out.print("\nSORTED:   ");
		for (int i = 0; i < numbers.length; ++i)
			System.out.print(numbers[i] + " ");
		*/
		scan.close();
	}

	//Selection Sort Algorithm
	public static int [] SelectionSort(int[] numbers) {
		int indexSmallest = 0;
		int temp = 0;  // Temporary variable for swap
		
		for (int i = 0; i < numbers.length - 1; i++) {
			// Find index of smallest remaining element indexSmallest = i
			for (int j = i + 1; j < numbers.length; j++)
				if ( numbers[j] < numbers[indexSmallest] )
					indexSmallest = j;
			
			// Swap numbers[i] and numbers[indexSmallest]
			temp = numbers[i];
			numbers[i] = numbers[indexSmallest];
			numbers[indexSmallest] = temp;
		}
		
		return numbers;
	}

	//Insertion Sort Algorithm
	public static int [] InsertionSort(int[] numbers) {
		int temp = 0;
		
		for (int i = 1; i < numbers.length; i++) {
			int j = i;
			
			// Insert numbers[i] into sorted part
		    // stopping once numbers[i] in correct position
			while (j > 0 && numbers[j] < numbers[j - 1]) {
				
				// Swap numbers[j] and numbers[j - 1]
				temp = numbers[j];
				numbers[j] = numbers[j - 1];
				numbers[j - 1] = temp;
				j--;
			}
		}
		
		return numbers;
	}
	
	//Used in QuickSort Algorithm (See line 116)
	public static int Partition(int[] numbers, int lowIndex, int highIndex) {
		// Pick middle element as pivot
		int midpoint = lowIndex + (highIndex - lowIndex) / 2;
		int pivot = numbers[midpoint];
		
		boolean done = false;
		while(!done) {
			// Increment lowIndex while numbers[lowIndex] < pivot
			while (numbers[lowIndex] < pivot)
				lowIndex++;
			
			// Decrement highIndex while pivot < numbers[highIndex]
		    while (pivot < numbers[highIndex])
		    	highIndex--;
		    
		    // If zero or one elements remain, then all numbers are 
		    // partitioned. Return highIndex.
		    if (lowIndex >= highIndex) {
		    	done = true;
		    }
		    else {
		    	// Swap numbers[lowIndex] and numbers[highIndex]
		    	int temp = numbers[lowIndex];
		    	numbers[lowIndex] = numbers[highIndex];
		    	numbers[highIndex] = temp;

		    	// Update lowIndex and highIndex
		    	lowIndex++;
		    	highIndex--;
		    }
		}
		
		return highIndex;
	}
	
	//Quick Sort Algorithm
	public static int [] Quicksort(int[] numbers, int lowIndex, int highIndex) {
		// Base case: If the partition size is 1 or zero 
		// elements, then the partition is already sorted
		if (lowIndex >= highIndex) {
			return numbers;
		}
		
		// Partition the data within the array. Value lowEndIndex 
		// returned from partitioning is the index of the low 
		// partition's last element.
		int lowEndIndex = Partition(numbers, lowIndex, highIndex);
		
		// Recursively sort low partition (lowIndex to lowEndIndex) 
		// and high partition (lowEndIndex + 1 to highIndex)
		Quicksort(numbers, lowIndex, lowEndIndex);
		Quicksort(numbers, lowEndIndex + 1, highIndex);
		
		return numbers;
	}
	
	//Used in Merge Sort Algorithm (See line 185)
	public static int [] Merge(int[] numbers, int i, int j, int k) {
		int mergedSize = k - i + 1;           		// Size of merged partition
		int mergePos = 0;                          	// Position to insert merged number
		int leftPos = 0;                           	// Position of elements in left partition
		int rightPos = 0;                          	// Position of elements in right partition
		int[] mergedNumbers = new int[mergedSize];  // Dynamically allocates temporary array
											  		// for merged numbers

		leftPos = i;                           		// Initialize left partition position
		rightPos = j + 1;                      		// Initialize right partition position
		
		// Add smallest element from left or right partition to merged numbers
		while (leftPos <= j && rightPos <= k) {
			if (numbers[leftPos] <= numbers[rightPos]) {
				mergedNumbers[mergePos] = numbers[leftPos];
				leftPos++;
			}
			else {
				mergedNumbers[mergePos] = numbers[rightPos];
				rightPos++;

			}
			mergePos++;
		}

		// If left partition is not empty, add remaining elements to merged numbers
		while (leftPos <= j) {
			mergedNumbers[mergePos] = numbers[leftPos];
			leftPos++;
			mergePos++;
		}

		// If right partition is not empty, add remaining elements to merged numbers
		while (rightPos <= k) {
			mergedNumbers[mergePos] = numbers[rightPos];
			rightPos++;
			mergePos++;
		}

		// Copy merge number back to numbers
		for (mergePos = 0; mergePos < mergedSize; ++mergePos)
			numbers[i + mergePos] = mergedNumbers[mergePos];
		   
		return numbers;
	}
	
	//Merge Sort Algorithm
	public static int [] MergeSort(int[] numbers, int i, int k) {
		int j = 0;

		if (i < k) {
			j = (i + k) / 2;  // Find the midpoint in the partition

			// Recursively sort left and right partitions
			MergeSort(numbers, i, j);
			MergeSort(numbers, j + 1, k);

			// Merge left and right partition in sorted order
			Merge(numbers, i, j, k);
		}
		
		return numbers;
	}
	
	public static int[] genData(int N) {
		int[] randomArray = new int[N];
		Random randIntGenerator = new Random();
		
		for(int i = 0; i < N; i++)
			randomArray[i] = randIntGenerator.nextInt(100);
		
		return randomArray;
	}
	
	public static long sortTime(int[] data, int choice) {
		int[] copy = data.clone();
		long startTime = 0;
		long endTime = 0;
		
		switch(choice) {
		case 0:
			startTime = System.currentTimeMillis();
			SelectionSort(copy);
			endTime = System.currentTimeMillis();
			break;
		case 1:
			startTime = System.currentTimeMillis();
			InsertionSort(copy);
			endTime = System.currentTimeMillis();
			break;
		case 2:
			startTime = System.currentTimeMillis();
			Quicksort(copy, 0, copy.length - 1);
			endTime = System.currentTimeMillis();
			break;
		case 3:
			startTime = System.currentTimeMillis();
			MergeSort(copy, 0, copy.length - 1);
			endTime = System.currentTimeMillis();
			break;
		default: 
			System.out.println("Invalid input for choice.");
			break;
		}
		return endTime - startTime;
	}
}
