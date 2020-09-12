import java.util.ArrayList;
import java.util.Collections;

public class Algorithm implements Runnable{

    public static void main(String[] args) {
        Algorithm alg = new Algorithm();
        alg.run();
        System.out.println(alg.solutions.get(0).draw());
    }

    public Algorithm(){
        this.settings = Settings.get();
    }
    public Algorithm(Settings settings) {
        this.settings = settings;
    }

    private final ArrayList<Chromosome> solutions = new ArrayList<>();
    private final ArrayList<Chromosome> population = new ArrayList<>();

    private int iteration = 0;
    private final Settings settings;

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        init();
        while(solutions.size() < settings.getAmountOfSolutions()) {
            crossover();
            mutation();
            selection();
            findSolutions();
            iteration++;
            if (Thread.interrupted()) return;
        }
        long end = System.currentTimeMillis();
        TestSuite.returnStatistics(new Statistics(iteration, solutions.get(0), settings, start, end));
    }

    public int getIteration() {
        return iteration;
    }
    public ArrayList<Chromosome> getSolutions() {
        return solutions;
    }
    public Settings getSettings() {
        return settings;
    }

    private void init() {
        for (int i = 0; i < settings.getPopulationSize(); i++) {
            population.add(Chromosome.create(settings));
        }
    }
    private void crossover() {
        for (int i = 0; i < settings.getN() / 2; i++) {
            if(Math.random() <= settings.getCrossoverRate()) {
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
            if (Math.random() <= settings.getMutationRate1()) {
                c.mutate();
            }
            if (Math.random() <= settings.getMutationRate2()) {
                c.mutate();
            }
            if (Math.random() <= settings.getMutationRate3()) {
                c.mutate();
            }
        }
    }
    private void selection() {
        Collections.sort(population);
        for (int i = population.size(); i < settings.getPopulationSize(); i++) {
            population.remove(i-1);
        }
    }
    private void findSolutions() {
        for (Chromosome chromosome: population) {
            if(chromosome.getFitness() == 0) {
                solutions.add(chromosome);
                population.set(population.indexOf(chromosome), Chromosome.create(settings));
            }
        }
    }

    private Chromosome alternatingPositionCrossover(Chromosome p1, Chromosome p2) {
        ArrayList<Integer> successor = new ArrayList<>();
        for (int i = 0; i<settings.getN(); i++) {
            if(!successor.contains(p1.getQueens()[i])) successor.add(p1.getQueens()[i]);
            if(!successor.contains(p2.getQueens()[i])) successor.add(p2.getQueens()[i]);
            if(successor.size() == settings.getN()) break;
        }
        return new Chromosome(successor.stream().mapToInt(i -> i).toArray(), settings);
    }

    private Chromosome getRandomParent() {
        return population.get((int) (Math.random()* settings.getPopulationSize()));
    }
}
