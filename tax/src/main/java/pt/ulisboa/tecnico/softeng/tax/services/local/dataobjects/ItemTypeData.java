package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;

public class ItemTypeData {
    private String name;
    private int tax;

    public ItemTypeData() {
    }

    public ItemTypeData(ItemType itemtype) {
        this.name = itemtype.getName();
        this.tax = itemtype.getTax();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}
