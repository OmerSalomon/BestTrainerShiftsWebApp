package entities.algorithm;

public class Sector {
    private String name;
    private int[] shiftsSize;

    public Sector(String name, int[] shiftsSize) {
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
