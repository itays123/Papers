/**
 * Assignment 15 - using a class to represent a word in a text list.
 * 
 * @author Itay Schechner
 * @version 06-06-2021
 */
public class WordNode {

    private String _word;
    private WordNode _next;

    /**
     * Creates a node with no linking for another node
     * 
     * @param word the value that the node holds
     */
    public WordNode(String word) {
        _word = word;
        _next = null;
    }

    /**
     * Creates a node with a link to another node
     * 
     * @param word the value that the node holds
     * @param next the node to link to
     */
    public WordNode(String word, WordNode next) {
        _word = word;
        _next = next;
    }

    /**
     * Get the value of the word
     * 
     * @return the word value
     */
    public String getWord() {
        return _word;
    }

    /**
     * Modify the value of the word
     * 
     * @param word the new value of the word
     */
    public void setWord(String word) {
        _word = word;
    }

    /**
     * Get the next node
     * 
     * @return the next node refrence
     */
    public WordNode getNext() {
        return _next;
    }

    /**
     * Modify the value of the next node
     * 
     * @param next the new node to link to
     */
    public void setNext(WordNode next) {
        _next = next;
    }

    /**
     * Returns a string representation of the node
     * 
     * @return a string representation in the following format: { _word=<word>,
     *         _next=<next> }
     */
    public String toString() {
        return "{" + " _word='" + getWord() + "'" + ", _next='" + getNext() + "'" + "}";
    }

}
