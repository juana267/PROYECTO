package pe.edu.upeu.segundaunidadfx.componente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;

public class ComboBoxAutoComplete<T> {

    private final ComboBox<T> comboBox;
    private final ObservableList<T> originalItems;

    public ComboBoxAutoComplete(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        this.originalItems = FXCollections.observableArrayList(comboBox.getItems());
        setupAutoComplete();
    }

    private void setupAutoComplete() {
        comboBox.setTooltip(new Tooltip());
        comboBox.setEditable(true);

        comboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                comboBox.getItems().setAll(originalItems);
            } else {
                ObservableList<T> filteredItems = FXCollections.observableArrayList();
                for (T item : originalItems) {
                    if (item.toString().toLowerCase().contains(newText.toLowerCase())) {
                        filteredItems.add(item);
                    }
                }
                comboBox.getItems().setAll(filteredItems);
            }
            comboBox.show();
        });

        comboBox.setOnHidden(event -> comboBox.getItems().setAll(originalItems));
    }
}
