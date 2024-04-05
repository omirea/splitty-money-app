package client.nodes;

import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.text.TextFlow;

public class DebtsTable {

    private TreeView<TextFlow> treeView;
    private Button email;
    private Button IBAN;
    private Button closeDebt;

    public DebtsTable(TreeView<TextFlow> treeView, Button email, Button IBAN, Button closeDebt) {
        this.treeView=treeView;
        this.email = email;
        this.IBAN = IBAN;
        this.closeDebt = closeDebt;
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
