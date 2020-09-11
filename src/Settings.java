/**
 * The algorithm can be configured here.
 * - n determines the size of the n-queen problem.
 * - populationSize defines the size of the population
 * - the mutationRates are defining the probability for a single, double and triple mutation
 * - crossoverRate is the probability of a crossover to occur
 * - amountOfSolutions is used as the finishing condition for the algorithm. If this amount of solutions are found, the algorithm will end.
 * A valid solution is defined by the fitness zero.
 */
public class Settings {
    static int n = 25;
    static int populationSize = 10000;
    static double mutationRate1 = 0.8;
    static double mutationRate2 = 0.3;
    static double mutationRate3 = 0.3;
    static double crossoverRate = 0.9;
    static int amountOfSolutions = 1;
}
