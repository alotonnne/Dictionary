package dictionaryAction;

import CommandLine.Word;
import javafx.scene.control.ListCell;

public class MyCell extends ListCell<String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item);
        } else {
            setText(null);
        }
    }
}

