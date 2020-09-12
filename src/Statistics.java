public class Statistics {
    int iterations;
    Chromosome solution;
    Settings settings;
    long start;
    long end;
    long computeTime;

    public Statistics(int iterations, Chromosome solution, Settings settings, long start, long end) {
        this.iterations = iterations;
        this.solution = solution;
        this.settings = settings;
        this.start = start;
        this.end = end;
        this.computeTime = end-start;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "iterations=" + iterations +
                ", solution=" + solution +
                ", settings=" + settings +
                ", start=" + start +
                ", end=" + end +
                ", comuteTime=" + computeTime +
                '}';
    }
}
