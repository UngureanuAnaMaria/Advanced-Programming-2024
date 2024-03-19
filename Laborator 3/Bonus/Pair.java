import java.util.Objects;

public class Pair<T, U> {
    private T start;
    private U end;

    public Pair(T start, U end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public U getEnd() {
        return end;
    }

    public void setEnd(U end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(start, pair.start) && Objects.equals(end, pair.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
