package entities.algorithm;

import entities.algorithm.Constants;
import entities.algorithm.Sector;
import entities.algorithm.Trainer;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a scheduling solution for trainers across different sectors using a genetic algorithm.
 */
public class Schedule {

    private final Trainer[] trainers;
    private final Sector[] sectors;
    private final int[][] matrix;
    private final double fitness;
    private final Random rnd;

    /**
     * Constructor to create an initial random schedule.
     * @param trainers the array of trainers.
     * @param sectors the array of sectors.
     */
    public Schedule(Trainer[] trainers, Sector[] sectors) {
        rnd = new Random();
        this.trainers = trainers;
        this.sectors = sectors;

        // Initialize the scheduling matrix with all slots unassigned (-1)
        int[][] randomSchedule = new int[trainers.length][Constants.SHIFTS_PER_WEEK];
        for (int[] row : randomSchedule) {
            Arrays.fill(row, -1);
        }

        // Randomly assign trainers to sectors based on availability and a probability factor
        double probability = rnd.nextDouble();
        for (int i = 0; i < randomSchedule.length; i++) {
            for (int j = 0; j < randomSchedule[i].length; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < probability) {
                    randomSchedule[i][j] = rnd.nextInt(sectors.length);
                }
            }
        }

        this.matrix = randomSchedule;
        this.fitness = calculateFitnessForSchedule(matrix);
    }

    /**
     * Constructor to create a schedule by combining two parent schedules.
     * @param a the first parent schedule.
     * @param b the second parent schedule.
     */
    public Schedule(Schedule a, Schedule b) {
        rnd = new Random();
        this.trainers = a.getTrainers();
        this.sectors = a.getSectors();

        int[][] childMatrix = new int[trainers.length][Constants.SHIFTS_PER_WEEK];
        double crossoverProb = rnd.nextDouble();
        for (int i = 0; i < childMatrix.length; i++) {
            for (int j = 0; j < childMatrix[i].length; j++) {
                childMatrix[i][j] = rnd.nextDouble() < crossoverProb ? a.getMatrix()[i][j] : b.getMatrix()[i][j];
            }
        }

        this.matrix = childMatrix;
        this.fitness = calculateFitnessForSchedule(matrix);
    }

    /**
     * Calculates the fitness of a given schedule based on various constraints and penalties.
     * @param schedule the schedule matrix.
     * @return the calculated fitness value.
     */
    private double calculateFitnessForSchedule(int[][] schedule) {
        double totalPenalties = 0;

        // Evaluate penalties for each shift
        for (int shiftIndex = 0; shiftIndex < Constants.SHIFTS_PER_WEEK; shiftIndex++) {
            int[] shift = Arrays.stream(schedule).mapToInt(row -> row[shiftIndex]).toArray();
            totalPenalties += calculatePenaltiesForShift(shift, shiftIndex);
        }

        // Evaluate penalties for each trainer's weekly rest periods
        for (int[] trainerSchedule : schedule) {
            totalPenalties += calculatePenaltiesForTrainerRestingTime(trainerSchedule);
        }

        return 1 / (1 + totalPenalties); // Fitness is higher when penalties are lower
    }

    /**
     * Calculates penalties for a single shift.
     * @param shift array representing trainer assignments for a single shift.
     * @param shiftNumber the index of the shift in the week.
     * @return the penalty score for that shift.
     */
    private double calculatePenaltiesForShift(int[] shift, int shiftNumber) {
        double shiftPenalties = 0;
        int shiftType = shiftNumber % Constants.SHIFT_PER_DAY; // Determine the type of the shift (morning, noon, evening)
        boolean hasManager = false;

        for (int sectorIndex = 0; sectorIndex < sectors.length; sectorIndex++) {
            int numTrainers = (int) Arrays.stream(shift).filter(slot -> slot == sectorIndex).count();
            Sector sector = sectors[sectorIndex];
            int requiredTrainers = sector.getShiftsSize(shiftType);

            if (numTrainers < requiredTrainers)
                shiftPenalties += Constants.TRAINER_UNDER_BOOK_PENALTY;

            if (numTrainers > requiredTrainers)
                shiftPenalties += Constants.TRAINER_OVER_BOOK_PENALTY;

            if (!Arrays.stream(shift).anyMatch(id -> id == sectorIndex && trainers[id].isManager()))
                hasManager = true;
        }

        if (!hasManager)
            shiftPenalties += Constants.SHIFT_WITHOUT_MANAGER_PENALTY;

        return shiftPenalties * shiftPenalties / 10000;
    }

    /**
     * Calculates penalties for not providing sufficient rest between shifts for trainers.
     * @param trainerWeeklySchedule the schedule of a single trainer over a week.
     * @return the penalty score based on rest violations.
     */

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
     * Mutates the schedule based on a given mutation rate.
     * @param mutationRate the probability of changing any given assignment in the schedule.
     */
    public void mutateSchedule(double mutationRate) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < Constants.SHIFTS_PER_WEEK; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < mutationRate) {
                    matrix[i][j] = rnd.nextInt(sectors.length + 1) - 1; // Random re-assignment with a chance of unassignment
                }
            }
        }
    }

    // Getters
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
}
