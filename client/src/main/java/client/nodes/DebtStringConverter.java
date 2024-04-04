package client.nodes;

import commons.Debt;
import javafx.util.StringConverter;

public class DebtStringConverter extends StringConverter<Debt> {
    @Override
    public String toString(Debt object) {
        if(object!=null)
            return object.getHasToPay().getName();
        else
            return null;
    }

    @Override
    public Debt fromString(String string) {
        return null;
    }
}
