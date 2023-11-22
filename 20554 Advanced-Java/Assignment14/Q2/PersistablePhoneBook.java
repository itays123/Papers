import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Represents a phone book that persists on file save
 */
public class PersistablePhoneBook extends PhoneBook {

    public static final String FILE_NAME = "PhoneBook.ser";

    /**
     * Reads saved phone book from file
     */
    public PersistablePhoneBook() throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            entries = (TreeMap<String, String>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            // initialize a new map
            entries = new TreeMap<>((Comparator<String> & Serializable) (o1, o2) -> {
                return o1.compareTo(o2);
            });
        } catch (ClassNotFoundException e) {
            // should not happen, do nothing
        }
    }

    /**
     * Saves the phone book to a file
     */
    public void persist() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(entries);

        objectOutputStream.close();
        fileOutputStream.close();
    }

}
