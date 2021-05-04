package api.dto;

public class DtoWarning {

    private final String spaceName;
    private final String spaceType;
    private final int capacity;
    private final int amount;

    public DtoWarning(String spaceName, String spaceType, int capacity, int amount) {
        this.spaceName = spaceName;
        this.spaceType = spaceType;
        this.capacity = capacity;
        this.amount = amount;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAmount() {
        return amount;
    }

}
