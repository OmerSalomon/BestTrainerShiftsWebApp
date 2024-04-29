package entities.algorithm;

/**
 * Represents a trainer with their availability and managerial status.
 * This class holds information about a trainer's name, weekly availability for shifts,
 * and whether the trainer is a manager, which is used for scheduling purposes.
 */
public class Trainer {
    private final String name;
    private final boolean[] availability;
    private final boolean isManager;

    /**
     * Constructs a new Trainer with specified name, availability, and managerial status.
     * Throws an IllegalArgumentException if the provided availability array does not match
     * the expected length defined by the number of shifts per week.
     *
     * @param name the name of the trainer
     * @param availability a boolean array indicating availability of the trainer for each shift of the week
     * @param isManager true if the trainer is a manager, false otherwise
     * @throws IllegalArgumentException if the availability array length does not match the required number of shifts
     */
    public Trainer(String name, boolean[] availability, boolean isManager) {
        if (availability.length != Constants.SHIFTS_PER_WEEK)
            throw new IllegalArgumentException("Availability array length must be " + Constants.SHIFTS_PER_WEEK + " as the number shifts in a week");
        this.name = name;
        this.availability = availability;
        this.isManager = isManager;
    }

    /**
     * Gets the name of the trainer.
     *
     * @return the name of the trainer
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the availability of the trainer for all shifts during the week.
     *
     * @return an array of booleans where each index represents the trainer's availability for a specific shift
     */
    public boolean[] getAvailability() {
        return availability;
    }

    /**
     * Checks if the trainer is a manager.
     *
     * @return true if the trainer is a manager, false otherwise
     */
    public boolean isManager() {
        return isManager;
    }

    /**
     * Checks if the trainer is available for a specific shift.
     *
     * @param index the shift index for which availability is queried
     * @return true if the trainer is available for the specified shift, false otherwise
     */
    public boolean isAvailable(int index) {
        return availability[index];
    }
}
