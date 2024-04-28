package entities.algorithm;

import entities.algorithm.Constants;
import entities.algorithm.Sector;
import entities.algorithm.Trainer;

import java.util.Arrays;
import java.util.Random;

public class Schedule {

    private final Trainer[] trainers;
    private final Sector[] sectors;
    private final int[][] matrix;
    private final double fitness;
    private final Random rnd;

    public Schedule(Trainer[] trainers, Sector[] sectors) {
        rnd = new Random();
        this.trainers = trainers;
        this.sectors = sectors;

        int[][] randomSchedule = new int[trainers.length][Constants.SHIFTS_PER_WEEK];

        for (int[] ints : randomSchedule) {
            Arrays.fill(ints, -1);
        }

        double probability = rnd.nextDouble();

        for (int i = 0; i < randomSchedule.length; i++) {
            for (int j = 0; j < randomSchedule[i].length; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < probability)
                    randomSchedule[i][j] = rnd.nextInt(sectors.length);
            }
        }

        matrix = randomSchedule;
        fitness = calculateFitnessForSchedule(matrix);
    }

    public Schedule(Schedule a, Schedule b) {
        rnd = new Random();
        this.trainers = a.getTrainers();
        this.sectors = a.getSectors();

        int[][] child = new int[a.getTrainers().length][Constants.SHIFTS_PER_WEEK];

        double prob = rnd.nextDouble();

        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child[i].length; j++) {

                if (rnd.nextDouble() < prob)
                    child[i][j] = a.getMatrix()[i][j];
                else
                    child[i][j] = b.getMatrix()[i][j];
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

    public void mutateSchedule(double mutationRate) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < Constants.SHIFTS_PER_WEEK; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < mutationRate)
                    matrix[i][j] = rnd.nextInt(sectors.length + 1) - 1;
            }
        }
    }

}