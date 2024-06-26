package View.GUI.TableEntries;

import Model.Values.Value;
import javafx.beans.property.SimpleStringProperty;

public class SymbolTableEntry {
    private final String id;
    private final Value value;

    public SymbolTableEntry(String id, Value value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Value getValue() {
        return value;
    }

    public SimpleStringProperty idProperty() {
        return new SimpleStringProperty(this.id);
    }

    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(this.value.toString());
    }
}
