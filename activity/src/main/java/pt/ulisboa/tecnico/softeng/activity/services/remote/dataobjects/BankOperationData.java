package pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects;

import org.joda.time.DateTime;

public class BankOperationData {
    private String reference;
    private String type;
    private String iban;
    private Double value;
    private DateTime time;

    public BankOperationData() {
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DateTime getTime() {
        return this.time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}
