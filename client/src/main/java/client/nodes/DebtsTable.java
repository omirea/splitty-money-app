package client.nodes;

import commons.Debt;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeView;
import javafx.scene.text.TextFlow;

public class DebtsTable {

    private CheckBox checkBox;
    private TreeView<TextFlow> treeView;
    private Button email;
    private Button IBAN;
    private Button closeDebt;
    private Debt debt;

    public DebtsTable(CheckBox checkBox, TreeView<TextFlow> treeView, Button email,
                      Button IBAN, Button closeDebt, Debt debt) {
        this.checkBox=checkBox;
        this.treeView=treeView;
        this.email = email;
        this.IBAN = IBAN;
        this.closeDebt = closeDebt;
        this.debt=debt;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }


    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TreeView<TextFlow> getTreeView() {
        return treeView;
    }

    public void setTreeView(TreeView<TextFlow> treeView) {
        this.treeView = treeView;
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
