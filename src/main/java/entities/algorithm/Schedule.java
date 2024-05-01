package entities.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Schedule {

    private final Trainer[] trainers;
    private final Sector[] sectors;
    private final int[][] matrix;
    private final double fitness;
    private final Random rnd;

    /**
     * Constructs a new Schedule object using arrays of trainers and sectors. Initializes the schedule matrix randomly
     * based on trainer availability and a random probability for assignment.
     *
     * @param trainers Array of {@link Trainer} objects representing the trainers.
     * @param sectors Array of {@link Sector} objects representing the sectors.
     */
    public Schedule(Trainer[] trainers, Sector[] sectors) {
        rnd = new Random();
        this.trainers = trainers;
        this.sectors = sectors;

        int[][] randomSchedule = new int[sectors.length][Constants.SHIFTS_PER_WEEK];

        for (int[] ints : randomSchedule) {
            Arrays.fill(ints, -1);
        }

        double probability = rnd.nextDouble();

        for (int shiftCounter = 0; shiftCounter < Constants.SHIFTS_PER_WEEK; shiftCounter++) {
            int[] sectorGuardCount = new int[sectors.length];
            int shiftType = shiftCounter % Constants.SHIFT_PER_DAY;

            for (int guardCounter = 0; guardCounter < sectors.length; guardCounter++) {
                if (trainers[guardCounter].isAvailable(shiftCounter) && rnd.nextDouble() < probability) {
                    int sectorNum = rnd.nextInt(sectors.length);

                    if (sectorGuardCount[sectorNum] < sectors[sectorNum].getShiftsSize(shiftType)) {
                        randomSchedule[guardCounter][shiftCounter] = sectorNum;
                        sectorGuardCount[sectorNum]++;
                    }

                }
            }
        }

        matrix = randomSchedule;
        fitness = calculateFitnessForSchedule(matrix);
    }


    /**
     * Creates a new schedule by combining features from two parent schedules.
     * This is typically used in genetic algorithms to create new solutions from existing ones.
     *
     * @param a First parent schedule.
     * @param b Second parent schedule.
     */
    public Schedule(Schedule a, Schedule b) {
        rnd = new Random();
        this.trainers = a.getTrainers();
        this.sectors = a.getSectors();

        int[][] child = new int[trainers.length][Constants.SHIFTS_PER_WEEK];

        for (int[] ints : child) {
            Arrays.fill(ints, -1);
        }

        double prob = rnd.nextDouble();

        for (int shiftCounter = 0; shiftCounter < Constants.SHIFTS_PER_WEEK; shiftCounter++) {
            int[] sectorTrainerCount = new int[sectors.length];
            int shiftType = shiftCounter % Constants.SHIFT_PER_DAY;
            for (int trainerCounter = 0; trainerCounter < trainers.length; trainerCounter++) {

                int childSectorNum;

                if (prob > rnd.nextDouble())
                    childSectorNum = a.getMatrix()[trainerCounter][shiftCounter];
                else
                    childSectorNum = b.getMatrix()[trainerCounter][shiftCounter];

                if (childSectorNum != -1 && sectorTrainerCount[childSectorNum] < sectors[childSectorNum].getShiftsSize(shiftType)) {
                    child[trainerCounter][shiftCounter] = childSectorNum;
                    sectorTrainerCount[childSectorNum]++;
                }
            }
        }

        matrix = child;
        fitness = calculateFitnessForSchedule(matrix);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public double getFitness() {
        return fitness;
    }

    public Trainer[] getTrainers() {
        return trainers;
    }

    public Sector[] getSectors() {
        return sectors;
    }


    /**
     * Calculates penalties for a specific shift based on various constraints like the required number of trainers
     * and the presence of a manager. Penalties are imposed for underbooking and overbooking of trainers,
     * and an additional penalty is added if no manager is present during the shift.
     *
     * @param shift An array representing the sector assignments for each trainer during this shift. Each element in the
     *              array corresponds to a trainer, where the value is the sector number the trainer is assigned to,
     *              or -1 if the trainer is not assigned to any sector.
     * @param shiftNumber The index of the shift within the week, used to determine the type of shift (morning, noon, evening).
     * @return The total penalty calculated for this shift, adjusted by a factor for normalization.
     */
    private double calculatePenaltiesForShift(int[] shift, int shiftNumber) {
        double shiftPenalties = 0;
        int shiftType = shiftNumber % Constants.SHIFT_PER_DAY; // morning = 0, noon = 1, evening = 2

        boolean hasManager = false;

        for (int sectorNum = 0; sectorNum < sectors.length; sectorNum++) {
            Sector sector = sectors[sectorNum];

            int numberOfTrainersInSectorShift = 0;

            for (int i = 0; i < shift.length; i++) {
                if (shift[i] == sectorNum) {
                    Trainer trainer = trainers[i];
                    hasManager |= trainer.isManager();
                    numberOfTrainersInSectorShift++;
                }
            }

            int shiftSize = sector.getShiftsSize(shiftType);

            if (numberOfTrainersInSectorShift < shiftSize)
                shiftPenalties += Constants.TRAINER_UNDER_BOOK_PENALTY;

            if (numberOfTrainersInSectorShift > shiftSize)
                shiftPenalties += Constants.TRAINER_OVER_BOOK_PENALTY;
            shiftPenalties += (Math.abs(sector.getShiftsSize(shiftType) - numberOfTrainersInSectorShift)) * Constants.TRAINER_UNDER_BOOK_PENALTY;
        }

        if (!hasManager)
            shiftPenalties += Constants.SHIFT_WITHOUT_MANAGER_PENALTY;

        return shiftPenalties * shiftPenalties / 10000;
    }

    /**
     * Calculates the overall fitness of the schedule based on penalty conditions such as inadequate rest between shifts
     * and inadequate or excessive number of trainers per shift.
     *
     * @param schedule The schedule matrix where rows represent trainers and columns represent shifts.
     * @return The calculated fitness value as a double.
     */
    private double calculateFitnessForSchedule(int[][] schedule){
        double totalPenalties = 0;

        for (int shiftIterator = 0; shiftIterator < Constants.SHIFTS_PER_WEEK; shiftIterator++) {
            int[] shift = new int[trainers.length];
            for (int j = 0; j < trainers.length; j++) {
                shift[j] = schedule[j][shiftIterator];
            }

            totalPenalties += calculatePenaltiesForShift(shift, shiftIterator);
        }

        for (int i = 0; i < schedule.length; i++) {
            totalPenalties += calculatePenaltiesForTrainerRestingTime(schedule[i]);
        }

        return 1 / (1 + totalPenalties);
    }

    private double calculatePenaltiesForTrainerRestingTime(int[] trainerWeeklySchedule) {
        double trainerRestPenalties = 0;

        for (int i = 0; i < trainerWeeklySchedule.length; i++) {
            int nextShiftIndex = (i + 1) % trainerWeeklySchedule.length;
            int nextTwoShiftIndex = (i + 2) % trainerWeeklySchedule.length;

            boolean isWorkingNextShift = trainerWeeklySchedule[nextShiftIndex] != -1;
            boolean isWorkingNextTwoShifts = trainerWeeklySchedule[nextTwoShiftIndex] != -1;

            int shiftTypeNum = i % 3;

            if (shiftTypeNum == 0 && isWorkingNextShift)
                trainerRestPenalties += Constants.NOT_ENOUGH_REST_PENALTY;

            if (shiftTypeNum == 1 && isWorkingNextShift)
                trainerRestPenalties += Constants.NOT_ENOUGH_REST_PENALTY;

            if (shiftTypeNum == 2 && (isWorkingNextShift || isWorkingNextTwoShifts))
                trainerRestPenalties += Constants.NOT_ENOUGH_REST_PENALTY;
        }

        return trainerRestPenalties * trainerRestPenalties / 10000;
    }

    /**
     * Mutates the schedule by randomly altering the shift assignments at a specified mutation rate. This is typically used
     * in genetic algorithms to introduce variation.
     *
     * @param mutationRate The probability of a shift being mutated.
     */
    public void mutateSchedule(double mutationRate) {
        for (int shiftCounter = 0; shiftCounter < Constants.SHIFTS_PER_WEEK; shiftCounter++) {

            int[] sectorGuardCount = new int[sectors.length];
            int shiftType = shiftCounter % Constants.SHIFT_PER_DAY;

            for (int guardCounter = 0; guardCounter < sectors.length; guardCounter++) {
                int sectorNum = matrix[guardCounter][shiftCounter];

                if (sectorNum != -1)
                    sectorGuardCount[sectorNum]++;
            }

            for (int guardCounter = 0; guardCounter < sectors.length; guardCounter++) {

                int oldSectorNum = matrix[guardCounter][shiftCounter];
                if (oldSectorNum != -1 && mutationRate > rnd.nextDouble()) {
                    int newSectorNum = rnd.nextInt(sectors.length);

                    if (sectorGuardCount[newSectorNum] < sectors[newSectorNum].getShiftsSize(shiftType)) {
                        matrix[guardCounter][shiftCounter] = newSectorNum;
                        sectorGuardCount[oldSectorNum]--;
                        sectorGuardCount[newSectorNum]++;
                    }

                }
            }
        }
    }

}