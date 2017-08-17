/*
Joseph Austin
Assignment 5 - Quicksort
CS 4343

This program is an implementation of the Quicksort
algorithm that allows the pivot choice to be any
element in the array or subarray.

*/

import java.util.*;
import java.io.*;

public class Quicksort
{
	public static Scanner scan = new Scanner(System.in); //Read from file
	public static ArrayList<Long> data = new ArrayList<>(); //Base set of data points
	public static ArrayList<Long> toSort = new ArrayList<>(); //The array to sort
	public static int comparisons = 0; //number of comparisons for this sort
	public static int swaps = 0; //number of swaps for this sort
	public static int calls = 0; //number of recursion calls for this sort
	public static final int FIRST = 0; //Pivot as the first element in the array
	public static final int LAST = 1; //Pivot as the last element in the array
	public static final int RANDOM = 2; //Pivot as a random element in the array
	public static final int MEDIAN = 3; //Pivot index as the median of three random numbers
	public static void main(String[] args)
	{
		if (args.length != 1) 
		{
			System.out.println("usage: java Quicksort <inputfile.txt>");
			System.exit(0);
		}
		try {scan = new Scanner(new File(args[0]));}
		catch(FileNotFoundException e) 
		{
			System.out.println("The system could not find the file \"" + args[0] + "\".");
			System.exit(0);
		}
		while(scan.hasNextLong()) {data.add(new Long(scan.nextLong()));}
		scan = new Scanner(System.in);

		toSort.addAll(data);
		System.out.println("Input size n = " + toSort.size());
		
		//Quicksort always using the first element as the pivot
		toSort.clear();
		toSort.addAll(data);
		long start = System.nanoTime();
		quicksort(FIRST, 0, toSort.size()-1);
		long end = System.nanoTime();
		
		//Print the array to prove the algorithm works
		System.out.print("Print the sorted array? y/n ");
		String response = scan.nextLine();
		if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') printSorted(); 
		
		System.out.println("Quicksorting using the first element as a pivot.");
		System.out.println("First element pivot on input size " + toSort.size() + " needed " + comparisons + " comparisons, " + swaps + " swaps, and " + calls + " recursion calls.");
		System.out.println("The sort took " + ((double)(end-start))/1000000 + " milliseconds.");
		System.out.println();
		
		//Quicksort using a random element as the pivot
		comparisons = 0;
		swaps = 0;
		calls = 0;
		toSort.clear();
		toSort.addAll(data);
		start = System.nanoTime();
		quicksort(RANDOM, 0, toSort.size()-1);
		end = System.nanoTime();
		System.out.println("Quicksorting using a random element as a pivot.");
		System.out.println("Random element pivot on input size " + toSort.size() + " needed " + comparisons + " comparisons, " + swaps + " swaps, and " + calls + " recursion calls.");
		System.out.println("The sort took " + ((double)(end-start))/1000000 + " milliseconds.");
		System.out.println();
		
		//Quicksort using the median of three random numbers as the pivot index
		comparisons = 0;
		swaps = 0;
		calls = 0;
		toSort.clear();
		toSort.addAll(data);
		start = System.nanoTime();
		quicksort(MEDIAN, 0, toSort.size()-1);
		end = System.nanoTime();
		System.out.println("Quicksorting using the median of three random numbers as the pivot index.");
		System.out.println("Median random element pivot on input size " + toSort.size() + " needed " + comparisons + " comparisons, " + swaps + " swaps, and " + calls + " recursion calls.");
		System.out.println("The sort took " + ((double)(end-start))/1000000 + " milliseconds.");
		System.out.println();
	}
	
	//This implementation of quicksort uses two iterating variables.
	//i starts at the left of the pivot and tries to find elements that are greater than the pivot that need to move up.
	//j starts at the right of the pivot and tries to find elements that need to move down.
	//if i finds an element that needs to move up or hits the pivot, it waits for j to find an element that needs to move down.
	//(vice versa - if j find an element that needs to move down it waits for i)
	//wherever i and j stop, a correct swap will occur.
	//Either the pivot will be swapped, or two elements will be exchanged to the correct sides of the pivot.
	//Once every element has been inspected by either i or j, the pivot will have all elements less than it to the left
	//and all elements greater than it to the right. 
	//A recursive call is then made to sort the subarray from low to pivot-1, and also the subarray from pivot+1 to high. 
	public static void quicksort(int METHOD, int low, int high)
	{
		if(high-low <= 0) return;
		calls++;
		int i = low, j = high; //i starts at the bottom and moves up, j starts at the top and moves back
		int pivotIndex = low; //make sure pivot index is assigned
		switch (METHOD) //check how we're choosing the pivot
		{
			case FIRST: pivotIndex = low;
						break;
			case LAST:  pivotIndex = high;
						break;	
			case RANDOM: pivotIndex = randomRange(low, high);
						 break;	
			case MEDIAN: pivotIndex = randomMedian(low, high);
						 break;			 
		}
		while (i != j) //while i and j haven't met up
		{
			while(i != pivotIndex) //while i is not at the pivot index yet
			{
				comparisons++;
				if(toSort.get(i) > toSort.get(pivotIndex)) break; //if this element needs to move to the right of the pivot, break
				i++; //otherwise keep looking
			} //i is looking at an element that needs to swap now (either the pivot or an element to swap)
			while(j != pivotIndex) //while j i snot at the pivot index yet
			{
				comparisons++;
				if(toSort.get(j) < toSort.get(pivotIndex)) break; //if this element needs to move to the left of the pivot, break
				j--; //otherwise keep looking
			}
			if (i != j) //if i and j are not looking at the same element
			{
				swap(i, j); //then at this point, a swap needs to occur
				if(i == pivotIndex) //if i was at the pivot index, the pivot got swapped to where j is.
				{
					pivotIndex = j;
					i++;
				}
				else if(j == pivotIndex) //if j was at the pivot index, the pivot got swapped to where i is.
				{
					pivotIndex = i;
					j--;
				}
				else //otherwise, after the swap is done, keep looking.
				{
					i++;
					j--;
				}
			}
		}
		quicksort(METHOD, low, pivotIndex-1);
		quicksort(METHOD, pivotIndex+1, high);
	}
	//This method is a verbose version of quicksort for hand-tracing and debugging.
	public static void quicktest(int METHOD, int low, int high)
	{
		if(high-low <= 0) return;
		calls++;
		int pivotIndex = low;
		switch (METHOD)
		{
			case LAST:  pivotIndex = high;
						break;	

			case RANDOM: pivotIndex = randomRange(low, high);
						 break;	

			case MEDIAN: pivotIndex = randomMedian(low, high);
						 break;			 
		}
		System.out.println("--------------------------------------------");
		System.out.println("Method call, pivotIndex = " + pivotIndex + ", low = " + low + ", high = "+ high);
		printSorted(low, high);
		System.out.println("--------------------------------------------");
		int i = low, j = high;
		System.out.println("The pivot is value [" + toSort.get(pivotIndex) + "] at position " + pivotIndex);
		while (i != j)
		{
			System.out.println();
			while(i != pivotIndex) //don't compare the pivot to itself
			{
				comparisons++;
				System.out.println("i is looking at value [" + toSort.get(i) + "] at index i = " + i);
				if(toSort.get(i) > toSort.get(pivotIndex))
				{
					System.out.println("i found value [" + toSort.get(i) + "] at index " + i + " that needs to move higher");
					break;
				}
				i++;
			}
			while(j != pivotIndex)
			{
				comparisons++;
				System.out.println("j is looking at value [" + toSort.get(j) + "] at index j = " + j);
				if(toSort.get(j) < toSort.get(pivotIndex))
				{
					System.out.println("j found value [" + toSort.get(j) + "] at index " + j + " that needs to move lower");
					break;
				}
				j--;
			}
			if (i != j)
			{
				swap(i, j); 
				System.out.println("Swapping value [" + toSort.get(i) + "] at position i = " + i + " with value [" + toSort.get(j) + "] at j = " + j);
				if(i == pivotIndex) 
				{
					pivotIndex = j;
					i++;
					System.out.println("The pivot ["+ toSort.get(pivotIndex) +"] is now located at j = " + j);
				}
				else if(j == pivotIndex) 
				{
					pivotIndex = i;
					j--;
					System.out.println("The pivot ["+ toSort.get(pivotIndex) +"] is now located at i = " + i);
				}
				else
				{
					i++;
					j--;
				}
			}
			else System.out.println("i is equal to j at position " + i);
			printSorted(low, high);
			System.out.println("Comparisons: " + comparisons);
		}
		quicktest(METHOD, low, pivotIndex-1);
		quicktest(METHOD, pivotIndex+1, high);
	}
	public static void printSorted()
	{
		System.out.print("[");
		for(int i = 0; i < toSort.size(); i++) 
		{
			System.out.print(toSort.get(i));
			if(i != toSort.size()-1) System.out.print(", ");
		}
		System.out.print("] \n");
	}
	public static void printSorted(int low, int high)
	{
		System.out.print("[");
		for(int i = low; i <= high; i++) 
		{
			System.out.print(toSort.get(i));
			if(i != high) System.out.print(", ");
		}
		System.out.print("] \n");
	}
	public static int median(int[] values)
	{
		Arrays.sort(values);
		return values[(values.length-1)/2];
	}
	public static void swap(int i, int j)
	{
		long temp = toSort.get(i);
		toSort.set(i, toSort.get(j));
		toSort.set(j, temp);
		swaps++;
	}
	public static int randomRange(int min, int max)
	{
		Random r = new Random(System.nanoTime());
		return r.nextInt((max - min) + 1) + min;
	}
	public static int randomMedian(int min, int max)
	{
		int[] a = new int[3];
		a[0] = randomRange(min, max);
		a[1] = randomRange(min, max);
		a[2] = randomRange(min, max);
		return median(a);
	}
}