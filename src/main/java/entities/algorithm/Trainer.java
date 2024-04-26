package entities.algorithm;

public class Trainer {
    private final String name;
    private final boolean[] availability;
    private final boolean isManager;

    public Trainer(String name, boolean[] availability, boolean isManager) {
        if (availability.length != Constants.SHIFTS_PER_WEEK)
            throw new IllegalArgumentException("Availability array length must be " + Constants.DAYS_IN_WEEK + " as the number shifts in a week");
        this.name = name;
        this.availability = availability;
        this.isManager = isManager;
    }

    public String getName() {
        return name;
    }

    public boolean[] getAvailability() {
        return availability;
    }

    public boolean isManager() {
        return isManager;
    }

    public boolean isAvailable(int index) {
        return availability[index];
    }
}
