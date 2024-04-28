package entities.algorithm;


import java.util.*;

public class GeneticAlgorithm {
    private Trainer[] trainers;
    private Sector[] sectors;
    private Random rnd;

    public GeneticAlgorithm(Trainer[] trainers, Sector[] sectors) {
        this.trainers = trainers;
        this.sectors = sectors;
        rnd = new Random();
    }

    private Schedule[] createRandomFirstGeneration(int generationSize) {
        Schedule[] randomGeneration = new Schedule[generationSize];

        for (int i = 0; i < randomGeneration.length; i++) {
            randomGeneration[i] = new Schedule(trainers, sectors);
        }

        return randomGeneration;
    }

    private Schedule[] createNextGeneration(Schedule[] currentGeneration, double elitismPercentage, double mutationRate) {
        Schedule[] nextGeneration = new Schedule[currentGeneration.length];

        Arrays.sort(currentGeneration, Comparator.comparingDouble(Schedule::getFitness).reversed());

        int i = 0;
        for (; i < currentGeneration.length * elitismPercentage; i++) {
            nextGeneration[i] = currentGeneration[i];
        }

        for (; i < nextGeneration.length; i++) {
            Schedule parentA = selectParents(currentGeneration);
            Schedule parentB = null;
            while (parentA != parentB)
                parentB = selectParents(currentGeneration);

            Schedule child = new Schedule(parentA, parentB);
            child.mutateSchedule(mutationRate);
            nextGeneration[i] = child;
        }

        return nextGeneration;
    }

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

    private Schedule getBestSchedule(Schedule[] schedules) {
        Schedule bestSchedule = schedules[0];

        for (Schedule schedule : schedules) {
            if (schedule.getFitness() > bestSchedule.getFitness())
                bestSchedule = schedule;
        }

        return bestSchedule;
    }

    public int[][] runAlgorithm(int generationAmount, int generationSize, double mutationRate, double elitismPercentage){
        Schedule[] generation = createRandomFirstGeneration(generationSize);
        printScheduleBySector(getBestSchedule(generation).getMatrix());

        for (int i = 0; i < generationAmount; i++) {
            generation = createNextGeneration(generation, elitismPercentage, mutationRate);
        }

        printScheduleBySector(getBestSchedule(generation).getMatrix());
        return getBestSchedule(generation).getMatrix();
    }

    private double getAverageFitness(Schedule[] scheduleHolders) {
        double fitnessSum = 0;

        for (Schedule schedule : scheduleHolders) {
            fitnessSum += schedule.getFitness();
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
