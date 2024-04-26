package entities.algorithm;

public class Sector {
    private final String name;
    private final int[] shiftsSize;

    public Sector(String name, int[] shiftsSize) {
        if (shiftsSize.length != Constants.SHIFT_PER_DAY)
            throw new IllegalArgumentException("ShiftsSize's length must be equal to the number of shift in a day");

        this.name = name;
        this.shiftsSize = shiftsSize;
    }

    public String getName() {
        return name;
    }

    public int getShiftsSize(int shiftTypeNum) {
        return shiftsSize[shiftTypeNum];
    }
}
