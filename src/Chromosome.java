import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Representation: array of integers, index represents the column, the stored value represents the row
 */
public class Chromosome implements Comparable<Chromosome>{
    private final int[] queens;
    private final Settings settings;

    public Chromosome(int[] queens, Settings settings) {
        this.queens = queens;
        this.settings = settings;
    }

    @Override
    public String toString() {
        return Arrays.toString(queens);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Arrays.equals(queens, that.queens);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(queens);
    }
    @Override
    public int compareTo(Chromosome other) {
        return Integer.compare(getFitness(), other.getFitness());
    }

    public void mutate() {
        int index1 = (int) (Math.random() * settings.getN());
        int index2 = (int) (Math.random() * settings.getN());
        while (index1 == index2) index2 = (int) (Math.random() * settings.getN());
        int temp = queens[index1];
        queens[index1] = queens[index2];
        queens[index2] = temp;
    }

    public String draw() {
        ArrayList<Point> positions = new ArrayList<>();
        for (int i = 0; i < settings.getN(); i++) {
            positions.add(new Point(i, queens[i]));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < settings.getN(); i++) {
            for (int j = 0; j < settings.getN(); j++) sb.append(positions.contains(new Point(i,j)) ? "# " : "_ ");
            sb.append('\n');
        }
        return sb.toString();
    }

    public int[] getQueens() {
        return queens;
    }
    public int getFitness() {
        int fitness = 0;
        for (int i = 0; i < settings.getN()-1; i++) {
            for (int j = i; j < settings.getN(); j++) {
                if (Math.abs((double)(i - j)/(queens[i] - queens[j])) == 1) {
                    fitness++;
                }
            }
        }
        return fitness;
    }

    public static Chromosome create(Settings settings) {
        ArrayList<Integer> queens = new ArrayList<>(settings.getN());
        for (int i = 0; i < settings.getN(); i++) {
            queens.add(i);
        }
        Collections.shuffle(queens);
        return new Chromosome(queens.stream().mapToInt(i -> i).toArray(), settings);
    }
}
