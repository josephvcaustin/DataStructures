/* Joseph Austin
CS 4343 Data Structures and Algorithm Analysis
HW #1, Q3

This class finds prime numbers by checking if a number N is divisible by 
any numbers 2, 3, or (6i +/- 1) less than the square root of N.

First, to assert that this program finds prime numbers properly, the first 10,000
prime numbers are printed out.
This program correctly finds that the 10,000th prime number is 104,729.

Then, the running time for checking the primality of ALL NUMBERS within a 
certain range is determined. The last number found in that range is the largest 
prime in the range.

Following this, the running time for checking the primality of ONLY THE LARGEST
prime in that range is determined if checking all numbers in the range didn't fail.

After these computation times are calculated, the program looks at the computation
times of very large N. Instead of checking the primality of every number from 0 to N, it starts at
N and checks the primality of each number N-1, N-2, etc. Once a prime number is found (making it the
largest prime number less than N), the program finds the time it takes to determine that the number found is prime.
*/
public class PrimeTest
{
	public static long startTime = 0;
	public static long primeFound = 0; //Keep track of largest prime found in each run
	public static final long TOO_DAMN_LONG = 1200000000000L; //If a computation takes longer than 2 minutes (in nanoseconds), it took too long
	public static boolean failed = false;
	public static void main(String[] args)
	{
		//First, print 10,000 prime numbers to assert that this program works.
		//This can be compared to a prime number list found online.
		//For example: https://primes.utm.edu/lists/small/10000.txt
		
		//Uncomment the following lines to print out the first 10,000 primes
		//computed by this program.
		
		/* 
		System.out.println("-------------- THE FIRST 10,000 PRIME NUMBERS --------------");
		System.out.println();
		printPrimes(10000);
		System.out.println("------------------------------------------------------------");
		System.out.println(); 
		*/
		
		long compTime = 0; //Computation time to find all primes in a certain range
		
		//We need to know the largest primes for each range because
		//finding a prime number is always the worst case scenario for
		//the primality testing algorithm.
		
		//While we're looking at this, we can observe the run time for computing
		//the primality of every single number in a certain range (just out of curiousity).
		startTime = System.nanoTime();
		findPrimes(0, 1000);
		System.out.println("Largest prime below 1,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound); //findPrimes() sets "failed" to true if the computation takes too long
		System.out.println();
		
		startTime = System.nanoTime();
		findPrimes(0, 10000);
		System.out.println("Largest prime below 10,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound);
		System.out.println();
		
		startTime = System.nanoTime();
		findPrimes(0, 100000);
		System.out.println("Largest prime below 100,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound);
		System.out.println();
		
		startTime = System.nanoTime();
		findPrimes(0, 1000000);
		System.out.println("Largest prime below 1,000,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound);
		System.out.println();
		
		startTime = System.nanoTime();
		findPrimes(0, 10000000);
		System.out.println("Largest prime below 10,000,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound);
		System.out.println();
		
		startTime = System.nanoTime();
		findPrimes(0, 100000000);
		System.out.println("Largest prime below 100,000,000 = " + primeFound + ".");
		if(!failed) primeTime(primeFound);
		System.out.println();
		
		//Now let's take a look at computation time for very large primes.
		//Instead of looking at all numbers in a range, we'll start at the top and
		//work backward to find the largest prime in the range (because computing the 
		//primality of every number in the range would take way too long).
		long test = 1000000000;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 1 billion is " + test + ".");
		//Print how long it takes to determine that this number is prime.
		primeTime(test);
		System.out.println();
		
		test = 10000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 10 billion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 100000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 100 billion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 1000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 1 trillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 10000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 10 trillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 100000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 100 trillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 1000000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 1 quadrillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 10000000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 10 quadrillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 100000000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 100 quadrillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		test = 1000000000000000000L;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number below 1 quintillion is " + test + ".");
		primeTime(test);
		System.out.println();
		
		//Just for grins and giggles, let's see how long it takes to compute the largest 
		//prime number Java can handle.
		
		final long LONGEST_LONG = 9223372036854775807L;
		test = LONGEST_LONG;
		while(!isPrime(test))
		{
			test--;
		}
		System.out.println("The largest prime number in Java is " + test + ".");
		primeTime(test);
		//It only takes about six seconds on an i7 4790k at 4.00GHz.
		
	}
	public static boolean isPrime(long n)
	{
		//Prime numbers are defined for integers greater than 1. 
		if (n == 2) return true;
		if (n == 3) return true;
		if (n%2 == 0) return false;
		if (n%3 == 0) return false;
		
		//Primes are always (6i +/- 1), starting at i = 1.
		//We only have to check numbers less than sqrt(n). After that, extra work is being done.
		//(information gathered from Wikipedia's Primality Testing article)
		//https://en.wikipedia.org/wiki/Primality_test
		for(long i = 6; (i-1) <= (long)Math.sqrt(n); i+=6) 
		{
			if (n%(i-1) == 0) return false;
			if (n%(i+1) == 0) return false;
		}
		return true;
	}
	public static void printPrimes(int howMany) //Prints the first "howMany" prime numbers.
	{
		long test = 0;
		int primes = 0;
		int printsPerLine = 20;
		while(primes <= howMany)
		{
			if(isPrime(test))
			{
				System.out.print(test + ", ");
				primes++;
				if(primes%printsPerLine == 0) System.out.println();
			}
			test++;
		}
	}
	public static void findPrimes(long start, long end) //Finds all prime numbers in a certain range.
	{
		failed = false;
		long test = start;
		int primes = 0;
		primeFound = 0;
		while(test <= end)
		{
			if(isPrime(test))
			{
				//System.out.println(test); //Print the prime number found
				primeFound = test;
				primes++;
			}
			test++;
			if((System.nanoTime() - startTime) > TOO_DAMN_LONG) //If this computation is taking too long, quit.
			{
				System.out.println("Primality evaluation got to " + test + " evaluations out of " + end + " evaluations before it took too long.");
				failed = true;
				return;
			}				
		}
		long compTime = System.nanoTime() - startTime;
		System.out.println("Found " + primes + " prime numbers between " + start + " and " + end + ".");
		System.out.println("Computation with " + (end-start) + " evaluations took " + ((double)compTime/1000000000) + " seconds.");
	}
	public static void primeTime(long toProve)
	{
		//This method prints the time it takes to determine that
		//the largest prime number found within a certain range is
		//in fact prime.
		startTime = System.nanoTime();
		if (isPrime(toProve))
		{
			long compTime = System.nanoTime()-startTime;
			System.out.println("Determining that " + toProve + " is prime takes "  + ((double)compTime/1000000000) + " seconds.");
		}
		else
		{
			System.out.println("For some reason, " + toProve + " was found to be not prime.");
		}
	}
}