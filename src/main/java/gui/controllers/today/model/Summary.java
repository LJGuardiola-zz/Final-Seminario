package gui.controllers.today.model;

import api.dto.DtoCategoriesSummary;
import javafx.beans.property.*;

public class Summary {

    private final IntegerProperty categoryId;
    private final StringProperty category;
    private final LongProperty devices;
    private final LongProperty amount;

    public Summary(DtoCategoriesSummary summary) {
        this.categoryId = new SimpleIntegerProperty(summary.getCategoryId());
        this.category = new SimpleStringProperty(summary.getCategory());
        this.devices = new SimpleLongProperty(summary.getDevices());
        this.amount = new SimpleLongProperty(summary.getAmount());
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public long getDevices() {
        return devices.get();
    }

    public LongProperty devicesProperty() {
        return devices;
    }

    public void setDevices(long devices) {
        this.devices.set(devices);
    }

    public long getAmount() {
        return amount.get();
    }

    public LongProperty amountProperty() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount.set(amount);
    }

}
