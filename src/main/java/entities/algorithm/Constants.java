package entities.algorithm;

public class Constants {
    public static final int DAYS_IN_WEEK = 7;
    public static final int SHIFTS_PER_DAY = 3;
    public static final int SHIFTS_PER_WEEK = DAYS_IN_WEEK * SHIFTS_PER_DAY;
    public static final double SHIFT_WITHOUT_MANAGER_PENALTY = 5;
    public static final double TRAINER_UNDER_BOOK_PENALTY = 5;
    public static final double NOT_ENOUGH_REST_PENALTY = 1;
}
