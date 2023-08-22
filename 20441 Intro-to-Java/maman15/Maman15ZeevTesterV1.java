public class Maman15ZeevTesterV1{

    public static void main(String[] args ){
        System.out.println("**LAST UPDATE 26-05-21**\n");
        System.out.println("************Yalla lets check your Maman 15************\n");

        System.out.println("*Version1 notes: constructor and addToData tests a very basic");
        System.out.println("if you have a problem with toString it might be because");
        System.out.println("there are actual problems with constructors and other methods!!!!!!!!!!!!!!!!");


        System.out.println("** constructors **"); // only checks exceptions
        constructorTextListTest();

        System.out.println("** addToData **"); // only checks exceptions
        addToDataTest();

        System.out.println("** toString **");
        toStringTest();


        System.out.println("** howManyWords **");
        howManyWordsTest();


        System.out.println("** howManyDifferentWords **");
        howManyDifferentWordsTest();


        System.out.println("** mostFrequentWord **");
        mostFrequentWordTest();

        System.out.println("** howManyStarting **");
        howManyStartingTest();

        System.out.println("** mostFrequentStartingLetter **");
        mostFrequentStartingLetterTest();

        System.out.println("************Mabruk! Maman 15 is Fine************\n");

    }

    public static void constructorTextListTest(){
        //default
        try {
            TextList a = new TextList();
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList()");
        }
        //empty
        try {
            TextList a = new TextList("");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"\")");
        }

        //space
        try {
            TextList a = new TextList(" ");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\" \")");
        }

        //single character
        try {
            TextList a = new TextList("a");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"a\")");
        }
        //single word
        try {
            TextList a = new TextList("hakuna");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"hakuna\")");
        }
        //2 words
        try {
            TextList a = new TextList("hakuna matata");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"hakuna matata\")");
        }
        //3 words some same
        try {
            TextList a = new TextList("hakuna matata matata");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"hakuna matata matata\")");
        }
        //4 words some same
        try {
            TextList a = new TextList("hakuna matata hakuna matata");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"hakuna matata hakuna matata\")");
        }

        //long sentence
        try {
            TextList a = new TextList("scar is the true king all hail scar");
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - Cannot initialize TextList(\"scar is the true king all hail scar\")");
        }
    }

    public static void addToDataTest() {
        TextList myList;
        String word,s;
        try {
            myList = new TextList();
            word = "a";
            myList.addToData(word);
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in addToData");
            System.out.println("\ttried to add a word to empty initialized list");
        }

        try {
            myList = new TextList("");
            word = "";
            myList.addToData(word);
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in addToData");
            System.out.println("\ttried to add a empty string to empty initialized list");
        }

        try {
            myList = new TextList("hakuna");
            word = "";
            myList.addToData(word);
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in addToData");
            System.out.println("\ttried to add a empty string to initialized list");
        }

        addToDataTest("", "hakuna");//new to empty
        addToDataTest("matata", "hakuna");//add after word
        addToDataTest("hakuna", "matata");//add before word
        addToDataTest("hakuna matata", "hakuna");//more of the same
        addToDataTest("hakuna matata", "matata");//more of the same begining
        addToDataTest("hakuna hakuna matata", "hakuna");//more of the same begining
        addToDataTest("hakuna hakuna simba", "hakuna");
        addToDataTest("hakuna hakuna matata", "simba"); //add in the middle
        addToDataTest("simba killed mufasa", "hakuna");
        addToDataTest("simba killed mufasa", "rafiki");
        addToDataTest("all hail scar scar scar all hail scar", "hail");
        addToDataTest("bll hail scar scar scar bll hail scar", "all"); // add first

    }//end to Data test

    public static void addToDataTest(String input, String word) {
        TextList myList = new TextList(input);
        try {
            myList.addToData(word);
            System.out.println("\tOK!");
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in addToData");
            System.out.println("\ttried to add the word \"" + word + "\"");
            System.out.println("\tto initialized list with \"" + input + "\"");
        }
    } //toData helper

    public static void toStringTest() {
        toStringTest("", "");//empty
        toStringTest("hakuna", "hakuna\t1\n");//one word
        toStringTest("hakuna matata", "hakuna\t1\nmatata\t1\n");//2 different
        toStringTest("hakuna matata hakuna", "hakuna\t2\nmatata\t1\n"); //2 same
        toStringTest("hakuna matata matata", "hakuna\t1\nmatata\t2\n");
        toStringTest("a b", "a\t1\nb\t1\n");
        toStringTest("aa bb", "aa\t1\nbb\t1\n");
        toStringTest("aa bb c", "aa\t1\nbb\t1\nc\t1\n");
        toStringTest("cc bb a", "a\t1\nbb\t1\ncc\t1\n");
        toStringTest("cc cc bb bb a a", "a\t2\nbb\t2\ncc\t2\n");
        toStringTest("cc bb cc bb bb a a", "a\t2\nbb\t3\ncc\t2\n");
        toStringTest("ha ku na ma ta ta", "ha\t1\nku\t1\nma\t1\nna\t1\nta\t2\n");
    }//end of toStringTest

    public static void toStringTest(String input, String check) {
        try {
            TextList myList = new TextList(input);
            String s = myList.toString();
            if (s.equals(check))
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in toString");
                System.out.println("\tinput is  \n" + input);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + s);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in toString");
            System.out.println("\tinput wat \"" + input + "\"");

            }
        } //toStringTest helper

    public static void howManyWordsTest() {
        howManyWordsTest("", 0);
        howManyWordsTest("hakuna", 1);
        howManyWordsTest("hakuna matata", 2);
        howManyWordsTest("hakuna matata hakuna matata", 4);
        howManyWordsTest("anything you can do i can do better", 8);
        howManyWordsTest("scar is the true king of the vally", 8);
        howManyWordsTest("a ab abc abcd abcde a ab abc z", 9);

    }//end of howManyWordsTest

    public static void howManyWordsTest(String input, int check) {
        try {
            TextList myList = new TextList(input);
            int howMany = myList.howManyWords();
            if (howMany == check)
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in howManyWords()");
                System.out.println("\tinput is  \n" + input);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + howMany);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in howManyWords");
            System.out.println("\tinput was \"" + input + "\"");

        }
    } //howManyWordsTest helper

    public static void howManyDifferentWordsTest() {
        howManyDifferentWordsTest("", 0);
        howManyDifferentWordsTest("hakuna", 1);
        howManyDifferentWordsTest("hakuna matata", 2);
        howManyDifferentWordsTest("hakuna matata hakuna matata", 2);
        howManyDifferentWordsTest("anything you can do i can do better", 6);
        howManyDifferentWordsTest("scar is the true true king of the vally all hail scar", 9);
        howManyDifferentWordsTest("a ab abc abcd abcde a ab abc z", 6);

    }//end of howManyWordsTest

    public static void howManyDifferentWordsTest(String input, int check) {
        try {
            TextList myList = new TextList(input);
            int howMany = myList.howManyDifferentWords();
            if (howMany == check)
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in howManyDifferentWords()");
                System.out.println("\tinput is  \n" + input);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + howMany);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in howManyDifferentWords");
            System.out.println("\tinput was \"" + input + "\"");

        }
    } //howManyWordsTest helper

    public static void mostFrequentWordTest() {
        mostFrequentWordTest("", "");
        mostFrequentWordTest("hakuna", "hakuna");
        mostFrequentWordTest("hakuna matata", "hakuna");
        mostFrequentWordTest("matata hakuna", "hakuna");
        mostFrequentWordTest("hakuna matata hakuna matata", "hakuna");
        mostFrequentWordTest("anything you can do i can do better", "can");
        mostFrequentWordTest("scar is the only true king of the vally of hope", "of");
        mostFrequentWordTest("scar is the only true king of the true vally of hope", "of");
        mostFrequentWordTest("all hail scar all hail the true king", "all");
        mostFrequentWordTest("all king hail king scar king all hail the true king", "king");
        mostFrequentWordTest("aaa aaa aab aac aa bb cc a a", "a");

    }//end of mostFrequentWordTest

    public static void mostFrequentWordTest(String input, String check) {
        try {
            TextList myList = new TextList(input);
            String s = myList.mostFrequentWord();
            if (s.equals(check))
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in mostFrequentWord()");
                System.out.println("\tinput is  \n" + input);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + s);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in mostFrequentWord");
            System.out.println("\tinput wat \"" + input + "\"");
        }
    } //mostFrequentWordTest helper

    public static void howManyStartingTest() {
        howManyStartingTest("", 'c',0);
        howManyStartingTest("hakuna",'c' ,0);
        howManyStartingTest("hakuna",'h' ,1);
        howManyStartingTest("hakuna hakuna",'h' ,2);
        howManyStartingTest("hakuna matata",'h' ,1);
        howManyStartingTest("hakuna h a k u n a hak",'h' ,3);
        howManyStartingTest("anything you can do i can do better",'c' ,2);
        howManyStartingTest("scar is the only true king of the vally of hope",'t' ,3);
        howManyStartingTest("scar is the only true king simba just killed mufasa stam stam",'s' ,4);
        howManyStartingTest("mufasa was a tyrant", 'c',0);
        howManyStartingTest("mufasa was a tyrant", 'm',1);
        howManyStartingTest("mufasa was a tyrant", 't',1);
        howManyStartingTest("scar is the true king and the true heir to the throne", 't',7);
        howManyStartingTest("a a a aa aa aa ab ab ab abc abc abc abcd b c d e f g h i ", 'a',13);
        howManyStartingTest("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 'b',1);

    }//end of howManyStartingTest

    public static void howManyStartingTest(String input, char ch, int check) {
        try {
            char c = ch;
            TextList myList = new TextList(input);
            int starting = myList.howManyStarting(c);
            if (starting == check)
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in howManyStarting()");
                System.out.println("\tinput is  \n" + input + " char: " + c);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + starting);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in howManyStarting");
            System.out.println("\tinput wat \"" + input + "\"");
        }
    } //howManyStartingTest helper

    public static void mostFrequentStartingLetterTest() {

        mostFrequentStartingLetterTest("", ' ');
        mostFrequentStartingLetterTest("b", 'b');
        mostFrequentStartingLetterTest("ba", 'b');
        mostFrequentStartingLetterTest("abb", 'a');
        mostFrequentStartingLetterTest("b a a", 'a');
        mostFrequentStartingLetterTest("b c c d d d", 'd');
        mostFrequentStartingLetterTest("bad common cold dir das ddabadu", 'd');
        mostFrequentStartingLetterTest("b c c d d d b b b b", 'b');
        mostFrequentStartingLetterTest("b c c d d d c c c c d a d t g d f d", 'd');
        mostFrequentStartingLetterTest("anything you can do i can do better", 'c');
        mostFrequentStartingLetterTest("scar should be the true king of the vally", 't');
        mostFrequentStartingLetterTest("timon pumba timon pumba are banal beasts", 'b');
        for (int i = 'a' ; i < 'z' - 1 ; i++)
            mostFrequentStartingLetterTest((char) i + "" + (char) i  + " " + (char) (i+1) + "" + (char) (i+1), (char) i);

    }//end of howManyStartingTest

    public static void mostFrequentStartingLetterTest(String input, char check) {
        try {
            TextList myList = new TextList(input);
            char freqChar = myList.mostFrequentStartingLetter();
            if (freqChar == check)
                System.out.println("\tOK!");
            else
            {
                System.out.println("\tError - in mostFrequentStartingLetter()");
                System.out.println("\tinput is  \n" + input);
                System.out.println("\toutput should be  \n" + check);
                System.out.println("\tactual  \n" + freqChar);
            }
        } catch (Exception e) {
            System.out.println("\tError - **Exception** - in mostFrequentStartingLetter");
            System.out.println("\tinput wat \"" + input + "\"");
        }
    } //howManyStartingTest helper

    public static void template() {

        try {


        } catch (Exception e) {
            System.out.println("\tError - Exception - Q1 does not run");
        }

    }//end of template

}//end of Maman15ZeevTesterV1


