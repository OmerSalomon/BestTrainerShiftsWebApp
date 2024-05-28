package entities.algorithm;

import java.util.*;

/**
 * This class implements a genetic algorithm for scheduling trainers in different sectors.
 */
public class GeneticAlgorithm {
    private Trainer[] trainers;
    private Sector[] sectors;
    private Random rnd;

    /**
     * Constructor initializes the genetic algorithm with specified trainers and sectors.
     * @param trainers array of trainers involved in the scheduling.
     * @param sectors array of sectors where trainers need to be scheduled.
     */
    public GeneticAlgorithm(Trainer[] trainers, Sector[] sectors) {
        this.trainers = trainers;
        this.sectors = sectors;
        rnd = new Random();
    }

    /**
     * Generates a first random generation of schedules.
     * @param generationSize the number of schedules in the generation.
     * @return array of schedules constituting the first generation.
     */
    private Schedule[] createRandomFirstGeneration(int generationSize) {
        Schedule[] randomGeneration = new Schedule[generationSize];
        for (int i = 0; i < randomGeneration.length; i++) {
            randomGeneration[i] = new Schedule(trainers, sectors);
        }
        return randomGeneration;
    }

    /**
     * Creates the next generation of schedules from the current generation.
     * @param currentGeneration the array of current generation schedules.
     * @param elitismPercentage the percentage of top schedules to carry over to the next generation.
     * @param mutationRate the mutation rate applied to the new generation.
     * @return array of schedules constituting the next generation.
     */
    private Schedule[] createNextGeneration(Schedule[] currentGeneration, double elitismPercentage, double mutationRate) {
        Schedule[] nextGeneration = new Schedule[currentGeneration.length];

        // Sort schedules by fitness in descending order
        Arrays.sort(currentGeneration, Comparator.comparingDouble(Schedule::getFitness).reversed());

        int i = 0;
        // Carry forward the top schedules as per elitism percentage
        for (; i < (int)(currentGeneration.length * elitismPercentage); i++) {
            nextGeneration[i] = currentGeneration[i];
        }

        // Fill the rest of the next generation with offspring of selected parents
        for (; i < nextGeneration.length; i++) {
            Schedule parentA = selectParents(currentGeneration);
            Schedule parentB;
            do {
                parentB = selectParents(currentGeneration);
            } while (parentA == parentB);

            Schedule child = new Schedule(parentA, parentB);
            child.mutateSchedule(mutationRate);
            nextGeneration[i] = child;
        }

        return nextGeneration;
    }

    /**
     * Selects parents for reproduction using a tournament selection method.
     * @param scheduleHolders the array of schedules from which to select parents.
     * @return the selected schedule based on tournament selection.
     */
    private Schedule selectParents(Schedule[] scheduleHolders) {
        Random random = new Random();
        int tournamentSize = 3;
        Schedule[] tournament = new Schedule[tournamentSize];
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(scheduleHolders.length);
            tournament[i] = scheduleHolders[randomIndex];
        }
        return getBestSchedule(tournament);
    }

    /**
     * Returns the best schedule among a set based on fitness.
     * @param schedules array of candidate schedules.
     * @return the schedule with the highest fitness.
     */
    private Schedule getBestSchedule(Schedule[] schedules) {
        Schedule bestSchedule = schedules[0];
        for (Schedule schedule : schedules) {
            if (schedule.getFitness() > bestSchedule.getFitness()) {
                bestSchedule = schedule;
            }
        }
        return bestSchedule;
    }

    private double calculateAvgFitness(Schedule[] schedules) {
        double sum = 0;
        for (Schedule schedule : schedules) {
            sum += schedule.getFitness();
        }
        return sum / schedules.length;
    }

    /**
     * Executes the genetic algorithm.
     * @param generationAmount the number of generations to run.
     * @param generationSize the size of each generation.
     * @param mutationRate the mutation rate applied to the generations.
     * @param elitismPercentage the percentage of top performers to carry over to the next generation.
     * @return the best schedule matrix after all generations are run.
     */
    public int[][] runAlgorithm(int generationAmount, int generationSize, double mutationRate, double elitismPercentage) {
        Schedule[] generation = createRandomFirstGeneration(generationSize);
        printScheduleBySector(getBestSchedule(generation).getMatrix());

        for (int i = 0; i < generationAmount; i++) {
            generation = createNextGeneration(generation, elitismPercentage, mutationRate);
        }

        printScheduleBySector(getBestSchedule(generation).getMatrix());
        return getBestSchedule(generation).getMatrix();
    }

    /**
     * Prints the schedules for each sector.
     * @param schedule the matrix representing the schedule for each sector and shift.
     */
    private void printScheduleBySector(int[][] schedule) {
        for (int sectorIndex = 0; sectorIndex < sectors.length; sectorIndex++) {
            System.out.println("Schedule for Sector " + sectors[sectorIndex].getName() + ":");
            for (int shift = 0; shift < Constants.SHIFTS_PER_WEEK; shift++) {
                System.out.print("Shift " + shift + ": ");
                List<String> assignedTrainers = new ArrayList<>();
                for (int guardIndex = 0; guardIndex < trainers.length; guardIndex++) {
                    if (schedule[guardIndex][shift] == sectorIndex) {
                        String trainerName = trainers[guardIndex].getName() + (trainers[guardIndex].isManager() ? " M" : "");
                        assignedTrainers.add(trainerName);
                    }
                }
                if (assignedTrainers.isEmpty()) {
                    System.out.println("No trainers assigned.");
                } else {
                    System.out.println(String.join(", ", assignedTrainers));
                }
            }
            System.out.println(); // For better readability
        }
    }
}
