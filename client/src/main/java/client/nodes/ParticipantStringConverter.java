package client.nodes;

import commons.Participant;
import javafx.util.StringConverter;

public class ParticipantStringConverter extends StringConverter<Participant> {
    @Override
    public String toString(Participant participant) {
        if (participant == null) {
            return null;
        } else {
            return participant.getName();
        }
    }

    @Override
    public Participant fromString(String string) {
        return null;
    }
}
