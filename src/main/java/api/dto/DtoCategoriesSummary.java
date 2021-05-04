package api.dto;

public class DtoCategoriesSummary {

    private final int categoryId;
    private final String category;
    private final long devices;
    private final long amount;

    public DtoCategoriesSummary(int categoryId, String category, long devices, long amount) {
        this.categoryId = categoryId;
        this.category = category;
        this.devices = devices;
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public long getDevices() {
        return devices;
    }

    public long getAmount() {
        return amount;
    }

}
