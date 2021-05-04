package api.dto;

public class DtoSpaceSummary {

    private final String space;
    private final int capacity;
    private final int devices;
    private final int amount;
    private final boolean supered;

    public DtoSpaceSummary(String space, int capacity, int devices, int amount, boolean supered) {
        this.space = space;
        this.capacity = capacity;
        this.devices = devices;
        this.amount = amount;
        this.supered = supered;
    }

    public String getSpace() {
        return space;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getDevices() {
        return devices;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isSupered() {
        return supered;
    }

}
