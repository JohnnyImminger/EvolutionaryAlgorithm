import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {

    public static void main(String[] args) {
        Algorithm alg = new Algorithm();
        alg.run();
    }

    private final ArrayList<Chromosome> solutions = new ArrayList<>(Settings.amountOfSolutions);
    private final ArrayList<Chromosome> population = new ArrayList<>(Settings.populationSize);

    private int iteration = 0;

    public void run() {
        init();
        while(solutions.size() < Settings.amountOfSolutions) {
            crossover();
            mutation();
            selection();
            findSolutions();
            if(iteration%500 == 0) System.out.println(iteration + ", " + solutions.size() + " solutions");
            iteration++;
        }
        System.out.println(iteration + " iterations until finishing");
    }

    public int getIteration() {
        return iteration;
    }
    public ArrayList<Chromosome> getSolutions() {
        return solutions;
    }

    private void init() {
        for (int i = 0; i < Settings.populationSize; i++) {
            population.add(Chromosome.create());
        }
    }
    private void crossover() {
        for (int i = 0; i < Settings.n / 2; i++) {
            if(Math.random() <= Settings.crossoverRate) {
                Chromosome p1 = getRandomParent();
                Chromosome p2 = getRandomParent();
                while(p1 == p2) p2 = getRandomParent();
                population.add(alternatingPositionCrossover(p1, p2));
                population.add(alternatingPositionCrossover(p2, p1));
            }
        }
    }
    private void mutation() {
        for (Chromosome c: population) {
            if (Math.random() <= Settings.mutationRate1) {
                c.mutate();
            }
            if (Math.random() <= Settings.mutationRate2) {
                c.mutate();
            }
            if (Math.random() <= Settings.mutationRate3) {
                c.mutate();
            }
        }
    }
    private void selection() {
        Collections.sort(population);
        for (int i = population.size(); i < Settings.populationSize; i++) {
            population.remove(i-1);
        }
    }
    private void findSolutions() {
        for (Chromosome chromosome: population) {
            if(chromosome.getFitness() == 0) {
                solutions.add(chromosome);
                population.set(population.indexOf(chromosome), Chromosome.create());
            }
        }
    }

    private Chromosome alternatingPositionCrossover(Chromosome p1, Chromosome p2) {
        ArrayList<Integer> successor = new ArrayList<>();
        for (int i = 0; i<Settings.n; i++) {
            if(!successor.contains(p1.getQueens()[i])) successor.add(p1.getQueens()[i]);
            if(!successor.contains(p2.getQueens()[i])) successor.add(p2.getQueens()[i]);
        }
        return new Chromosome(successor.stream().mapToInt(i -> i).toArray());
    }

    private Chromosome getRandomParent() {
        return population.get((int) (Math.random()* Settings.populationSize));
    }
}
