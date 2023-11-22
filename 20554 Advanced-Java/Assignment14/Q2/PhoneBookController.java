import java.io.IOException;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class PhoneBookController {

    private PhoneBook phoneBook;

    @FXML
    private TextField searchArea;

    @FXML
    private ListView<Contact> contactList;

    private ObservableList<Contact> contacts;

    @FXML
    void initialize() {
        loadPhoneBook();

        // load contact list
        contacts = FXCollections.observableArrayList();
        contactList.setItems(contacts);
        contactList.setCellFactory(contact -> {
            return new ContactListCell();
        });

        // subscribe to text area changes
        renderContacts("");
        searchArea.textProperty().addListener((property, oldValue, newValue) -> {
            contacts.clear();
            renderContacts(newValue);
        });
    }

    /**
     * Render contacts to list
     */
    void renderContacts(String query) {
        contacts.addAll(phoneBook.search(query).collect(Collectors.toList()));
    }

    /**
     * Try to load phone book
     */
    public void loadPhoneBook() {
        try {
            phoneBook = new PersistablePhoneBook();
            System.out.println(phoneBook.entries);
        } catch (IOException e) {
            phoneBook = new PhoneBook();
        }
    }

    /**
     * Save phone book to file if possible
     */
    public void savePhoneBook() {
        try {
            if (phoneBook instanceof PersistablePhoneBook)
                ((PersistablePhoneBook) phoneBook).persist();
        } catch (IOException e) {
            System.out.println("Error saving phone book");
        }
    }

}
