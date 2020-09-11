import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestSuite {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> results = new ConcurrentLinkedQueue<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Thread t = new Thread(() -> {
                Algorithm alg = new Algorithm();
                alg.run();
                results.add(alg.getIteration());
            });
            threads.add(t);
            t.start();
        }
        for (Thread i: threads) {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Average iterations: " + results.stream().mapToInt(i -> i).sum() / results.size());
    }
    private static void writeResults(int iterations) {
        File file = new File("output/run");
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write("n = " + Settings.n);
            br.newLine();
            br.write("populationSize = " + Settings.populationSize);
            br.newLine();
            br.write("mutationRate1 = " + Settings.mutationRate1);
            br.newLine();
            br.write("mutationRate2 = " + Settings.mutationRate2);
            br.newLine();
            br.write("mutationRate3 = " + Settings.mutationRate3);
            br.newLine();
            br.write("crossoverRate = " + Settings.crossoverRate);
            br.newLine();
            br.write("amountOfSolutions = " + Settings.amountOfSolutions);
            br.newLine();
            br.write("iterations needed: " + iterations);
            br.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
