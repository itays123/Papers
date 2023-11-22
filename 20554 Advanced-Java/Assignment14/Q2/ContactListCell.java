import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * Represents a contact list cell
 */
public class ContactListCell extends ListCell<Contact> {

    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null)
            setGraphic(new HBox());
        else
            setGraphic(new HBox(new Label(item.getName())));
    }

}
