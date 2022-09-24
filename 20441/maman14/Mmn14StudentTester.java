
public class Mmn14StudentTester {

	public static void main(String[] args) {
		System.out.println("************ test Q1 - maximalDrop START *************");
		int[] a1 = { 5, 21, 3, 22, 12, 7, 27, 6, 4 };// max drop = 23
		int[] a2 = { 5, 21, 3, 22, 12, 7, 26, 14 }; 	// max drop = 18
		int[] a3 = { 5, 15, 3, 22, 12, 7, 27, 14 }; 	// max drop = 15

		int res = Ex14.maximalDrop(a1);
		System.out.println("\nrunning maximalDrop(a1); expected 23, got " + res);

		res = Ex14.maximalDrop(a2);
		System.out.println("\nrunning maximalDrop(a2); expected 18, got " + res);

		res = Ex14.maximalDrop(a3);
		System.out.println("\nrunning maximalDrop(a3); expected 15, got " + res);

		System.out.println("\n************ test Q1 - maximalDrop END *************\n");

		System.out.println("************ test Q2 - isSink START *************");
		int[][] m1 = { // sink=1
				{ 0, 1 }, 
				{ 0, 0 } };

		int[][] m2 = { // sink=2
				{ 0, 1, 1 }, 
				{ 0, 1, 1 }, 
				{ 0, 0, 0 } };

		int[][] m3 = { // sink=2
				{ 0, 1, 1, 0 }, 
				{ 0, 1, 1, 1 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 0 } };

		int[][] m4 = { // sink=-1
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 1 }, 
				{ 0, 0, 0, 0 } };

		int[][] m5 = { // sink=1
				{ 0, 1, 1, 1 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 1 }, 
				{ 0, 1, 1, 0 } };

		res = Ex14.isSink(m1);
		System.out.println("\nrunning isSink(m1); expected 1,  got " + res);

		res = Ex14.isSink(m2);
		System.out.println("\nrunning isSink(m2); expected 2,  got " + res);
 
		res = Ex14.isSink(m3);
		System.out.println("\nrunning isSink(m3); expected 2,  got " + res);

		res = Ex14.isSink(m4);
		System.out.println("\nrunning isSink(m4); expected -1, got " + res);

		res = Ex14.isSink(m5);
		System.out.println("\nrunning isSink(m5); expected 1,  got " + res);

		System.out.println("\n************ test Q2 - isSink END *************");

		
		boolean [][]a = {
				{true, 	false, 	false, 	true},
				{false, 	true, 	false, 	false},
				{false, 	true, 	false, 	true},
				{false, 	false, 	false, 	true},
				{true, 	true, 	true, 	false}
		};
		System.out.println("************ test Q3 - size START *************");
		
		res = Ex14.size(a, 0, 0);
		System.out.println("\nrunning size(a, 0, 0); expected 3, got " + res);

		res = Ex14.size(a, 1, 1);
		System.out.println("\nrunning size(a, 1, 1); expected 3, got " + res);

		res = Ex14.size(a, 2, 2);
		System.out.println("\nrunning size(a, 2, 2); expected 0, got " + res);

		res = Ex14.size(a, 4, 0);
		System.out.println("\nrunning size(a, 4, 0); expected 5, got " + res);

		System.out.println("\n************ test Q3 - size END *************");


		System.out.println("\n************ test Q4 - isPermutation START *************");
		int [] c1 = {1, 2, 3, 4};
		int [] b1 = {1, 2, 3, 4};
		boolean ans = Ex14.isPermutation(c1, b1);
		System.out.println("\nrunning isPermutation{c1, b1}; expected true, 	got " + ans);

		int [] c2 = {1, 2, 3, 4};
		int [] b2 = {1, 2, 3, 2};
		ans = Ex14.isPermutation(c2, b2);
		System.out.println("\nrunning isPermutation{c2, b2}; expected false,	got " + ans);

		int [] c3 = {1, 2, 3, 4};
		int [] b3 = {4, 2, 1, 3};
		ans = Ex14.isPermutation(c3, b3);
		System.out.println("\nrunning isPermutation{c3, b3}; expected true, 	got " + ans);

		int [] c4 = {1, 2, 2, 4};
		int [] b4 = {4, 2, 1, 2};
		ans = Ex14.isPermutation(c4, b4);
		System.out.println("\nrunning isPermutation{c4, b4}; expected true, 	got " + ans);

		int [] c5 = {1, 2, 2, 4, 5};
		int [] b5 = {4, 5, 1, 2};
		ans = Ex14.isPermutation(c5, b5);
		System.out.println("\nrunning isPermutation{c5, b5}; expected false,	got " + ans);

		
		System.out.println("\n************ test Q4 - isPermutation END *************");

	}

}
