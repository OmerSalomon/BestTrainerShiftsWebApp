package entities.algorithm;

/**
 * Represents a sector with specific shift size requirements.
 */
public class Sector {
    private final String name;
    private final int[] shiftsSize;

    /**
     * Constructs a Sector object with a name and an array representing the required number of trainers per shift type.
     *
     * @param name The name of the sector.
     * @param shiftsSize An array of integers where each integer represents the required number of trainers for each shift type in the sector.
     *                   The length of this array must match the constant number of shifts per day.
     * @throws IllegalArgumentException if the length of shiftsSize does not match the number of shifts per day defined in Constants.
     */
    public Sector(String name, int[] shiftsSize) {
        if (shiftsSize.length != Constants.SHIFTS_PER_DAY)
            throw new IllegalArgumentException("ShiftsSize's length must be equal to the number of shifts in a day");

        this.name = name;
        this.shiftsSize = shiftsSize;
    }

    /**
     * Retrieves the name of the sector.
     *
     * @return The name of the sector.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the number of required trainers for a specified type of shift in this sector.
     *
     * @param shiftTypeNum The index of the shift type, corresponding to different times of the day (e.g., morning, noon, evening).
     * @return The required number of trainers for the specified shift type.
     */
    public int getShiftsSize(int shiftTypeNum) {
        return shiftsSize[shiftTypeNum];
    }
}
