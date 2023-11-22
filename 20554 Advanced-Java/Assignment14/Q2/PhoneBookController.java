import java.io.IOException;

import javafx.fxml.FXML;

public class PhoneBookController {

    private PhoneBook phoneBook;

    @FXML
    void initialize() {
        try {
            phoneBook = new PersistablePhoneBook();
            System.out.println(phoneBook.entries);
        } catch (IOException e) {
            phoneBook = new PhoneBook();
        }
    }

    public void persist() {
        try {
            if (phoneBook instanceof PersistablePhoneBook)
                ((PersistablePhoneBook) phoneBook).persist();
        } catch (IOException e) {
            System.out.println("Error saving phone book");
        }
    }

}
