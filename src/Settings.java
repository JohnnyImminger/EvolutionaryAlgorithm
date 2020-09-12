/**
 * The algorithm can be configured here.
 * - n determines the size of the n-queen problem.
 * - populationSize defines the size of the population
 * - the mutationRates are defining the probability for a single, double and triple mutation
 * - crossoverRate is the probability of a crossover to occur
 * - amountOfSolutions is used as the finishing condition for the algorithm. If this amount of solutions are found, the algorithm will end.
 * A valid solution is defined by the fitness zero.
 */
public class Settings implements Comparable<Settings> {
    private final int n;
    private final int populationSize;
    private final double mutationRate1;
    private final double mutationRate2;
    private final double mutationRate3;
    private final double crossoverRate;
    private final int amountOfSolutions;

    public Settings(int n, int populationSize, double mutationRate1, double mutationRate2, double mutationRate3, double crossoverRate, int amountOfSolutions) {
        this.n = n;
        this.populationSize = populationSize;
        this.mutationRate1 = mutationRate1;
        this.mutationRate2 = mutationRate2;
        this.mutationRate3 = mutationRate3;
        this.crossoverRate = crossoverRate;
        this.amountOfSolutions = amountOfSolutions;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "n=" + n +
                ", populationSize=" + populationSize +
                ", mutationRate1=" + mutationRate1 +
                ", mutationRate2=" + mutationRate2 +
                ", mutationRate3=" + mutationRate3 +
                ", crossoverRate=" + crossoverRate +
                ", amountOfSolutions=" + amountOfSolutions +
                '}';
    }

    public static Settings get() {
        return new Settings(8, 1000, 0.8, 0.3, 0.1, 0.8, 1);
    }

    public int getN() {
        return n;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getMutationRate1() {
        return mutationRate1;
    }

    public double getMutationRate2() {
        return mutationRate2;
    }

    public double getMutationRate3() {
        return mutationRate3;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getAmountOfSolutions() {
        return amountOfSolutions;
    }

    @Override
    public int compareTo(Settings o) {
        if(this.getN() == o.getN()) {
            return Integer.compare(this.populationSize, o.populationSize);
        } else {
            return Integer.compare(this.n, o.n);
        }
    }
}
