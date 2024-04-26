package entities.algorithm;

import java.util.*;

public class GeneticAlgorithm {
    private final Trainer[] trainers;
    private final Sector[] sectors;
    private final Random rnd;

    public GeneticAlgorithm(Trainer[] trainers, Sector[] sectors) {
        this.trainers = trainers;
        this.sectors = sectors;
        rnd = new Random();
    }

    private ScheduleHolder[] createRandomFirstGeneration(int generationSize) {
        ScheduleHolder[] randomGeneration = new ScheduleHolder[generationSize];

        for (int i = 0; i < randomGeneration.length; i++) {
            int[][] randomSchedule = createRandomSchedule();
            double scheduleFitness = calculateFitnessForSchedule(randomSchedule);
            randomGeneration[i] = new ScheduleHolder(randomSchedule, scheduleFitness);
        }

        return randomGeneration;
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


    private int[][] createRandomSchedule() {
        int[][] firstGeneration = new int[trainers.length][Constants.SHIFTS_PER_WEEK];

        for (int[] ints : firstGeneration) {
            Arrays.fill(ints, -1);
        }

        double probability = rnd.nextDouble();

        for (int i = 0; i < firstGeneration.length; i++) {
            for (int j = 0; j < firstGeneration[i].length; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < probability)
                    firstGeneration[i][j] = rnd.nextInt(sectors.length);
            }
        }

        return firstGeneration;
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

    private int[][] crossover(int[][] parentA, int[][] parentB) {
        int[][] child = new int[trainers.length][Constants.SHIFTS_PER_WEEK];

        double prob = rnd.nextDouble();

        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child[i].length; j++) {

                if (rnd.nextDouble() < prob)
                    child[i][j] = parentA[i][j];
                else
                    child[i][j] = parentB[i][j];
            }
        }

        return child;
    }

    private ScheduleHolder[] createNextGeneration(ScheduleHolder[] currentGeneration, double elitismPercentage, double mutationRate) {
        ScheduleHolder[] nextGeneration = new ScheduleHolder[currentGeneration.length];

        Arrays.sort(currentGeneration, Comparator.comparingDouble((ScheduleHolder holder) -> holder.fitness).reversed());

        int i = 0;
        for (; i < currentGeneration.length * elitismPercentage; i++) {
            nextGeneration[i] = currentGeneration[i];
        }

        for (; i < nextGeneration.length; i++) {
            ScheduleHolder parentA = selectParents(currentGeneration);
            ScheduleHolder parentB = null;
            while (parentA != parentB)
                parentB = selectParents(currentGeneration);

            int[][] childSchedule = crossover(parentA.schedule, parentB.schedule);
            mutateSchedule(childSchedule, mutationRate);

            double childFitness = calculateFitnessForSchedule(childSchedule);
            ScheduleHolder child = new ScheduleHolder(childSchedule, childFitness);

            nextGeneration[i] = child;
        }

        return nextGeneration;
    }

    private ScheduleHolder selectParents(ScheduleHolder[] scheduleHolders) {
        Random random = new Random();
        int tournamentSize = 3;

        ScheduleHolder[] tournament = new ScheduleHolder[tournamentSize];
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(scheduleHolders.length);
            tournament[i] = scheduleHolders[randomIndex];
        }

        return getBestSchedule(tournament);
    }

    private ScheduleHolder getBestSchedule(ScheduleHolder[] scheduleHolders) {
        ScheduleHolder bestScheduleHolder = scheduleHolders[0];

        for (ScheduleHolder scheduleHolder : scheduleHolders) {
            if (scheduleHolder.fitness > bestScheduleHolder.fitness)
                bestScheduleHolder = scheduleHolder;
        }

        return bestScheduleHolder;
    }

    private void mutateSchedule(int[][] schedule, double mutationRate) {
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < Constants.SHIFTS_PER_WEEK; j++) {
                if (trainers[i].isAvailable(j) && rnd.nextDouble() < mutationRate)
                    schedule[i][j] = rnd.nextInt(sectors.length + 1) - 1;
            }
        }
    }

    public int[][] runAlgorithm(int generationAmount, int generationSize, double mutationRate, double elitismPercentage){
        ScheduleHolder[] generation = createRandomFirstGeneration(generationSize);
        printScheduleBySector(getBestSchedule(generation).schedule);

        for (int i = 0; i < generationAmount; i++) {
            generation = createNextGeneration(generation, elitismPercentage, mutationRate);
            System.out.println("Generation number " + i + ", Average fitness: " + getAverageFitness(generation) + ", Best fitness: " + getBestSchedule(generation).fitness);
        }

        printScheduleBySector(getBestSchedule(generation).schedule);
        return getBestSchedule(generation).schedule;
    }

    private double getAverageFitness(ScheduleHolder[] scheduleHolders) {
        double fitnessSum = 0;

        for (ScheduleHolder scheduleHolder : scheduleHolders) {
            fitnessSum += scheduleHolder.fitness;
        }

        return fitnessSum / scheduleHolders.length;
    }

    private void printScheduleBySector(int[][] schedule) {
        // Iterate over each sector
        for (int sectorIndex = 0; sectorIndex < sectors.length; sectorIndex++) {
            System.out.println("Schedule for Sector " + sectors[sectorIndex].getName() + ":");

            // Iterate over each shift in the week
            for (int shift = 0; shift < Constants.SHIFTS_PER_WEEK; shift++) {
                System.out.print("Shift " + shift + ": ");
                List<String> assignedTrainers = new ArrayList<>();

                // Check each guard to see if they are assigned to this sector for this shift
                for (int guardIndex = 0; guardIndex < trainers.length; guardIndex++) {
                    if (schedule[guardIndex][shift] == sectorIndex) {
                        String trainerName = trainers[guardIndex].getName();

                        trainerName += " ";

                        if (trainers[guardIndex].isManager())
                            trainerName += "M";

                        assignedTrainers.add(trainerName); // Assuming Guard has a getName() method
                    }
                }

                // Print out the names of the guards assigned to this shift
                if (assignedTrainers.isEmpty()) {
                    System.out.println("No guards assigned.");
                } else {
                    System.out.println(String.join(", ", assignedTrainers));
                }
            }
            System.out.println(); // Add a line break for better readability
        }
    }




}
