package entities.algorithm;

import entities.WSector;
import entities.WTrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class serves as the main driver program for executing the genetic algorithm for scheduling trainers across various sectors.
 */
public class Program {
    public static void main(String[] args) {
        // Create initial arrays of trainers and sectors
        Trainer[] trainers = createGuardsArr(30);
        Sector[] sectors = createSectorsArr();

        // Initialize the genetic algorithm with trainers and sectors
        GeneticAlgorithm ga = new GeneticAlgorithm(trainers, sectors);
        int populationSize = 100; // Size of the population
        int generationAmount = 1000; // Maximum number of generations

        // Run the genetic algorithm simulation to find the best schedule
        int[][] bestSchedule = ga.runAlgorithm(generationAmount, populationSize, 0.01, 0.1);
    }

    /**
     * Creates an array of Sector objects for the genetic algorithm.
     * @return an array of Sector initialized with predefined values.
     */
    private static Sector[] createSectorsArr() {
        List<Sector> sectors = new ArrayList<>();
        sectors.add(new Sector("Lifting room", new int[] {1, 5, 10}));
        sectors.add(new Sector("Swimming pool", new int[] {2, 2, 7}));
        sectors.add(new Sector("Basement", new int[] {0, 0, 0}));

        Sector[] array = new Sector[sectors.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = sectors.get(i);
        }

        return array;
    }

    /**
     * Creates an array of Trainer objects with randomized attributes.
     * @param amount the number of trainers to create.
     * @return an array of Trainer objects.
     */
    private static Trainer[] createGuardsArr(int amount) {
        Trainer[] guards = new Trainer[amount];
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            String name = "Trainer" + i;
            boolean isManager = random.nextDouble() < 0.1;
            boolean[] availability = createAvailability();
            guards[i] = new Trainer(name, availability, isManager);
        }

        return guards;
    }

    /**
     * Generates a boolean array representing the availability of a trainer for each shift.
     * @return a boolean array with availability marked as true or false based on random chance.
     */
    private static boolean[] createAvailability() {
        Random random = new Random();
        boolean[] availability = new boolean[Constants.SHIFTS_PER_WEEK];
        for (int i = 0; i < Constants.SHIFTS_PER_WEEK; i++) {
            availability[i] = random.nextDouble() < 0.4;
        }

        return availability;
    }

    /**
     * Converts WTrainer and WSector arrays to Trainer and Sector arrays, and runs the genetic algorithm.
     * @param wTrainerArray array of WTrainer objects.
     * @param wSectorArray array of WSector objects.
     * @return the best schedule matrix found by the genetic algorithm.
     */
    public static int[][] runGeneticAlgorithm(WTrainer[] wTrainerArray, WSector[] wSectorArray) {
        Trainer[] trainers = convertTrainerData(wTrainerArray);
        Sector[] sectors = convertSectorData(wSectorArray);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(trainers, sectors);
        return geneticAlgorithm.runAlgorithm(1000, 100, 0.01, 0.1);
    }

    /**
     * Converts WSector objects to Sector objects.
     * @param wSectors array of WSector objects.
     * @return an array of Sector objects.
     */
    public static Sector[] convertSectorData(WSector[] wSectors) {
        Sector[] sectors = new Sector[wSectors.length];
        for (int i = 0; i < wSectors.length; i++) {
            String name = wSectors[i].getName();
            int morningShiftSize = wSectors[i].getMorningShiftSize();
            int noonShiftSize = wSectors[i].getNoonShiftSize();
            int eveningShiftSize = wSectors[i].getEveningShiftSize();
            sectors[i] = new Sector(name, new int[] {morningShiftSize, noonShiftSize, eveningShiftSize});
        }
        return sectors;
    }

    /**
     * Converts WTrainer objects to Trainer objects.
     * @param wTrainers array of WTrainer objects.
     * @return an array of Trainer objects.
     */
    public static Trainer[] convertTrainerData(WTrainer[] wTrainers) {
        Trainer[] trainers = new Trainer[wTrainers.length];
        for (int i = 0; i < wTrainers.length; i++) {
            String name = wTrainers[i].getName();
            boolean[] availability = stringToBooleanArray(wTrainers[i].getAvailabilityString());
            boolean isManager = wTrainers[i].getIsManager();
            trainers[i] = new Trainer(name, availability, isManager);
        }

        return trainers;
    }

    /**
     * Converts a string of '0's and '1's to a boolean array.
     * @param binaryString a string containing only '0' and '1' characters.
     * @return a boolean array where '1' is true and '0' is false.
     */
    public static boolean[] stringToBooleanArray(String binaryString) {
        if (binaryString == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        boolean[] boolArray = new boolean[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) {
            boolArray[i] = binaryString.charAt(i) == '1';
        }
        return boolArray;
    }
}
