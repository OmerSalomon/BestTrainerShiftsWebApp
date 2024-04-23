package entities.algorithm;

public class ScheduleHolder {
    public final int[][] schedule;
    public final double fitness;

    public ScheduleHolder(int[][] matrix, double fitness) {
        this.schedule = matrix;
        this.fitness = fitness;
    }
}
