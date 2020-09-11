import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Representation: array of integers, index represents the column, the integer represents the row
 */
public class Chromosome implements Comparable<Chromosome>{
    private final int[] queens;

    public Chromosome(int[] queens) {
        this.queens = queens;
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
        int index1 = (int) (Math.random() * Settings.n);
        int index2 = (int) (Math.random() * Settings.n);
        while (index1 == index2) index2 = (int) (Math.random() * Settings.n);
        int temp = queens[index1];
        queens[index1] = queens[index2];
        queens[index2] = temp;
    }

    public String draw() {
        ArrayList<Point> positions = new ArrayList<>();
        for (int i = 0; i < Settings.n; i++) {
            positions.add(new Point(i, queens[i]));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Settings.n; i++) {
            for (int j = 0; j < Settings.n; j++) sb.append(positions.contains(new Point(i,j)) ? "# " : "_ ");
            sb.append('\n');
        }
        return sb.toString();
    }

    public int[] getQueens() {
        return queens;
    }
    public int getFitness() {
        int fitness = 0;
        for (int i = 0; i < Settings.n-1; i++) {
            for (int j = i; j < Settings.n; j++) {
                if (Math.abs((double)(i - j)/(queens[i] - queens[j])) == 1) {
                    fitness++;
                }
            }
        }
        return fitness;
    }

    public static Chromosome create() {
        ArrayList<Integer> queens = new ArrayList<>(Settings.n);
        for (int i = 0; i < Settings.n; i++) {
            queens.add(i);
        }
        Collections.shuffle(queens);
        return new Chromosome(queens.stream().mapToInt(i -> i).toArray());
    }
}
