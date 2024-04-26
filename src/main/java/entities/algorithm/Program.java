package entities.algorithm;

import entities.WSector;
import entities.WTrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Program {
    public static void main(String[] args) {
        Trainer[] trainers = createGuardsArr(30);
        Sector[] sectors = createSectorsArr();

        GeneticAlgorithm ga = new GeneticAlgorithm(trainers, sectors);
        int populationSize = 100; // Size of the population
        int generationAmount = 1000; // Maximum number of generations

        // Run the genetic algorithm simulation
        int[][] bestSchedule = ga.runAlgorithm(generationAmount, populationSize , 0.01, 0.1);
    }

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

    private static Trainer[] createGuardsArr(int amount) {
        Trainer[] guards = new Trainer[amount];

        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            String name = "Trainer" + i;
            boolean isManager = 0.1 > random.nextDouble();
            boolean[] availability = createAvailability();
            guards[i] = new Trainer(name, availability, isManager);
        }

        return guards;
    }

    private static boolean[] createAvailability() {
        Random random = new Random();

        boolean[] availability = new boolean[Constants.SHIFTS_PER_WEEK];

        for (int i = 0; i < Constants.SHIFTS_PER_WEEK; i++) {
            availability[i] = random.nextDouble() < 0.4;
        }

        return availability;
    }

    public static int[][] runGeneticAlgorithm(WTrainer[] wTrainerArray, WSector[] wSectorArray) {
        Trainer[] trainers = convertTrainerData(wTrainerArray);
        Sector[] sectors = convertSectorData(wSectorArray);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(trainers, sectors);

        return  geneticAlgorithm.runAlgorithm(1000, 100, 0.01, 0.1);
    }

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

    public static boolean[] stringToBooleanArray(String binaryString) {
        if (binaryString == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }

        boolean[] boolArray = new boolean[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) {
            char c = binaryString.charAt(i);
            if (c == '1') {
                boolArray[i] = true;
            } else if (c == '0') {
                boolArray[i] = false;
            } else {
                throw new IllegalArgumentException("Input string must only contain 0s and 1s.");
            }
        }
        return boolArray;
    }


}