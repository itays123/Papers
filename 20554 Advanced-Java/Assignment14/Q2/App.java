import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        PersistablePhoneBook phoneBook = new PersistablePhoneBook();
        System.out.println(phoneBook.search("").collect(Collectors.toList()));
        phoneBook.persist();
    }
}
