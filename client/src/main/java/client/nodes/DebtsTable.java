package client.nodes;

import javafx.scene.control.Button;

public class DebtsTable {
    private String debtInfo;
    private Button email;
    private Button IBAN;
    private Button closeDebt;

    public DebtsTable(String debtInfo, Button email, Button IBAN, Button closeDebt) {
        this.debtInfo = debtInfo;
        this.email = email;
        this.IBAN = IBAN;
        this.closeDebt = closeDebt;
    }

    public String getDebtInfo() {
        return debtInfo;
    }

    public void setDebtInfo(String debtInfo) {
        this.debtInfo = debtInfo;
    }

    public Button getEmail() {
        return email;
    }

    public void setEmail(Button email) {
        this.email = email;
    }

    public Button getIBAN() {
        return IBAN;
    }

    public void setIBAN(Button IBAN) {
        this.IBAN = IBAN;
    }

    public Button getCloseDebt() {
        return closeDebt;
    }

    public void setCloseDebt(Button closeDebt) {
        this.closeDebt = closeDebt;
    }
}
