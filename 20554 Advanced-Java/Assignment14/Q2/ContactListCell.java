import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Represents a contact list cell
 */
public class ContactListCell extends ListCell<Contact> {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button removeBtn;

    @FXML
    private HBox pane;

    public ContactListCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactListCell.fxml"));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            return;
        }

        nameLabel.setText(item.getName());
        phoneNumberField.setText(item.getPhoneNumber());

        setGraphic(pane);
    }

    public void setOnPhoneNumberUpdate(BiConsumer<String, String> updateHandler) {
        phoneNumberField.textProperty()
                .addListener((prop, oldValue, newValue) -> updateHandler.accept(nameLabel.getText(), newValue));
    }

    public void setOnDelete(Consumer<String> deleteHandler) {
        removeBtn.setOnAction(event -> {
            deleteHandler.accept(nameLabel.getText());
        });

    }
}
