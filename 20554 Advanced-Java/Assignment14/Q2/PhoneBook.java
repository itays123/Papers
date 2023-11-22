import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Represents a phone book
 */
public class PhoneBook {
    protected TreeMap<String, String> entries;

    /**
     * Create an empty phonebook
     */
    public PhoneBook() {
        entries = new TreeMap<String, String>();
    }

    /**
     * Put a phone number under a name. Adds a row to the phone book if doesn't
     * exist, and updates it if does exist
     */
    public void put(String name, String phone) {
        entries.put(name, phone);
    }

    /**
     * Remove an entry from the phone book associated with a given name.
     */
    public void remove(String name) {
        entries.remove(name);
    }

    /**
     * Search for contacts in the phone book
     */
    public Stream<Contact> search(String query) {
        return entries.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(query))
                .map(entry -> new Contact(entry.getKey(), entry.getValue()));
    }

}
