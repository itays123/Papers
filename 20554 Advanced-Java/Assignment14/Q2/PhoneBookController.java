import java.io.IOException;

import javafx.fxml.FXML;

public class PhoneBookController {

    private PersistablePhoneBook phoneBook;

    @FXML
    void initialize() {
        try {
            phoneBook = new PersistablePhoneBook();
            System.out.println(phoneBook.entries);
        } catch (IOException e) {
            System.out.println("Error creating phone book");
            // close window
        }
    }

    public void persist() {
        try {
            phoneBook.persist();
        } catch (IOException e) {
            System.out.println("Error saving phone book");
        }
    }

}
