import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestSuite {

    private static final Map<Settings, ArrayList<Statistics>> results = new HashMap<>();
    private static final ThreadPoolExecutor service = new ThreadPoolExecutor(10, 30, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting time: " + Instant.now().atZone(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        /*
        for (int i = 8; i < 20; i+=2) {
            runWith(new Settings(i, 1000, 0.6, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.0, 0.0, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.2, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.4, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.6, 1), 20);
        }
        for (int i = 8; i < 20; i+=2) {
            runWith(new Settings(i, 10, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 50, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 100, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 500, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 1000, 0.8, 0.3, 0.1, 0.8, 1), 20);
            runWith(new Settings(i, 5000, 0.8, 0.3, 0.1, 0.8, 1), 20);
        }
        */
        //runWith(new Settings(20, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        //runWith(new Settings(22, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        //runWith(new Settings(24, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        //runWith(new Settings(26, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        runWith(new Settings(28, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);
        //runWith(new Settings(30, 10000, 0.8, 0.3, 0.1, 0.8, 1), 10);

        //Algorithm testBigN = new Algorithm(new Settings(40, 10000, 0.8, 0.3, 0.1, 0.8, 1));


        service.shutdown();
        service.awaitTermination(3, TimeUnit.HOURS);
        showResults();

        System.out.println("Ending time: " + Instant.now().atZone(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
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
