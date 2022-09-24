/**
 * Assignment 15 - Using a class to represent a list of words
 * 
 * @author Itay Schechner
 * @version 06-06-2021
 */
public class TextList {

    // attributes
    public static final char SPACE = ' ';
    private WordNode _head;

    // constuctors
    /**
     * Default constructor. generates an empty list with O(1) time and memoty
     * complexity.
     */
    public TextList() {
        _head = null;
    }

    /**
     * Creates a new TextList object from a given text with O(n) time complexity and
     * O(1) memory complexity.
     * 
     * @param text the text to append to the list
     */
    public TextList(String text) {
        this();
        int beforeIndex = 0, afterIndex = 0;
        while (afterIndex < text.length()) {

            if (text.charAt(afterIndex) == SPACE) { // if the current word has ended
                String newWord = text.substring(beforeIndex, afterIndex); // get the word
                addToData(newWord);

                beforeIndex = afterIndex + 1; // set the start of the new word
            }

            afterIndex++;
        }

        String finalWord = text.substring(beforeIndex, afterIndex);
        addToData(finalWord);
    }

    /**
     * Add a word to the list in the right position in O(n) time complexity (wrost
     * case: new word comes last) and O(1) memory complexity.
     * 
     * @param word the word to add
     */
    public void addToData(String word) {
        if (word.equals(""))
            return;

        if (_head == null)
            _head = new WordNode(word);

        else if (_head.getWord().compareTo(word) > 0) {
            // head comes after the added word
            _head = new WordNode(word, _head);
        }

        else { // head is before the added word

            WordNode current = _head;
            while (current.getNext() != null && current.getNext().getWord().compareTo(word) <= 0) {
                // while next word is before the added word
                current = current.getNext();
            }

            // next word is after the current word
            WordNode newNode = new WordNode(word, current.getNext());
            current.setNext(newNode);
        }
    }

    /**
     * Counts words are in the list with O(n) time complexity and O(1) memory
     * complexity.
     * 
     * @return the number of words in the list
     */
    public int howManyWords() {
        int count = 0;
        WordNode current = _head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    /**
     * Counts unique words in the list in O(n) time complexity and O(1) memory
     * complexity.
     * 
     * @return the number of unique qords in the list
     */
    public int howManyDifferentWords() {
        int count = 0;
        WordNode current = _head;
        while (current != null) {
            if (current.getNext() == null || !current.getWord().equals(current.getNext().getWord()))
                // if next word does not equal to this one
                count++;
            current = current.getNext();
        }
        return count;
    }

    /**
     * Searches for the most frequent word of the list in O(n) time complexity and
     * O(1) memory complexity.
     * 
     * @return the most frequent word
     */
    public String mostFrequentWord() {
        int currentWordCount = 0, frequentWordCount = 0;
        String word = null;
        WordNode current = _head;
        while (current != null) {
            currentWordCount++;

            if (currentWordCount > frequentWordCount) {
                // if the current word is the most frequent one
                word = current.getWord();
                frequentWordCount = currentWordCount;
            }

            if (current.getNext() == null || !current.getNext().getWord().equals(current.getWord()))
                // if the next word does not equal to this word
                currentWordCount = 0;

            current = current.getNext();
        }
        return word;
    }

    /**
     * Check how many words start with a given letter in O(n) time complexity and
     * O(1) memory complexity.
     * 
     * @param letter the letter to check
     * @return the number of words starting with the letter.
     */
    public int howManyStarting(char letter) {
        int count = 0;
        WordNode current = _head;

        while (current != null) {
            if (current.getWord().charAt(0) == letter)
                count++;
            current = current.getNext();
        }
        return count;

    }

    /**
     * Searches for the most frequent starting letter
     * 
     * @return the most freqent starting letter as a char
     */
    public char mostFrequentStartingLetter() {
        return mostFrequentStartingLetter(_head, 1, SPACE, 0);
    }

    private char mostFrequentStartingLetter(WordNode current, int currentLetterCount, char mostFrequent,
            int frequentCharCount) {

        if (current == null)
            return mostFrequent;

        char currentLetter = current.getWord().charAt(0);
        if (current.getNext() != null && current.getNext().getWord().charAt(0) == currentLetter)
            // if next word starts with the same letter
            return mostFrequentStartingLetter(current.getNext(), currentLetterCount + 1, mostFrequent,
                    frequentCharCount);

        if (currentLetterCount > frequentCharCount)
            /*
             * if current letter is the most frequent AND next letter starts with a
             * different letter
             */
            return mostFrequentStartingLetter(current.getNext(), 1, currentLetter, currentLetterCount);

        /*
         * next word starts with a different letter, and the current letter is not the
         * most frequent
         */
        return mostFrequentStartingLetter(current.getNext(), 1, mostFrequent, frequentCharCount);

    }

    /**
     * Formats the list to a string with O(n) time complexity and O(1) memory
     * complexity.
     * 
     * @return the list of words, and by each word the number of times it's shown.
     */
    public String toString() {
        String result = "", row;
        int currentWordCount = 0;
        WordNode current = _head;

        while (current != null) {
            currentWordCount++;

            if (current.getNext() == null || !current.getNext().getWord().equals(current.getWord())) {
                // if the next word does not equal to this word
                row = current.getWord() + "\t" + currentWordCount + "\n";

                result += row;
                currentWordCount = 0;
            }

            current = current.getNext();

        }

        return result;
    }

}
