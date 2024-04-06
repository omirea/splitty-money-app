package client.scenes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddEditParticipantCtrlTest {

    /**
    @Test
    public void testCorrectIBAN(){
        AddEditParticipantCtrl participant=new AddEditParticipantCtrl(null, null);
        String IBAN="NL91ABNA0417164300";
        assertTrue(participant.isIbanValid(IBAN));
    }
*/
    @Test
    public void testCorrectEmail(){
        AddEditParticipantCtrl participant=new AddEditParticipantCtrl(null, null);
        String email="correct_email@gmail.com";
        assertTrue(participant.validateEmail(email));
    }


}