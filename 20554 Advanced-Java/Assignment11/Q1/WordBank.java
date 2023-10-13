import java.util.Random;

/**
 * The class to store possible words
 */
public class WordBank {

    private static final String[] wordBank = {
            "lamp", "jacket", "kite", "ocean", "fire", "dog", "zeppelin", "orange", "apple", "car",
            "elephant", "moon", "guitar", "queen", "zebra", "pencil", "cherry", "book", "violet", "watermelon",
            "quilt", "fox", "helicopter", "rose", "banana", "jigsaw", "hat", "island", "umbrella", "snake",
            "yarn", "cake", "lion", "desk", "kangaroo", "airplane", "yoga", "turtle", "giraffe", "rainbow",
            "xylophone", "mango", "jelly", "dolphin", "frog", "volcano", "nest", "butterfly", "kiwi", "horse",
            "sun", "ninja", "mountain", "carrot", "penguin", "yacht", "cactus", "ostrich", "octopus", "globe",
            "piano", "tiger", "daisy", "parrot", "whale", "rosemary", "squirrel", "mermaid", "dragon", "sunflower",
            "lighthouse", "comet", "giraffe", "guitar", "dolphin", "candle", "tornado", "dinosaur", "tulip", "sunset"
    };

    private static final Random rand = new Random();

    /**
     * Randomly picks a word from the word bank
     */
    public static String getRandomWord() {
        int index = rand.nextInt(wordBank.length);
        return wordBank[index];
    }

}
