import java.util.*;
import java.util.concurrent.*;

public class TestSuite {

    private static Map<Settings, ArrayList<Statistics>> results = new HashMap<>();
    private static final ThreadPoolExecutor service = new ThreadPoolExecutor(10, 30, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) throws InterruptedException {
        /*
        for (int i = 10; i < 18; i+=2) {
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.6, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.0, 0.0, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.2, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.4, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.6, 1), 20);
        }
         */

        runWith(new Settings(26, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        runWith(new Settings(28, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        runWith(new Settings(30, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);


        service.shutdown();
        service.awaitTermination(60, TimeUnit.MINUTES);
        showResults();
    }

    private static void runWith(Settings settings, int howOften) {
        for (int i = 0; i < howOften; i++) {
            service.execute(new Algorithm(settings));
        }
    }

    private static void showResults() {
        List<Settings> keys = new ArrayList<>(results.keySet());
        Collections.sort(keys);
        for (Settings key: keys) {
            ArrayList<Statistics> list = results.get(key);
            double averageIterations = list.stream().mapToInt(s -> s.iterations).average().getAsDouble();
            System.out.println(key);
            System.out.println("Average iterations: " + averageIterations);
            System.out.println();
        }
        List<Runnable> uncompleted = service.shutdownNow();
        System.out.println("Uncompleted tasks: " + uncompleted.size());
        if(uncompleted.size() == 0) return;
        System.out.println("Settings:");
        for (Runnable task: uncompleted) {
            System.out.println(((Algorithm) task).getSettings());
        }
    }

    public static synchronized void returnStatistics(Statistics s) {
        if(results.containsKey(s.settings)) {
            results.get(s.settings).add(s);
        } else {
            ArrayList<Statistics> statistics = new ArrayList<>(10);
            statistics.add(s);
            results.put(s.settings, statistics);
        }
    }
}
