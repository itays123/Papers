
/**
 * Tester for Maman15
 * @author Nissim brami
 */

public class Maman15StudentTester{

	public static void main(String args[]) {
		
		System.out.println("************** MAMAN 15 TESTER ********************");
		
		// Create the first list, with empty constructor
		TextList list0 = new TextList();
		
		// Check the method - addToData 
		System.out.println(list0);  
		list0.addToData("hello");
		System.out.println("list0 after adding the word hello: \n"+list0); 
		list0.addToData("hello");
		System.out.println("list0 after adding the word hello twice: \n"+list0); 
		
		// Create the second list, with the sentence below
		String text1 = "hello world this is my first text";
		TextList list1 = new TextList(text1);
		System.out.println("list1 after adding the text: hello world this is my first text:");
		System.out.println("expected: \n");
		System.out.println("first\t1");
		System.out.println("hello\t1");
		System.out.println("is\t1");
		System.out.println("my\t1");
		System.out.println("text\t1");
		System.out.println("this\t1");
		System.out.println("world\t1");
		
		System.out.println("\nResult: \n");
		System.out.println(list1+"\n");        
		
		// Check the method - howManyWords 
		System.out.println( "Amount of words in list1: "+list1.howManyWords());
		// Check the method - howManyDifferentWords 
		System.out.println( "Amount of different words in list1: "+list1.howManyDifferentWords());
		// Check the method - mostFrequentWord 
		System.out.println( "Most frequent word in list1: "+list1.mostFrequentWord());
		// Check the method - mostFrequentStartingLetter 
		System.out.println("Most frequent starting letter in list1: "+list1.mostFrequentStartingLetter());
		// Check the method - howManyStarting 
		System.out.println( "Number of words starting with letter 'm' in list1: "+list1.howManyStarting('m'));
		System.out.println();
		list1.addToData("ok");
		System.out.println("list1 after adding the word ok :\n"+list1);        

	}       

}